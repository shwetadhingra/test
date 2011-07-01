package com.palm.cloud.services.cmdb.collection.json;

import java.io.StringWriter;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONParser {

	public static <T> T toObject(Class<T> klass, String json) {
		T object = null;
    	try {
			ObjectMapper objectMapper = new ObjectMapper();
			object = objectMapper.readValue(json, klass);
		} catch (Exception e) {

		}
    	return object;
	}

	public static <T> String toJSON(T object) {
		String serialized = null;
    	try {
			ObjectMapper objectMapper = new ObjectMapper();
			MappingJsonFactory jsonFactory = new MappingJsonFactory();
			StringWriter sw = new StringWriter();
			JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(sw);
			objectMapper.writeValue(jsonGenerator, object);
			sw.close();
			serialized = sw.getBuffer().toString();
		} catch (Exception e) {

		}
		return serialized;
	}
	
}
