package br.com.sbk.saml2.idp.controller;

import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.sbk.saml2.idp.dto.SAMLAttribute;
import br.com.sbk.saml2.idp.dto.SAMLPrincipal;
import br.com.sbk.saml2.idp.entity.metadados.sp.EntityDescriptor;
import br.com.sbk.saml2.idp.service.MetadataService;
import br.com.sbk.saml2.idp.service.SAMLMessageService;

@Controller
public class SsoController {

	@Autowired
	private SAMLMessageService samlMessage;

	@Autowired
	private MetadataService database;

	@Value("${login.page.url}")
	private String loginUrl;

	public static final Logger logger = LoggerFactory.getLogger(SsoController.class);

	@GetMapping("/login")
	public ModelAndView singleSignOnServiceGet(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication, final ModelMap model) throws MessageDecodingException,
			MetadataProviderException, SecurityException, ValidationException, FileNotFoundException,
			InvalidKeyException, CertificateException, NoSuchAlgorithmException, java.security.SignatureException {

		final org.springframework.security.saml.context.SAMLMessageContext context = this.samlMessage
				.extractSAMLMessageContext(request, response, false);

		SsoController.logger.info("Pesquisando service provider {} na base de dados.",
				context.getInboundMessageIssuer());

		final EntityDescriptor metadados = this.database.recuperarEntityDescriptor(context.getInboundMessageIssuer());
		if (metadados == null) {
			throw new IllegalArgumentException(
					"Não foi possível obter os dados do entityId '" + context.getInboundMessageIssuer() + "'.");
		}

		if (context.getInboundSAMLMessage() instanceof AuthnRequest) {
			// Envia informacoes do contexto e SAMLRequest para atributos da pagina
			final AuthnRequest auth = (AuthnRequest) context.getInboundSAMLMessage();
			SsoController.logger.info("Atribuindo authRequest: " + auth.getID() + " para o issuer ["
					+ context.getInboundMessageIssuer() + "]");
			model.addAttribute("requestID", auth.getID());
			model.addAttribute("requestIssuer",
					Base64.getEncoder().encodeToString(auth.getIssuer().getValue().getBytes()));
			model.addAttribute("sigAlg",
					Base64.getEncoder().encodeToString(auth.getAssertionConsumerServiceURL().getBytes())/* sigAlg */);
			model.addAttribute("assertionConsumerServiceURL",
					Base64.getEncoder().encodeToString(auth.getAssertionConsumerServiceURL().getBytes()));
		}

		final String signature = request.getParameter("Signature");

		model.addAttribute("postRequest", "false");
		model.addAttribute("entityId",
				Base64.getEncoder().encodeToString(context.getInboundMessageIssuer().getBytes()));
		model.addAttribute("relayState", Base64.getEncoder().encodeToString(context.getRelayState().getBytes()));
		model.addAttribute("samlRequest", request.getParameter("SAMLRequest"));
		model.addAttribute("signature", signature);
		model.addAttribute("mensagem", request.getParameter("mensagem"));

		// Valida Certificado do SP
		samlMessage.validarCertificado(metadados.getSPSSODescriptor().getKeyDescriptor().get(0).getKeyInfo()
				.getX509Data().getX509Certificate(), signature);

		return new ModelAndView("redirect:" + loginUrl, model);
	}

//	@PostMapping("/doSSO")
	@RequestMapping(path = "/doSSO", method = { RequestMethod.GET, RequestMethod.POST })
	private void doSSO(final HttpServletRequest request, final HttpServletResponse response, final ModelMap model)
			throws FileNotFoundException {

		final String entityId = new String(Base64.getDecoder().decode(request.getParameter("entityId")));
		if (StringUtils.isEmpty(entityId)) {
			throw new IllegalArgumentException("Não foi possível obter o parametro 'entityId'.");
		}

		final EntityDescriptor metadados = this.database.recuperarEntityDescriptor(entityId);
		if (metadados == null) {
			throw new IllegalArgumentException("Não foi possível obter os dados do entityId '" + entityId + "'.");
		}

		final String relayState = new String(Base64.getDecoder().decode(request.getParameter("relayState")));
		final String authRequestID = request.getParameter("requestID");
		final String authRequestIssuer = new String(Base64.getDecoder().decode(request.getParameter("requestIssuer")));
		final String username = request.getParameter("username");
		final String assertionConsumerServiceURL = new String(
				Base64.getDecoder().decode(request.getParameter("assertionConsumerServiceURL")));

		final List<SAMLAttribute> attributes = Collections
				.singletonList(new SAMLAttribute("autenticado", Collections.singletonList("true")));

		final SAMLPrincipal principal = new SAMLPrincipal(username,
				attributes.stream().filter(attr -> "urn:oasis:names:tc:SAML:1.1:nameid-format".equals(attr.getName()))
						.findFirst().map(attr -> attr.getValue()).orElse(NameIDType.UNSPECIFIED),
				attributes, authRequestIssuer, authRequestID, assertionConsumerServiceURL, relayState);
		
		logger.info("Usuario {} logado para EntityId {}.", username, entityId);

		this.samlMessage.sendAuthnResponse(principal, response, StatusCode.SUCCESS_URI);
	}
}
