package br.com.sbk.saml2.idp.entity.metadados.sp;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "", propOrder = { "keyDescriptor", "singleLogoutService", "assertionConsumerService" })
public class SPSSODescriptor {
	@XmlAttribute(name = "xmlns")
	private String xmlns;

	@XmlAttribute(name = "protocolSupportEnumeration")
	private String protocolSupportEnumeration;

	@XmlAttribute(name = "AuthnRequestsSigned")
	private String AuthnRequestsSigned;

	@XmlElement(name = "KeyDescriptor", required = false)
	private List<KeyDescriptor> KeyDescriptor;

	@XmlAttribute(name = "validUntil")
	private String validUntil;

	@XmlAttribute(name = "WantAssertionsSigned")
	private String WantAssertionsSigned;

	@XmlElement(name = "AssertionConsumerService", required = false)
	private AssertionConsumerService AssertionConsumerService;

	public List<KeyDescriptor> getKeyDescriptor() {
		return KeyDescriptor;
	}

	public void setKeyDescriptor(List<KeyDescriptor> keyDescriptor) {
		KeyDescriptor = keyDescriptor;
	}

	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public String getProtocolSupportEnumeration() {
		return protocolSupportEnumeration;
	}

	public void setProtocolSupportEnumeration(String protocolSupportEnumeration) {
		this.protocolSupportEnumeration = protocolSupportEnumeration;
	}

	public String getAuthnRequestsSigned() {
		return AuthnRequestsSigned;
	}

	public void setAuthnRequestsSigned(String AuthnRequestsSigned) {
		this.AuthnRequestsSigned = AuthnRequestsSigned;
	}

	public String getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(String validUntil) {
		this.validUntil = validUntil;
	}

	public String getWantAssertionsSigned() {
		return WantAssertionsSigned;
	}

	public void setWantAssertionsSigned(String WantAssertionsSigned) {
		this.WantAssertionsSigned = WantAssertionsSigned;
	}

	public AssertionConsumerService getAssertionConsumerService() {
		return AssertionConsumerService;
	}

	public void setAssertionConsumerService(AssertionConsumerService AssertionConsumerService) {
		this.AssertionConsumerService = AssertionConsumerService;
	}

	@Override
	public String toString() {
		return "ClassPojo [xmlns = " + xmlns + ", protocolSupportEnumeration = " + protocolSupportEnumeration
				+ ", AuthnRequestsSigned = " + AuthnRequestsSigned + ", KeyDescriptor = " + KeyDescriptor
				+ ", validUntil = " + validUntil + ", WantAssertionsSigned = " + WantAssertionsSigned
				+ ", AssertionConsumerService = " + AssertionConsumerService + "]";
	}
}
