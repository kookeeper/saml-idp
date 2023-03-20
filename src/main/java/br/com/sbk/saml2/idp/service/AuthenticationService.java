package br.com.sbk.saml2.idp.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	private final Map<String, List<String>> attributes = new TreeMap<>();

	public static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

	public Map<String, List<String>> getAttributes() {
		return this.attributes;
	}
}