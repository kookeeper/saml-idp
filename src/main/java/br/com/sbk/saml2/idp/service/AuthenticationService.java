package br.com.sbk.saml2.idp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.sbk.saml2.idp.dto.SAMLAttribute;

@Service
public class AuthenticationService {

	private final Map<String, List<String>> attributes = new TreeMap<>();

	public static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

	private String uid;

	@Autowired
	private IdentityService servicoIdentidade;

	public List<SAMLAttribute> authenticate(final Authentication authentication, final String identity,
			final String propertiesList, final String optRadio) {

		String dominio = null;
		final String login = (String) authentication.getPrincipal();
		final String senha = (String) authentication.getCredentials();
		final Holder<Boolean> autenticado = new Holder<>();
		final Holder<String> grupoEmpresa = new Holder<>();
		final Holder<String> mensagem = new Holder<>();

		AuthenticationService.logger.info("Autenticando usuario [authenticate] >>>> " + authentication.getPrincipal());

		// Autentica Usuario
		if (optRadio != null) {
			dominio = optRadio;
		} else {
			dominio = "MV";
		}

		this.servicoIdentidade.autenticarUsuario(dominio, login, senha, autenticado, grupoEmpresa, mensagem);

		final List<SAMLAttribute> attributes = new ArrayList<>();

		attributes.add(new SAMLAttribute("autenticado", Collections.singletonList(autenticado.value.toString())));
		if (autenticado.value) {
			AuthenticationService.logger
					.info("Usuario autenticado [authenticate] >>>> " + authentication.getPrincipal());
			attributes.addAll(this.attributes(authentication, identity, propertiesList, grupoEmpresa.value, mensagem));
		} else {
			AuthenticationService.logger.info("Usuario rejeitado [authenticate] >>>> " + authentication.getPrincipal());
		}

		return attributes;

	}

	private List<SAMLAttribute> attributes(final Authentication authentication, final String identity,
			final String propertiesList, final String grupoEmpresa, final Holder<String> mensagem) {

		this.resetAttributes();

		final List<String> idpProp = new ArrayList<>();
		if (propertiesList != null) {
			final List<String> attributeItems = Arrays.asList(propertiesList.split("\\s*,\\s*"));
			for (final String attribute : attributeItems) {
				if (attribute.split("\\.")[0].equalsIgnoreCase("idp")) {
					idpProp.add(attribute.split("\\.")[1]);
				}
			}

			if (idpProp != null) {
				if (idpProp.size() > 0) {
					this.obterKeyValuesIdpProp(idpProp, authentication, identity, propertiesList, grupoEmpresa,
							mensagem);
				}

			}
		}
		// Provide the ability to limit the list attributes returned to the SP
		return this.attributes.entrySet().stream()
				.filter(entry -> !entry.getValue().stream().allMatch(StringUtils::isEmpty))
				.map(entry -> entry.getKey().equals("urn:mace:dir:attribute-def:uid")
						? new SAMLAttribute(entry.getKey(), Collections.singletonList(this.uid))
						: new SAMLAttribute(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}

	private void obterKeyValuesIdpProp(final List<String> idpProp, final Authentication authentication,
			final String identity, final String propertiesList, final String grupoEmpresa,
			final Holder<String> mensagem) {
		for (final String idp : idpProp) {
			if (idp.equalsIgnoreCase("uid")) {
				this.putAttribute("urn:mace:dir:attribute-def:" + idp, authentication.getName());
			}
			if (idp.equalsIgnoreCase("empresa")) {
				this.putAttribute("urn:mace:dir:attribute-def:" + idp, String.valueOf(grupoEmpresa));
			}
		}
	}

	private void resetAttributes() {
		this.attributes.clear();
	}

	private void putAttribute(final String key, final String... values) {
		this.attributes.put(key, Arrays.asList(values));
	}

	public Map<String, List<String>> getAttributes() {
		return this.attributes;
	}
}