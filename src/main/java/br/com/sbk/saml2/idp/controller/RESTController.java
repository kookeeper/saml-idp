package br.com.sbk.saml2.idp.controller;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sbk.saml2.idp.config.IdpConfiguration;
import br.com.sbk.saml2.idp.entity.metadados.idp.EntityDescriptor;
import br.com.sbk.saml2.idp.entity.metadados.idp.IDPSSODescriptor;
import br.com.sbk.saml2.idp.entity.metadados.idp.KeyDescriptor;
import br.com.sbk.saml2.idp.entity.metadados.idp.KeyInfo;
import br.com.sbk.saml2.idp.entity.metadados.idp.SingleSignOnService;
import br.com.sbk.saml2.idp.entity.metadados.idp.X509Data;
import io.swagger.annotations.Api;

@RestController
@Api(value = "SamlIdp")
public class RESTController {
/*
	@Autowired
	private TbIntsoaSpConfigRepository rbIntsoaSpConfigRepository;
*/
	@Autowired
	private IdpConfiguration idp;
	
	public static final Logger logger = LoggerFactory.getLogger(RESTController.class);
/*
	@RequestMapping(value = "/salvarConfiguracaoSP", method = RequestMethod.POST)
	public String salvarConfiguracaoSP(@RequestBody Spconfig sp) {
		String retorno;

		try {
			logger.info("Iniciando salvamento dos dados do parceiro na tabela TB_INTSOA_SPCONFIG >>> entityId: "
					+ sp.getEntityid());
			TbIntsoaSpConfig tbIntsoaSpConfig = new TbIntsoaSpConfig();

			tbIntsoaSpConfig.setEntityId(sp.getEntityid());
			tbIntsoaSpConfig.setSpUrl(sp.getSpurl());
			tbIntsoaSpConfig.setSpAttributes(sp.getSpattributes());
			tbIntsoaSpConfig.setSpCertificate(sp.getSpcertificate());
			tbIntsoaSpConfig.setSpErro(sp.getSperro());
			tbIntsoaSpConfig.setSpRedirect(sp.getSpredirect());

			rbIntsoaSpConfigRepository.save(tbIntsoaSpConfig);

			logger.info("Registro salvo com sucesso na tabela TB_INTSOA_SPCONFIG >>> entityId: " + sp.getEntityid());

			retorno = "Salvo com sucesso.";
		} catch (Exception e) {
			retorno = "Erro ao salvar.";
			logger.error("Erro ao salvar na tabela TB_INTSOA_SPCONFIG >>> " + e.getMessage());
		}

		return retorno;
	}
*/
	
	@GetMapping("/metadados")
	public ResponseEntity<byte[]> metadados() throws Exception {	
		
		byte[] isr = null;
		HttpHeaders respHeaders = new HttpHeaders();
		
		try {
			logger.info("Obtendo dados de configuração do IDP");

			EntityDescriptor entityDescriptor = new EntityDescriptor();
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

			isr = sw.toString().getBytes();
			String fileName = "metadados.xml";
			respHeaders.setContentLength(isr.length);
			respHeaders.setContentType(new MediaType("text", "xml"));
			respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
			
		} catch (Exception e) {
			logger.error("Erro ao salvar na tabela TB_INTSOA_SPCONFIG >>> " + e.getMessage());
		}
		
		
		return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
	}

}
