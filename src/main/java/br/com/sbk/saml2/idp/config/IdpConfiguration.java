package br.com.sbk.saml2.idp.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.stereotype.Component;

import br.com.sbk.saml2.idp.execution.AuthenticationMethod;
import br.com.sbk.saml2.idp.execution.FederatedUserAuthenticationToken;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class IdpConfiguration extends SharedConfiguration {

	private String defaultEntityId;
	private List<FederatedUserAuthenticationToken> users = new ArrayList<>();
	private String acsEndpoint;
	private AuthenticationMethod authenticationMethod;
	private AuthenticationMethod defaultAuthenticationMethod;
	private final String idpPrivateKey;
	private final String idpCertificate;
	
	@Autowired
	public IdpConfiguration(JKSKeyManager keyManager, @Value("${idp.entity_id}") String defaultEntityId,
			@Value("${idp.private_key}") String idpPrivateKey, @Value("${idp.certificate}") String idpCertificate,
			@Value("${idp.auth_method}") String authMethod, @Value("${idp.passphrase}") String passphrase) {
		super(keyManager, passphrase);
		this.defaultEntityId = defaultEntityId;
		this.idpPrivateKey = idpPrivateKey;
		this.idpCertificate = idpCertificate;
		this.defaultAuthenticationMethod = AuthenticationMethod.valueOf(authMethod);
		reset();
	}

	@Override
	public void reset() {
		setEntityId(defaultEntityId);
		resetKeyStore(defaultEntityId, idpPrivateKey, idpCertificate);
		setAcsEndpoint(null);
		setAuthenticationMethod(this.defaultAuthenticationMethod);
		setSignatureAlgorithm(getDefaultSignatureAlgorithm());
	}


	public void setAcsEndpoint(String acsEndpoint) {
		this.acsEndpoint = acsEndpoint;
	}

	public void setAuthenticationMethod(AuthenticationMethod authenticationMethod) {
		this.authenticationMethod = authenticationMethod;
	}

	public String getAcsEndpoint() {
		return this.acsEndpoint;
	}

	public AuthenticationMethod getAuthenticationMethod() {
		return this.authenticationMethod;
	}

	public AuthenticationMethod getDefaultAuthenticationMethod() {
		return this.defaultAuthenticationMethod;
	}

	public String getIdpPrivateKey() {
		return this.idpPrivateKey;
	}

	public String getIdpCertificate() {
		return this.idpCertificate;
	}

	public List<FederatedUserAuthenticationToken> getUsers() {
		return this.users;
	}
}
