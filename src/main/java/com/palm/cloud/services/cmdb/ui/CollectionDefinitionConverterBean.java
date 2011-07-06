package com.palm.cloud.services.cmdb.ui;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.palm.cloud.services.cmdb.collection.json.JSONParser;
import com.palm.cloud.services.cmdb.collection.xml.Vertex;
import com.palm.cloud.services.cmdb.collection.xml.XMLParser;

@ManagedBean
@RequestScoped
public class CollectionDefinitionConverterBean {

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
	
	public void toXML() {
		if (json != null) {
			try {
				Vertex object = JSONParser.toObject(Vertex.class, json);
				if (object != null) {
					this.xml = XMLParser.marshall(object);
				}
			} catch (Exception e) {
				//Do nothing
			}
		}
	}

	public void toJSON() {
		if (xml != null) {
			try {
				Vertex object = XMLParser.unmarshall(Vertex.class, xml);
				if (object != null) {
					this.json = JSONParser.toJSON(object);
				}
			} catch (Exception e) {
				//Do nothing
			}
		}
	}

	public void clear() {
		this.xml = null;
		this.json = null;
	}

}
