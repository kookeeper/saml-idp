package br.com.sbk.saml2.idp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sbk.saml2.idp.service.MetadataService;

@RestController
public class MetadadosController {

	public static final Logger logger = LoggerFactory.getLogger(MetadadosController.class);

	private final MetadataService metadataService;
	
	public MetadadosController(final MetadataService metadataService) {
		this.metadataService = metadataService;
	}
	
	@GetMapping("/metadados")
	public ResponseEntity<byte[]> metadados() throws Exception {
		
		final byte[] isr = metadataService.getMetadados();
		
		final HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentLength(isr.length);
		respHeaders.setContentType(new MediaType("text", "xml"));
		respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=metadados.xml");

		return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
	}

}
