package com.palm.cloud.services.cmdb.ui;

import com.palm.cloud.services.cmdb.collection.json.JSONParser;
import com.palm.cloud.services.cmdb.collection.xml.XMLParser;

public abstract class BaseConverterBean {

	private String xml;
	
	private String json;

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	private <T> void toXML(Class<T> t) {
		if (json != null) {
			try {
				T object = JSONParser.toObject(t, json);
				if (object != null) {
					this.xml = XMLParser.marshall(object);
				}
			} catch (Exception e) {
				//Do nothing
			}
		}
	}

	private <T> void toJSON(Class<T> t) {
		if (xml != null) {
			try {
				T object = XMLParser.unmarshall(t, xml);
				if (object != null) {
					this.json = JSONParser.toJSON(object);
				}
			} catch (Exception e) {
				//Do nothing
			}
		}
	}

	public void toXML() {
		this.toXML(this.getType());
	}
	
	public void toJSON() {
		this.toJSON(this.getType());
	}
	
	protected abstract Class<?> getType();
	
	public void clear() {
		this.xml = null;
		this.json = null;
	}

}
