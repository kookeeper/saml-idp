package br.com.sbk.saml2.idp.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Classe responsavel por armazenar as propriedades da configuracao do SwaggerUI
 *
 * @author TC013143 - Henrique Guedes
 * @version 1.0.0
 * @since 1.0.0
 * @date 7 de nov de 2019 17:10:30
 *
 */
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

	private boolean enabled = true;
	private String basePackage = "br.com.gpa";
	private String group = "gpa-desenv";
	@Value(value = "${spring.application.name:}")
	private String title;
	@Value(value = "${info.description:}")
	private String description;
	@Value(value = "${info.version:}")
	private String version;
	private String termsOfServiceUrl = "";
	private String license = "";
	private String licenseUrl = "";
	private String excludes = "/error*";
	private String contactName = "Arquitetura de integracao";
	private String contactUrl = "http://gpabr.com";
	private String contactMail = "TI-ARQUITETURA-PROJETOS-INTEGRACAO@cs.grupopaodeacucar.com.br";

	/**
	 * @return "get" da propriedade enabled
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * @param enabled "set" da propriedade enabled
	 */
	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return "get" da propriedade basePackage
	 */
	public String getBasePackage() {
		return this.basePackage;
	}

	/**
	 * @param basePackage "set" da propriedade basePackage
	 */
	public void setBasePackage(final String basePackage) {
		this.basePackage = basePackage;
	}

	/**
	 * @return "get" da propriedade group
	 */
	public String getGroup() {
		return this.group;
	}

	/**
	 * @param group "set" da propriedade group
	 */
	public void setGroup(final String group) {
		this.group = group;
	}

	/**
	 * @return "get" da propriedade title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @param title "set" da propriedade title
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * @return "get" da propriedade description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description "set" da propriedade description
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return "get" da propriedade version
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @param version "set" da propriedade version
	 */
	public void setVersion(final String version) {
		this.version = version;
	}

	/**
	 * @return "get" da propriedade termsOfServiceUrl
	 */
	public String getTermsOfServiceUrl() {
		return this.termsOfServiceUrl;
	}

	/**
	 * @param termsOfServiceUrl "set" da propriedade termsOfServiceUrl
	 */
	public void setTermsOfServiceUrl(final String termsOfServiceUrl) {
		this.termsOfServiceUrl = termsOfServiceUrl;
	}

	/**
	 * @return "get" da propriedade license
	 */
	public String getLicense() {
		return this.license;
	}

	/**
	 * @param license "set" da propriedade license
	 */
	public void setLicense(final String license) {
		this.license = license;
	}

	/**
	 * @return "get" da propriedade licenseUrl
	 */
	public String getLicenseUrl() {
		return this.licenseUrl;
	}

	/**
	 * @param licenseUrl "set" da propriedade licenseUrl
	 */
	public void setLicenseUrl(final String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}

	/**
	 * @return "get" da propriedade excludes
	 */
	public String getExcludes() {
		return this.excludes;
	}

	/**
	 * @param excludes "set" da propriedade excludes
	 */
	public void setExcludes(final String excludes) {
		this.excludes = excludes;
	}

	/**
	 * @return "get" da propriedade contactName
	 */
	public String getContactName() {
		return this.contactName;
	}

	/**
	 * @param contactName "set" da propriedade contactName
	 */
	public void setContactName(final String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return "get" da propriedade contactUrl
	 */
	public String getContactUrl() {
		return this.contactUrl;
	}

	/**
	 * @param contactUrl "set" da propriedade contactUrl
	 */
	public void setContactUrl(final String contactUrl) {
		this.contactUrl = contactUrl;
	}

	/**
	 * @return "get" da propriedade contactMail
	 */
	public String getContactMail() {
		return this.contactMail;
	}

	/**
	 * @param contactMail "set" da propriedade contactMail
	 */
	public void setContactMail(final String contactMail) {
		this.contactMail = contactMail;
	}

}
