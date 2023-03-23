package br.com.sbk.saml2.idp.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.sax.SAXSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import br.com.sbk.saml2.idp.entity.metadados.sp.EntityDescriptor;
import br.com.sbk.saml2.idp.utils.NamespaceFilterXMLReader;

@Service
public class MetadataService {

	public static final Logger logger = LoggerFactory.getLogger(MetadataService.class);

	public String recuperarCertificado(final String entityId) throws FileNotFoundException {
		return this.recuperarEntityDescriptor(entityId).getSPSSODescriptor().getKeyDescriptor().get(0).getKeyInfo().getX509Data().getX509Certificate();
	}

	public EntityDescriptor recuperarEntityDescriptor(final String entityId) throws FileNotFoundException {
		try {
			XMLReader reader = new NamespaceFilterXMLReader();
			InputSource is = new InputSource(new FileReader(new File("/home/sergio/sbk/metadados/SAMLSP-00D8Y000001ZF0a.xml")));
			SAXSource ss = new SAXSource(reader, is);
			JAXBContext jaxbContext = JAXBContext.newInstance(EntityDescriptor.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			EntityDescriptor metadados = (EntityDescriptor) unmarshaller.unmarshal(ss);
			return metadados;
		} catch (JAXBException | SAXException | ParserConfigurationException e) {
			throw new IllegalArgumentException("Erro ao tentar recuperar EntityDescriptor: " + e.getLocalizedMessage(), e);
		}
	}
}