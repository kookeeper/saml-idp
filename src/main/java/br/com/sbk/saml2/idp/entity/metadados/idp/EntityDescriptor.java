package br.com.sbk.saml2.idp.entity.metadados.idp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "EntityDescriptor")
public class EntityDescriptor {
	@XmlElement(name = "IDPSSODescriptor", required = false)
	private IDPSSODescriptor IDPSSODescriptor;

	@XmlAttribute(name = "xmlns")
	private String xmlns;

	@XmlAttribute(name = "entityID")
	private String entityID;

	public IDPSSODescriptor getIDPSSODescriptor() {
		return IDPSSODescriptor;
	}

	public void setIDPSSODescriptor(IDPSSODescriptor SPSSODescriptor) {
		this.IDPSSODescriptor = SPSSODescriptor;
	}

	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public String getEntityID() {
		return entityID;
	}

	public void setEntityID(String entityID) {
		this.entityID = entityID;
	}

	@Override
	public String toString() {
		return "ClassPojo [SPSSODescriptor = " + IDPSSODescriptor + ", xmlns = " + xmlns + ", entityID = " + entityID
				+ "]";
	}
}
