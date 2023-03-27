package br.com.sbk.saml2.idp.config;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.opensaml.common.binding.decoding.URIComparator;
import org.opensaml.common.binding.security.IssueInstantRule;
import org.opensaml.common.binding.security.MessageReplayRule;
import org.opensaml.saml2.binding.decoding.HTTPPostDecoder;
import org.opensaml.saml2.binding.decoding.HTTPRedirectDeflateDecoder;
import org.opensaml.saml2.binding.encoding.HTTPPostSimpleSignEncoder;
import org.opensaml.util.storage.MapBasedStorageService;
import org.opensaml.util.storage.ReplayCache;
import org.opensaml.ws.security.provider.BasicSecurityPolicy;
import org.opensaml.ws.security.provider.StaticSecurityPolicyResolver;
import org.opensaml.xml.parse.StaticBasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.security.saml.util.VelocityFactory;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.sbk.saml2.idp.service.SAMLMessageService;

@Configuration
public class WebSecurityConfigurer implements WebMvcConfigurer {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	@Autowired
	public SAMLMessageService samlMessageHandler(@Value("${idp.clock_skew}") int clockSkew,
			@Value("${idp.expires}") int expires, @Value("${idp.base_url}") String idpBaseUrl,
			@Value("${idp.compare_endpoints}") boolean compareEndpoints, IdpConfiguration idpConfiguration,
			JKSKeyManager keyManager) throws XMLParserException, URISyntaxException {
		StaticBasicParserPool parserPool = new StaticBasicParserPool();
		BasicSecurityPolicy securityPolicy = new BasicSecurityPolicy();
		securityPolicy.getPolicyRules().addAll(Arrays.asList(new IssueInstantRule(clockSkew, expires),
				new MessageReplayRule(new ReplayCache(new MapBasedStorageService(), 14400000))));

		HTTPRedirectDeflateDecoder httpRedirectDeflateDecoder = new HTTPRedirectDeflateDecoder(parserPool);
		HTTPPostDecoder httpPostDecoder = new HTTPPostDecoder(parserPool);
		if (!compareEndpoints) {
			URIComparator noopComparator = (uri1, uri2) -> true;
			httpPostDecoder.setURIComparator(noopComparator);
			httpRedirectDeflateDecoder.setURIComparator(noopComparator);
		}

		parserPool.initialize();
		HTTPPostSimpleSignEncoder httpPostSimpleSignEncoder = new HTTPPostSimpleSignEncoder(VelocityFactory.getEngine(),
				"/templates/saml2-post-simplesign-binding.vm", true);

		return new SAMLMessageService(keyManager, Arrays.asList(httpRedirectDeflateDecoder, httpPostDecoder),
				httpPostSimpleSignEncoder, new StaticSecurityPolicyResolver(securityPolicy), idpConfiguration,
				idpBaseUrl);
	}

	@Autowired
	@Bean
	public JKSKeyManager keyManager(@Value("${idp.entity_id}") String idpEntityId,
			@Value("${idp.private_key}") String idpPrivateKey, @Value("${idp.certificate}") String idpCertificate,
			@Value("${idp.passphrase}") String idpPassphrase) throws InvalidKeySpecException, CertificateException,
			NoSuchAlgorithmException, KeyStoreException, IOException, XMLStreamException {
		KeyStore keyStore = KeyStoreLocator.createKeyStore(idpPassphrase);
		KeyStoreLocator.addPrivateKey(keyStore, idpEntityId, idpPrivateKey, idpCertificate, idpPassphrase);
		Map<String, String> keyPasswords = Collections.singletonMap(idpEntityId, idpPassphrase);

		JKSKeyManager keyManager = new JKSKeyManager(keyStore, keyPasswords, idpEntityId);
			try {
				keyManager.resolveSingle(new CriteriaSet(new EntityIDCriteria(idpEntityId)));
				keyManager.getKeyStore().getEntry(idpEntityId, new KeyStore.PasswordProtection(keyPasswords.get(idpEntityId).toCharArray()));
			} catch (UnrecoverableEntryException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		return keyManager;
	}

	@Bean
	public ServletContextInitializer servletContextInitializer() {
		// otherwise the two localhost instances override each other session
		return servletContext -> servletContext.getSessionCookieConfig().setName("mujinaIdpSessionId");
	}

}
