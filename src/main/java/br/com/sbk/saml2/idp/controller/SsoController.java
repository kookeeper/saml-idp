package br.com.sbk.saml2.idp.controller;

import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.impl.AuthnRequestImpl;
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

		SsoController.logger.debug("Pesquisando service provider {} na base de dados.",
				context.getInboundMessageIssuer());

		final EntityDescriptor metadados = this.database.recuperarEntityDescriptor(context.getInboundMessageIssuer());
		if (metadados == null) {
			throw new IllegalArgumentException(
					"Não foi possível obter os dados do entityId '" + context.getInboundMessageIssuer() + "'.");
		}

		SsoController.logger.debug("Abrindo pagina de login para service provider: {}",
				context.getInboundMessageIssuer());

		if (context.getInboundSAMLMessage() instanceof AuthnRequest) {
			// Envia informacoes do contexto e SAMLRequest para atributos da pagina
			final AuthnRequest auth = (AuthnRequest) context.getInboundSAMLMessage();
			SsoController.logger.info("Atribuindo authRequest: " + auth.getID() + " para o issuer ["
					+ context.getInboundMessageIssuer() + "]");
			model.addAttribute("requestID", auth.getID());
			model.addAttribute("requestIssuer", auth.getIssuer().getValue());
		}
		final String relayState = context.getRelayState();
		final String sigAlg = request.getParameter("SigAlg");
		final String signature = request.getParameter("Signature");

		model.addAttribute("samlRequest", request.getParameter("SAMLRequest"));
		model.addAttribute("postRequest", "false");
		model.addAttribute("entityId", context.getInboundMessageIssuer());
		model.addAttribute("relayState", Base64.getEncoder().encode(relayState.getBytes()));
		model.addAttribute("sigAlg", sigAlg);
		model.addAttribute("signature", signature);
		model.addAttribute("mensagem", request.getParameter("mensagem"));
		model.addAttribute("assertionConsumerServiceURL", Base64.getEncoder().encode(
				((AuthnRequestImpl) context.getInboundSAMLMessage()).getAssertionConsumerServiceURL().getBytes()));

		// Valida Certificado do SP
		samlMessage.validarCertificado(metadados.getSPSSODescriptor().getKeyDescriptor().get(0).getKeyInfo()
				.getX509Data().getX509Certificate(), signature);

		return new ModelAndView("redirect:" + loginUrl, model);
	}

//	@PostMapping("/doSSO")
	@RequestMapping(path = "/doSSO", method = { RequestMethod.GET, RequestMethod.POST })
	private void doSSO(final HttpServletRequest request, final HttpServletResponse response, final ModelMap model)
			throws FileNotFoundException {

		final String entityId = request.getParameter("entityId");
		if (StringUtils.isEmpty(entityId)) {
			throw new IllegalArgumentException("Não foi possível obter o parametro 'entityId'.");
		}

		final EntityDescriptor metadados = this.database.recuperarEntityDescriptor(entityId);
		if (metadados == null) {
			throw new IllegalArgumentException("Não foi possível obter os dados do entityId '" + entityId + "'.");
		}

		final String relayState = new String(Base64.getDecoder().decode(request.getParameter("relayState")));
		final String authRequestID = request.getParameter("requestID");
		SsoController.logger.info("Atribuindo valores ao authRequest: " + authRequestID + " - para o usuario ["
				+ request.getParameter("j_username") + "]");
		final String authRequestIssuer = request.getParameter("requestIssuer");
		final String username = request.getParameter("username");
		final String assertionConsumerServiceURL = new String(
				Base64.getDecoder().decode(request.getParameter("assertionConsumerServiceURL")));

		final List<SAMLAttribute> attributes = new ArrayList<>();

		attributes.add(new SAMLAttribute("autenticado", Collections.singletonList("true")));

		String statusCode = "";
		SAMLPrincipal principal = null;
		if (attributes.isEmpty()) {
			statusCode = StatusCode.AUTHN_FAILED_URI;
		} else {
			statusCode = StatusCode.SUCCESS_URI;
		}

		SsoController.logger.info("Atribuindo requestID a tag principal: " + authRequestID + " - para o usuario ["
				+ request.getParameter("j_username") + "]");
		principal = new SAMLPrincipal(username,
				attributes.stream().filter(attr -> "urn:oasis:names:tc:SAML:1.1:nameid-format".equals(attr.getName()))
						.findFirst().map(attr -> attr.getValue()).orElse(NameIDType.UNSPECIFIED),
				attributes, authRequestIssuer, authRequestID, assertionConsumerServiceURL, relayState);

		this.samlMessage.sendAuthnResponse(principal, response, statusCode);
	}
}
