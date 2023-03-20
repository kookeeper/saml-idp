package br.com.sbk.saml2.idp.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidAuthenticationException extends AuthenticationException {

  /**
	 * 
	 */
	private static final long serialVersionUID = -9176795496480439967L;

public InvalidAuthenticationException(String msg) {
    super(msg);
  }
}
