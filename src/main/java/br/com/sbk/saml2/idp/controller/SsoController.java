package br.com.sbk.saml2.idp.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import br.com.sbk.saml2.idp.dto.SAMLAttribute;
import br.com.sbk.saml2.idp.dto.SAMLPrincipal;
import br.com.sbk.saml2.idp.entity.metadados.sp.EntityDescriptor;
import br.com.sbk.saml2.idp.service.AuthenticationService;
import br.com.sbk.saml2.idp.service.DatabaseService;
import br.com.sbk.saml2.idp.service.SAMLMessageService;

@Controller
public class SsoController {

	@Autowired
	private SAMLMessageService samlMessage;

	@Autowired
	private AuthenticationService authentication;

	@Autowired
	private DatabaseService database;

	@Value("${idp.error.message}")
	private String idpErrorMessage;

	@Value("${idp.error.message.avance}")
	private String idpErrorMessageAvance;

	String relayState;
	String sigAlg;
	String signature;
	String keyInfo;

	public static final Logger logger = LoggerFactory.getLogger(SsoController.class);

	@GetMapping("/login")
	public String singleSignOnServiceGet(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication, final ModelMap model)
			throws IOException, MarshallingException, SignatureException, MessageEncodingException, ValidationException,
			SecurityException, MessageDecodingException, MetadataProviderException, CertificateEncodingException,
			SAXException, ParserConfigurationException, JAXBException {

		final org.springframework.security.saml.context.SAMLMessageContext context = this.samlMessage
				.extractSAMLMessageContext(request, response, false);

		SsoController.logger.debug("Pesquisando service provider {} na base de dados.",
				context.getInboundMessageIssuer());

		final EntityDescriptor metadados = this.database.recuperarEntityDescriptor(context.getInboundMessageIssuer());

		if (metadados == null) {
			SsoController.logger.error(
					"Service Provider " + context.getInboundMessageIssuer() + " nao encontrado na base de dados.");
			model.addAttribute("mensagem",
					"Service Provider " + context.getInboundMessageIssuer() + " nao encontrado na base de dados.");
			return "loginErro";
		} else {
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
			this.relayState = context.getRelayState();
			this.sigAlg = request.getParameter("SigAlg");
			this.signature = request.getParameter("Signature");

			model.addAttribute("samlRequest", request.getParameter("SAMLRequest"));
			model.addAttribute("postRequest", "false");
			model.addAttribute("entityId", context.getInboundMessageIssuer());
			model.addAttribute("relayState", this.relayState);
			model.addAttribute("sigAlg", this.sigAlg);
			model.addAttribute("signature", this.signature);
			model.addAttribute("mensagem", request.getParameter("mensagem"));

			try {
				// Valida Certificado do SP
//				final byte[] keyBinary = DatatypeConverter.parseBase64Binary(metadados.getSPSSODescriptor()
//						.getKeyDescriptor().get(0).getKeyInfo().getX509Data().getX509Certificate());
//				final CertificateFactory certFactory = CertificateFactory.getInstance("X509");
//				final Certificate cert = certFactory.generateCertificate(new ByteArrayInputStream(keyBinary));
//
//				// Signature sign = Signature.getInstance("SHA1WithRSA");
//				// sign.initVerify(cert);
//				// sign.verify(DatatypeConverter.parseBase64Binary(this.signature));
//
//				final BasicX509Credential credential = new BasicX509Credential();
//				credential.setEntityCertificate((X509Certificate) cert);
			} catch (final Exception e) {
				SsoController.logger.error("Erro ao tentar validar certificado do SP: " + e.getLocalizedMessage(), e);
				model.addAttribute("mensagem", e.getLocalizedMessage());
				return "loginErro";
			}

			return "login";
		}
	}

	@PostMapping("/doSSO")
	private ModelAndView doSSO(final HttpServletRequest request, final HttpServletResponse response,
			Authentication authentication, final boolean postRequest, String identityId, String propertiesList,
			final ModelMap model) throws FileNotFoundException {

		final EntityDescriptor metadados = this.database.recuperarEntityDescriptor(request.getParameter("entityId"));

		String errorPage = "loginErro";

		if (metadados != null) {
			SsoController.logger.info("Atribuindo valores ao authRequest: " + request.getParameter("requestID")
					+ " - para o usuario [" + request.getParameter("j_username") + "]");
			final String authRequestID = request.getParameter("requestID");
			final String authRequestIssuer = request.getParameter("requestIssuer");
			String assertionConsumerServiceURL = metadados.getSPSSODescriptor().getAssertionConsumerService()
					.getLocation();
//			errorPage = tbIntsoaSpConfigResult.getSpErro();
			identityId = request.getParameter("entityId");
//			propertiesList = tbIntsoaSpConfigResult.getSpAttributes();
			final String optRadio = request.getParameter("optradio");

			if (request.getParameter("j_username") != null || request.getParameter("j_password") != null) {
				authentication = new UsernamePasswordAuthenticationToken(
						request.getParameter("j_username").toUpperCase(), request.getParameter("j_password"));

				SsoController.logger.info("Autenticando o usuario sob o dominio >>>> " + optRadio);

				final List<SAMLAttribute> attributes = this.authentication.authenticate(authentication, identityId,
						propertiesList, optRadio);

				SsoController.logger.info(
						"Properties do usuario <" + authentication.getPrincipal().toString() + ">: " + attributes);

				// primeiro atributo é se esta autenticado ou nao
				if (new Boolean(attributes.get(0).getValue())) {
					String statusCode = "";
					SAMLPrincipal principal = null;
					if (attributes.isEmpty()) {
						statusCode = StatusCode.AUTHN_FAILED_URI;
					} else {
						statusCode = StatusCode.SUCCESS_URI;
					}

					SsoController.logger.info("Atribuindo requestID a tag principal: " + authRequestID
							+ " - para o usuario [" + request.getParameter("j_username") + "]");
					principal = new SAMLPrincipal(authentication.getName(),
							attributes.stream()
									.filter(attr -> "urn:oasis:names:tc:SAML:1.1:nameid-format".equals(attr.getName()))
									.findFirst().map(attr -> attr.getValue()).orElse(NameIDType.UNSPECIFIED),
							attributes, authRequestIssuer, authRequestID, assertionConsumerServiceURL,
							request.getParameter("relayState"));

					this.samlMessage.sendAuthnResponse(principal, response, statusCode);

					return null;
				} else {
					model.addAttribute("entityId", request.getParameter("entityId"));
					model.addAttribute("msgErro", this.idpErrorMessage);
//					model.addAttribute("action", tbIntsoaSpConfigResult.getSpRedirect());

					return new ModelAndView(errorPage, model);
				}
			} else {
				model.addAttribute("msgErro", this.idpErrorMessage);
				model.addAttribute("entityId", request.getParameter("entityId"));
//				model.addAttribute("action", tbIntsoaSpConfigResult.getSpRedirect());
//				assertionConsumerServiceURL = tbIntsoaSpConfigResult.getSpErro();
				return new ModelAndView(assertionConsumerServiceURL, model);
			}
		} else {
			return new ModelAndView(errorPage, model);
		}
	}

	@GetMapping("/doRedirect")
	private void doRedirect(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		SsoController.logger.info("Redirecionando...");

		// TODO obter da request
		final String spRedirect = "";

		final EntityDescriptor metadados = this.database.recuperarEntityDescriptor(request.getParameter("entityId"));

		if (metadados != null) {
			response.sendRedirect(spRedirect);
		} else {
			response.sendRedirect("spError");
		}

	}

	@GetMapping("/loginErro")
	public String loginErro(final HttpServletRequest request, final HttpServletResponse response,
			final ModelMap model) throws FileNotFoundException {

		// TODO obter da request
		final String spRedirect = "";

		String errorPage = "loginErro";
		final EntityDescriptor metadados = this.database.recuperarEntityDescriptor(request.getParameter("entityId"));

		if (metadados != null) {
//			errorPage = tbIntsoaSpConfigResult.getSpErro();
			model.addAttribute("action", spRedirect);
		}

		model.addAttribute("msgErro", request.getParameter("msgErro"));
		return errorPage;
	}

}
