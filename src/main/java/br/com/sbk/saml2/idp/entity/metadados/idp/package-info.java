@XmlSchema(
    namespace = "urn:oasis:names:tc:SAML:2.0:metadata",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(prefix="md", namespaceURI="urn:oasis:names:tc:SAML:2.0:metadata"),
        @XmlNs(prefix="ds", namespaceURI="http://www.w3.org/2000/09/xmldsig#")
    }
)  

package br.com.sbk.saml2.idp.entity.metadados.idp;
import javax.xml.bind.annotation.*;