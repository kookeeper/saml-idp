package br.com.sbk.saml2.idp.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Classe responsavel por configurar automaticamente o Swagger da aplicacao
 *
 * @author TC013143 - Henrique Guedes
 * @version 1.0.0
 * @since 1.0.0
 * @date 7 de nov de 2019 17:11:01
 *
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger", value = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class SwaggerAutoConfiguration {

	// private static final Logger logger = LoggerFactory.getLogger(SwaggerAutoConfiguration.class);

	@Autowired
	private SwaggerProperties swaggerProperties;

	/**
	 * Metodo responsavel por retornar o Bean {@link Docket}
	 *
	 * @return {@link Docket}
	 */
	@Bean
	public Docket detalheApi() {
		final Docket docket = new Docket(DocumentationType.SWAGGER_2);
		final List<ResponseMessage> responseMessage = this.getResponseMessages();
		docket.select().apis(RequestHandlerSelectors.basePackage(this.swaggerProperties.getBasePackage())).paths(PathSelectors.any()).build().apiInfo(this.informacoesApi().build())
				.useDefaultResponseMessages(Boolean.FALSE).globalResponseMessage(RequestMethod.GET, responseMessage).globalResponseMessage(RequestMethod.POST, responseMessage)
				.globalResponseMessage(RequestMethod.PUT, responseMessage).globalResponseMessage(RequestMethod.PATCH, responseMessage)
				.globalResponseMessage(RequestMethod.DELETE, responseMessage).globalResponseMessage(RequestMethod.HEAD, responseMessage)
				.globalResponseMessage(RequestMethod.OPTIONS, responseMessage).globalResponseMessage(RequestMethod.TRACE, responseMessage);
		return docket;
	}

	/**
	 * Metodo responsavel por criar as informacoes do API.
	 *
	 * @return informacoesApi {$ApiInfoBuilder}
	 */
	private ApiInfoBuilder informacoesApi() {
		final ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		apiInfoBuilder.title(this.swaggerProperties.getTitle());
		apiInfoBuilder.description(this.swaggerProperties.getDescription());
		apiInfoBuilder.version(this.swaggerProperties.getVersion());
		apiInfoBuilder.termsOfServiceUrl(this.swaggerProperties.getTermsOfServiceUrl());
		apiInfoBuilder.license(this.swaggerProperties.getLicense());
		apiInfoBuilder.licenseUrl(this.swaggerProperties.getLicenseUrl());
		apiInfoBuilder.contact(this.contato());
		return apiInfoBuilder;
	}

	/**
	 * Metodo responsavel por criar as informacoes de Response Code/Message para as API's
	 *
	 * @return Lista de {@link ResponseMessage}
	 */
	private List<ResponseMessage> getResponseMessages() {
		final ResponseMessageBuilder responseMessageBuilder = new ResponseMessageBuilder();
		final List<ResponseMessage> responseMessages = new ArrayList<>();
		final HttpStatus[] okStatus = new HttpStatus[] { HttpStatus.OK, HttpStatus.CREATED, HttpStatus.ACCEPTED, HttpStatus.NO_CONTENT };
		for (final HttpStatus httpStatus : okStatus) {
			responseMessageBuilder.code(httpStatus.value()).message(httpStatus.getReasonPhrase());
			responseMessages.add(responseMessageBuilder.build());
		}
		final HttpStatus[] errorStatus = new HttpStatus[] { HttpStatus.BAD_REQUEST, HttpStatus.UNAUTHORIZED, HttpStatus.FORBIDDEN, HttpStatus.NOT_FOUND, HttpStatus.CONFLICT,
				HttpStatus.INTERNAL_SERVER_ERROR };
		for (final HttpStatus httpStatus : errorStatus) {
			responseMessageBuilder.code(httpStatus.value()).message(httpStatus.getReasonPhrase());
			responseMessages.add(responseMessageBuilder.build());
		}
		return responseMessages;
	}

	/**
	 * Metodo responsavel por criar um contato
	 *
	 * @return contato {$Contact}
	 */
	private Contact contato() {
		return new Contact(this.swaggerProperties.getContactName(), this.swaggerProperties.getContactUrl(), this.swaggerProperties.getContactMail());
	}

}
