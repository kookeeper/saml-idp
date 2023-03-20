package br.com.sbk.saml2.idp.entity.metadados.sp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class EncryptionMethod
{
	@XmlAttribute(name = "Algorithm")
    private String Algorithm;

    public String getAlgorithm ()
    {
        return Algorithm;
    }

    public void setAlgorithm (String Algorithm)
    {
        this.Algorithm = Algorithm;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Algorithm = "+Algorithm+"]";
    }
}