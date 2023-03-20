package br.com.sbk.saml2.idp.entity.metadados.idp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class SingleSignOnService
{
	@XmlAttribute(name = "Binding")
    private String Binding;

	@XmlAttribute(name = "index")
    private String index;

	@XmlAttribute(name = "Location")
    private String Location;
	
	@XmlAttribute(name = "isDefault")
    private String isDefault;
	
    public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getBinding ()
    {
        return Binding;
    }

    public void setBinding (String Binding)
    {
        this.Binding = Binding;
    }

    public String getIndex ()
    {
        return index;
    }

    public void setIndex (String index)
    {
        this.index = index;
    }

    public String getLocation ()
    {
        return Location;
    }

    public void setLocation (String Location)
    {
        this.Location = Location;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Binding = "+Binding+", index = "+index+", Location = "+Location+"]";
    }
}
