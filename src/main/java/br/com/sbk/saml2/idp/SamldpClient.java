package br.com.sbk.saml2.idp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "br.com.sbk.saml2.idp" })
public class SamldpClient {

	public static void main(String[] args) {
		SpringApplication.run(SamldpClient.class, args);
	}

}
