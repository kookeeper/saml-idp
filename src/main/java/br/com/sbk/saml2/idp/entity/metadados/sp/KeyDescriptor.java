package br.com.sbk.saml2.idp.entity.metadados.sp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class KeyDescriptor
{
	@XmlElement(name = "KeyInfo", required = false)
    private KeyInfo KeyInfo;

    @XmlAttribute(name = "use")
    private String use;

    @XmlElement(name = "EncryptionMethod", required = false)
    private EncryptionMethod[] EncryptionMethod;

    public KeyInfo getKeyInfo ()
    {
        return KeyInfo;
    }

    public void setKeyInfo (KeyInfo KeyInfo)
    {
        this.KeyInfo = KeyInfo;
    }

    public String getUse ()
    {
        return use;
    }

    public void setUse (String use)
    {
        this.use = use;
    }

    public EncryptionMethod[] getEncryptionMethod ()
    {
        return EncryptionMethod;
    }

    public void setEncryptionMethod (EncryptionMethod[] EncryptionMethod)
    {
        this.EncryptionMethod = EncryptionMethod;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [KeyInfo = "+KeyInfo+", use = "+use+", EncryptionMethod = "+EncryptionMethod+"]";
    }
}
