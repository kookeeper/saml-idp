package br.com.sbk.saml2.idp.service;

import javax.xml.ws.Holder;

import org.springframework.stereotype.Service;

import br.com.sbk.saml2.idp.repository.intsoa.ConIdRepository;

@Service
public class IdentityService {
	
	private ConIdRepository repository;

	public IdentityService(final ConIdRepository repository) {
		this.repository = repository;
	}

	public void autenticarUsuario(String dominio, String login, String senha, Holder<Boolean> autenticado,
			Holder<String> grupoEmpresa, Holder<String> mensagem) {
		autenticado.value = senha.equals(repository.findByUserName(login).getPasswordHash());
	}

}
