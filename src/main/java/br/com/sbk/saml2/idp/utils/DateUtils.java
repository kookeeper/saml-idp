package br.com.sbk.saml2.idp.utils;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtils {

	
	public static XMLGregorianCalendar formatData(Long data) throws DatatypeConfigurationException{
		
		java.util.Date datamovto = new java.util.Date(data);
		GregorianCalendar dataMovtoGregorianCalendar = new GregorianCalendar();
		dataMovtoGregorianCalendar.setTime(datamovto);
		
		XMLGregorianCalendar dataMovimento = DatatypeFactory.newInstance().newXMLGregorianCalendar(dataMovtoGregorianCalendar);
		return dataMovimento;
		
	}
}
