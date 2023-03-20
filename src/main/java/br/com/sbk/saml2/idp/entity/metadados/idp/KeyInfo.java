package br.com.sbk.saml2.idp.entity.metadados.idp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class KeyInfo
{
	@XmlAttribute(name = "xmlns")
    private String xmlns;

    @XmlElement(name = "X509Data", required = false, namespace = "http://www.w3.org/2000/09/xmldsig#")
    private X509Data X509Data;

    public String getXmlns ()
    {
        return xmlns;
    }

    public void setXmlns (String xmlns)
    {
        this.xmlns = xmlns;
    }

    public X509Data getX509Data ()
    {
        return X509Data;
    }

    public void setX509Data (X509Data X509Data)
    {
        this.X509Data = X509Data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [xmlns = "+xmlns+", X509Data = "+X509Data+"]";
    }
}