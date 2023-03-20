package br.com.sbk.saml2.idp.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Classe responsavel por padronizar as mensagens de response.
 *
 * @author "Arquitetura de Integracao"
 * @version 1.0.0
 * @since 1.0.0
 * @date 19 de junho de 2018
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class ResponseEntityDetails {
	
	private Integer status;
	private String date;
	private String message;
	private String details;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	public ResponseEntityDetails(Integer status, String message, String details) {
		super();
		this.status = status;
		this.date = dateFormat.format(new Date());
		this.message = message;
		this.details = details;
	}

	public Integer getStatus() {
		return status;
	}

	public String getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

}
