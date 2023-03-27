package br.com.sbk.saml2.idp.service;

import static br.com.sbk.saml2.idp.execution.SAMLBuilder.buildAssertion;
import static br.com.sbk.saml2.idp.execution.SAMLBuilder.buildIssuer;
import static br.com.sbk.saml2.idp.execution.SAMLBuilder.buildSAMLObject;
import static br.com.sbk.saml2.idp.execution.SAMLBuilder.buildStatus;
import static br.com.sbk.saml2.idp.execution.SAMLBuilder.signAssertion;
import static java.util.Arrays.asList;
import static org.opensaml.xml.Configuration.getValidatorSuite;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.joda.time.DateTime;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.common.binding.encoding.SAMLMessageEncoder;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.impl.LogoutRequestImpl;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.security.SecurityPolicyResolver;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.ValidatorSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.saml.context.SAMLMessageContext;
import org.springframework.security.saml.key.KeyManager;

import br.com.sbk.saml2.idp.config.IdpConfiguration;
import br.com.sbk.saml2.idp.dto.SAMLPrincipal;
import br.com.sbk.saml2.idp.execution.ProxiedSAMLContextProviderLB;
import br.com.sbk.saml2.idp.execution.SAMLBuilder;

public class SAMLMessageService {

	private final KeyManager keyManager;
	private final Collection<SAMLMessageDecoder> decoders;
	private final SAMLMessageEncoder encoder;
	private final SecurityPolicyResolver resolver;
	private final IdpConfiguration idpConfiguration;

	private final List<ValidatorSuite> validatorSuites;
	private final ProxiedSAMLContextProviderLB proxiedSAMLContextProviderLB;

	public static final Logger log = LoggerFactory.getLogger(SAMLMessageService.class);

	public SAMLMessageService(KeyManager keyManager, Collection<SAMLMessageDecoder> decoders,
			SAMLMessageEncoder encoder, SecurityPolicyResolver securityPolicyResolver,
			IdpConfiguration idpConfiguration, String idpBaseUrl) throws URISyntaxException {
		try {
			DefaultBootstrap.bootstrap();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.keyManager = keyManager;
		this.encoder = encoder;
		this.decoders = decoders;
		this.resolver = securityPolicyResolver;
		this.idpConfiguration = idpConfiguration;
		this.validatorSuites = asList(getValidatorSuite("saml2-core-schema-validator"),
				getValidatorSuite("saml2-core-spec-validator"));
		this.proxiedSAMLContextProviderLB = new ProxiedSAMLContextProviderLB(new URI(idpBaseUrl));
	}

	public SAMLMessageContext extractSAMLMessageContext(HttpServletRequest request, HttpServletResponse response,
			boolean postRequest) throws MetadataProviderException, MessageDecodingException, SecurityException, ValidationException {
		SAMLMessageContext messageContext = new SAMLMessageContext();

		proxiedSAMLContextProviderLB.populateGenericContext(request, response, messageContext);

		messageContext.setSecurityPolicyResolver(resolver);

		SAMLMessageDecoder samlMessageDecoder = samlMessageDecoder(postRequest);
		samlMessageDecoder.decode(messageContext);

		SAMLObject inboundSAMLMessage = messageContext.getInboundSAMLMessage();

		if (inboundSAMLMessage instanceof AuthnRequest) {
			final AuthnRequest authnRequest = (AuthnRequest) inboundSAMLMessage;

			// lambda is poor with Exceptions
			for (ValidatorSuite validatorSuite : validatorSuites) {
				validatorSuite.validate(authnRequest);
			}
		} else if (inboundSAMLMessage instanceof LogoutRequestImpl) {
			final LogoutRequestImpl logoutRequest = (LogoutRequestImpl) inboundSAMLMessage;
			log.info("Logout para issuer {}.", logoutRequest.getIssuer().getSPProvidedID());
		}

		return messageContext;
	}

	private SAMLMessageDecoder samlMessageDecoder(boolean postRequest) {
		return decoders.stream()
				.filter(samlMessageDecoder -> postRequest
						? samlMessageDecoder.getBindingURI().equals(SAMLConstants.SAML2_POST_BINDING_URI)
						: samlMessageDecoder.getBindingURI().equals(SAMLConstants.SAML2_REDIRECT_BINDING_URI))
				.findAny().orElseThrow(() -> new RuntimeException(String.format("Only %s and %s are supported",
						SAMLConstants.SAML2_REDIRECT_BINDING_URI, SAMLConstants.SAML2_POST_BINDING_URI)));
	}

	@SuppressWarnings("unchecked")
	public void sendAuthnResponse(SAMLPrincipal principal, HttpServletResponse response, String statusCode) {

		try {
			Status status = buildStatus(statusCode);

			String entityId = idpConfiguration.getEntityId();
			Credential signingCredential = resolveCredential(entityId);

			Response authResponse = buildSAMLObject(Response.class, Response.DEFAULT_ELEMENT_NAME);
			Issuer issuer = buildIssuer(entityId);

			authResponse.setIssuer(issuer);
			authResponse.setID(SAMLBuilder.randomSAMLId());
			authResponse.setIssueInstant(new DateTime());
			authResponse.setInResponseTo(principal.getRequestID());

			Assertion assertion = buildAssertion(principal, status, entityId);
			signAssertion(assertion, signingCredential);

			authResponse.getAssertions().add(assertion);
			authResponse.setDestination(principal.getAssertionConsumerServiceURL());

			authResponse.setStatus(status);

			Endpoint endpoint = buildSAMLObject(Endpoint.class, SingleSignOnService.DEFAULT_ELEMENT_NAME);
			endpoint.setLocation(principal.getAssertionConsumerServiceURL());

			HttpServletResponseAdapter outTransport = new HttpServletResponseAdapter(response, false);

			@SuppressWarnings("rawtypes")
			BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();

			messageContext.setOutboundMessageTransport(outTransport);
			messageContext.setPeerEntityEndpoint(endpoint);
			messageContext.setOutboundSAMLMessage(authResponse);
			messageContext.setOutboundSAMLMessageSigningCredential(signingCredential);

			messageContext.setOutboundMessageIssuer(entityId);
			messageContext.setRelayState(principal.getRelayState());

			log.info("OutboundMessageIssuer enviado ao client: " + authResponse.getInResponseTo() + " para o usuario ["
					+ principal.getNameID() + "]");

			encoder.encode(messageContext);
		} catch (MarshallingException | SignatureException | MessageEncodingException e) {
			throw new IllegalArgumentException(
					"Erro ao tentar devolver response autenticado: " + e.getLocalizedMessage(), e);
		}

	}

	public Credential resolveCredential(String entityId) {
		try {
			return keyManager.resolveSingle(new CriteriaSet(new EntityIDCriteria(entityId)));
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public void validarCertificado(final String spCertificate, final String signature) throws CertificateException,
			NoSuchAlgorithmException, InvalidKeyException, java.security.SignatureException {
		final byte[] keyBinary = DatatypeConverter.parseBase64Binary(spCertificate);
		final CertificateFactory certFactory = CertificateFactory.getInstance("X509");
		final Certificate cert = certFactory.generateCertificate(new ByteArrayInputStream(keyBinary));

		Signature sign = Signature.getInstance("SHA256withRSA");
		sign.initVerify(cert);
		sign.verify(DatatypeConverter.parseBase64Binary(signature));

		final BasicX509Credential credential = new BasicX509Credential();
		credential.setEntityCertificate((X509Certificate) cert);
	}

}
