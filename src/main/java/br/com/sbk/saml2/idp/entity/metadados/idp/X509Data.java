package br.com.sbk.saml2.idp.entity.metadados.idp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class X509Data
{
	@XmlElement(name = "X509Certificate", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private String X509Certificate;

    public String getX509Certificate ()
    {
        return X509Certificate;
    }

    public void setX509Certificate (String X509Certificate)
    {
        this.X509Certificate = X509Certificate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [X509Certificate = "+X509Certificate+"]";
    }
}
