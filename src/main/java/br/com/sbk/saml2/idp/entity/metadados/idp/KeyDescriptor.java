package br.com.sbk.saml2.idp.entity.metadados.idp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class KeyDescriptor {
	@XmlElement(name = "KeyInfo", required = false, namespace = "http://www.w3.org/2000/09/xmldsig#")
	private KeyInfo KeyInfo;

	@XmlAttribute(name = "use")
	private String use;

	public KeyInfo getKeyInfo() {
		return KeyInfo;
	}

	public void setKeyInfo(KeyInfo KeyInfo) {
		this.KeyInfo = KeyInfo;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	@Override
	public String toString() {
		return "ClassPojo [KeyInfo = " + KeyInfo + ", use = " + use + "]";
	}
}
