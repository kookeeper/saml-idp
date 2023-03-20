package br.com.sbk.saml2.idp.entity.metadados.sp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "EntityDescriptor")
public class EntityDescriptor
{
	@XmlElement(name = "SPSSODescriptor", required = false)
    private SPSSODescriptor SPSSODescriptor;

	@XmlAttribute(name = "xmlns")
    private String xmlns;

	@XmlAttribute(name = "validUntil")
    private String validUntil;

	@XmlAttribute(name = "entityID")
    private String entityID;

    public SPSSODescriptor getSPSSODescriptor ()
    {
        return SPSSODescriptor;
    }

    public void setSPSSODescriptor (SPSSODescriptor SPSSODescriptor)
    {
        this.SPSSODescriptor = SPSSODescriptor;
    }

    public String getXmlns ()
    {
        return xmlns;
    }

    public void setXmlns (String xmlns)
    {
        this.xmlns = xmlns;
    }

    public String getValidUntil ()
    {
        return validUntil;
    }

    public void setValidUntil (String validUntil)
    {
        this.validUntil = validUntil;
    }

    public String getEntityID ()
    {
        return entityID;
    }

    public void setEntityID (String entityID)
    {
        this.entityID = entityID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SPSSODescriptor = "+SPSSODescriptor+", xmlns = "+xmlns+", validUntil = "+validUntil+", entityID = "+entityID+"]";
    }
}

//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "", propOrder = { "sPSSODescriptor", "organization" })
//@XmlRootElement(name = "EntityDescriptor")
//public class EntityDescriptor {
//
//	@XmlElement(name = "SPSSODescriptor", required = false)
//	protected EntityDescriptor.SPSSODescriptor sPSSODescriptor;
//
//	@XmlElement(name = "Organization", required = false)
//	protected EntityDescriptor.Organization organization;
//
//	public EntityDescriptor.SPSSODescriptor getsPSSODescriptor() {
//		return sPSSODescriptor;
//	}
//
//	public void setsPSSODescriptor(EntityDescriptor.SPSSODescriptor sPSSODescriptor) {
//		this.sPSSODescriptor = sPSSODescriptor;
//	}
//
//	public EntityDescriptor.Organization getOrganization() {
//		return organization;
//	}
//
//	public void setOrganization(EntityDescriptor.Organization organization) {
//		this.organization = organization;
//	}
//
//	@XmlAccessorType(XmlAccessType.FIELD)
//	@XmlType(name = "", propOrder = { "keyDescriptor", "singleLogoutService", "assertionConsumerService" })
//	public static class SPSSODescriptor {
//
//		@XmlElement(name = "KeyDescriptor", required = false)
//		protected KeyDescriptor keyDescriptor;
//
//		@XmlElement(name = "SingleLogoutService", required = false)
//		protected String singleLogoutService;
//
//		@XmlElement(name = "AssertionConsumerService", required = false)
//		protected String assertionConsumerService;
//
//		public KeyDescriptor getKeyDescriptor() {
//			return this.keyDescriptor;
//		}
//
//		public void setKeyDescriptor(KeyDescriptor keyDescriptor) {
//			this.keyDescriptor = keyDescriptor;
//		}
//
//		public String getSingleLogoutService() {
//			return singleLogoutService;
//		}
//
//		public void setSingleLogoutService(String singleLogoutService) {
//			this.singleLogoutService = singleLogoutService;
//		}
//
//		public String getAssertionConsumerService() {
//			return assertionConsumerService;
//		}
//
//		public void setAssertionConsumerService(String assertionConsumerService) {
//			this.assertionConsumerService = assertionConsumerService;
//		}
//
//	}
//
//	@XmlAccessorType(XmlAccessType.FIELD)
//	@XmlType(name = "", propOrder = { "organizationName", "organizationURL" })
//	public static class Organization {
//
//		@XmlElement(name = "OrganizationName", required = false)
//		protected String organizationName;
//
//		@XmlElement(name = "OrganizationURL", required = false)
//		protected String organizationURL;
//
//		public String getOrganizationName() {
//			return organizationName;
//		}
//
//		public void setOrganizationName(String organizationName) {
//			this.organizationName = organizationName;
//		}
//
//		public String getOrganizationURL() {
//			return organizationURL;
//		}
//
//		public void setOrganizationURL(String organizationURL) {
//			this.organizationURL = organizationURL;
//		}
//
//	}
//
//	@XmlAccessorType(XmlAccessType.FIELD)
//	@XmlType(name = "", propOrder = { "keyInfo" })
//	public static class KeyDescriptor {
//
//		@XmlElement(name = "KeyInfo", required = false)
//		protected KeyInfo keyInfo;
//
//		public KeyInfo getKeyInfo() {
//			return keyInfo;
//		}
//
//		public void setKeyInfo(KeyInfo keyInfo) {
//			this.keyInfo = keyInfo;
//		}
//
//	}
//
//	@XmlAccessorType(XmlAccessType.FIELD)
//	@XmlType(name = "", propOrder = { "x509Data" })
//	public static class KeyInfo {
//
//		@XmlElement(name = "X509Data", required = false)
//		protected X509Data x509Data;
//
//		public X509Data getX509Data() {
//			return x509Data;
//		}
//
//		public void setX509Data(X509Data x509Data) {
//			this.x509Data = x509Data;
//		}
//
//	}
//
//	@XmlAccessorType(XmlAccessType.FIELD)
//	@XmlType(name = "", propOrder = { "x509Certificate" })
//	public static class X509Data {
//
//		@XmlElement(name = "X509Certificate", required = false)
//		protected String x509Certificate;
//
//		public String getX509Certificate() {
//			return x509Certificate;
//		}
//
//		public void setX509Certificate(String x509Certificate) {
//			this.x509Certificate = x509Certificate;
//		}
//
//	}
//
//}
