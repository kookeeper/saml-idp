package br.com.sbk.saml2.idp.entity.metadados.idp;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class IDPSSODescriptor {
	@XmlAttribute(name = "WantAuthnRequestsSigned")
	private String WantAuthnRequestsSigned;
	
	@XmlAttribute(name = "protocolSupportEnumeration")
	private String protocolSupportEnumeration;

	@XmlElement(name = "KeyDescriptor", required = false)
	private List<KeyDescriptor> KeyDescriptor;

	@XmlElement(name = "SingleSignOnService", required = false)
	private SingleSignOnService SingleSignOnService;

	public SingleSignOnService getSingleSignOnService() {
		return SingleSignOnService;
	}

	public void setSingleSignOnService(SingleSignOnService singleSignOnService) {
		SingleSignOnService = singleSignOnService;
	}

	public String getProtocolSupportEnumeration() {
		return protocolSupportEnumeration;
	}

	public void setProtocolSupportEnumeration(String protocolSupportEnumeration) {
		this.protocolSupportEnumeration = protocolSupportEnumeration;
	}

	public String getWantAuthnRequestsSigned() {
		return WantAuthnRequestsSigned;
	}

	public void setWantAuthnRequestsSigned(String wantAuthnRequestsSigned) {
		WantAuthnRequestsSigned = wantAuthnRequestsSigned;
	}

	public List<KeyDescriptor> getKeyDescriptor() {
		return KeyDescriptor;
	}

	public void setKeyDescriptor(List<KeyDescriptor> keyDescriptor) {
		KeyDescriptor = keyDescriptor;
	}

	@Override
	public String toString() {
		return "IDPSSODescriptor [protocolSupportEnumeration=" + protocolSupportEnumeration
				+ ", WantAuthnRequestsSigned=" + WantAuthnRequestsSigned + ", KeyDescriptor=" + KeyDescriptor
				+ ", SingleSignOnService=" + SingleSignOnService + "]";
	}

}
