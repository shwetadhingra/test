package com.palm.cloud.services.cmdb.collection.xml;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLParser {

	@SuppressWarnings("unchecked")
	public static <T> T unmarshall(Class<T> klass, String xml) 
			throws JAXBException {
		
		JAXBContext jc = JAXBContext.newInstance(klass);
		Unmarshaller u = jc.createUnmarshaller();
		return (T) u.unmarshal(new ByteArrayInputStream(xml.getBytes()));
	}
	
	public static <T> String marshall(T object) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(object.getClass());
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		m.marshal(object, sw);
		return sw.toString();
	}

}
