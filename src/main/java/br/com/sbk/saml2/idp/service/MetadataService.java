package br.com.sbk.saml2.idp.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.sax.SAXSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import br.com.sbk.saml2.idp.config.IdpConfiguration;
import br.com.sbk.saml2.idp.entity.metadados.idp.IDPSSODescriptor;
import br.com.sbk.saml2.idp.entity.metadados.idp.KeyDescriptor;
import br.com.sbk.saml2.idp.entity.metadados.idp.KeyInfo;
import br.com.sbk.saml2.idp.entity.metadados.idp.SingleSignOnService;
import br.com.sbk.saml2.idp.entity.metadados.idp.X509Data;
import br.com.sbk.saml2.idp.entity.metadados.sp.EntityDescriptor;
import br.com.sbk.saml2.idp.utils.NamespaceFilterXMLReader;

@Service
public class MetadataService {

	public static final Logger logger = LoggerFactory.getLogger(MetadataService.class);
	
	private final IdpConfiguration idp;
	private final String metadadosFolder;
	private final Map<String, EntityDescriptor> entityDescriptors = new HashMap<>();

	public MetadataService(final IdpConfiguration idp, @Value("${metadados.folder}") final String metadadosFolder) throws FileNotFoundException, SAXException, ParserConfigurationException, JAXBException {
		this.idp = idp;
		this.metadadosFolder = metadadosFolder;
		loadEntityDescriptors();
	}

	public String recuperarCertificado(final String entityId) throws FileNotFoundException {
		return this.recuperarEntityDescriptor(entityId).getSPSSODescriptor().getKeyDescriptor().get(0).getKeyInfo()
				.getX509Data().getX509Certificate();
	}
	
	public EntityDescriptor recuperarEntityDescriptor(final String entityId) {
		return entityDescriptors.get(entityId);
	}

	public EntityDescriptor recuperarEntityDescriptor_(final String entityId) throws FileNotFoundException {
		try {
			XMLReader reader = new NamespaceFilterXMLReader();
			InputSource is = new InputSource(
					new FileReader(new File("/home/sergio/sbk/metadados/SAMLSP-00D8Y000001ZF0a.xml")));
			SAXSource ss = new SAXSource(reader, is);
			JAXBContext jaxbContext = JAXBContext.newInstance(EntityDescriptor.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			EntityDescriptor metadados = (EntityDescriptor) unmarshaller.unmarshal(ss);
			return metadados;
		} catch (JAXBException | SAXException | ParserConfigurationException e) {
			throw new IllegalArgumentException("Erro ao tentar recuperar EntityDescriptor: " + e.getLocalizedMessage(),
					e);
		}
	}

	public byte[] getMetadados() throws JAXBException {

		logger.info("Obtendo dados de configuração do IDP");

		br.com.sbk.saml2.idp.entity.metadados.idp.EntityDescriptor entityDescriptor = new br.com.sbk.saml2.idp.entity.metadados.idp.EntityDescriptor();
		IDPSSODescriptor iDPSSODescriptor = new IDPSSODescriptor();
		KeyDescriptor keyDescriptor = new KeyDescriptor();
		List<KeyDescriptor> keyDescriptorList = new ArrayList<>();
		KeyInfo keyInfo = new KeyInfo();
		SingleSignOnService singleSignOnService = new SingleSignOnService();
		X509Data x509Data = new X509Data();

		entityDescriptor.setEntityID(idp.getEntityId());

		iDPSSODescriptor.setWantAuthnRequestsSigned("false");
		iDPSSODescriptor.setProtocolSupportEnumeration("urn:oasis:names:tc:SAML:2.0:protocol");

		keyDescriptor.setUse("signing");

		keyInfo.setXmlns("http://www.w3.org/2000/09/xmldsig#");

		x509Data.setX509Certificate(idp.getIdpCertificate());
		keyInfo.setX509Data(x509Data);
		keyDescriptor.setKeyInfo(keyInfo);
		keyDescriptorList.add(keyDescriptor);
		iDPSSODescriptor.setKeyDescriptor(keyDescriptorList);

		singleSignOnService.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-REDIRECT");
		singleSignOnService.setIndex("0");
		singleSignOnService.setLocation("http://localhost:8080");
		singleSignOnService.setIsDefault("true");
		iDPSSODescriptor.setSingleSignOnService(singleSignOnService);

		entityDescriptor.setIDPSSODescriptor(iDPSSODescriptor);

		JAXBContext jaxbContext = JAXBContext.newInstance(EntityDescriptor.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);

		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(entityDescriptor, sw);

		return sw.toString().getBytes();
	}
	
	private void loadEntityDescriptors() throws FileNotFoundException, SAXException, ParserConfigurationException, JAXBException {
		if (!new File(this.metadadosFolder).isDirectory()) {
			throw new IllegalArgumentException("Pasta configurada não existe: '" + this.metadadosFolder + "'.");
		}
		
		for (File file : new File(this.metadadosFolder).listFiles()) {
			final EntityDescriptor e = readFile(file);
			logger.info("EntityId encontrado: " + e.getEntityID());
			entityDescriptors.put(e.getEntityID(), e);
		}
	}
	
	private EntityDescriptor readFile(final File file) throws SAXException, ParserConfigurationException, FileNotFoundException, JAXBException {
		XMLReader reader = new NamespaceFilterXMLReader();
		InputSource is = new InputSource(new FileReader(file));
		SAXSource ss = new SAXSource(reader, is);
		JAXBContext jaxbContext = JAXBContext.newInstance(EntityDescriptor.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return (EntityDescriptor) unmarshaller.unmarshal(ss);
	}
}