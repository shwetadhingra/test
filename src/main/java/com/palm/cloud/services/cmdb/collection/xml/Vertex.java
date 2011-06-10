package com.palm.cloud.services.cmdb.collection.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "vertex")
@XmlAccessorType(XmlAccessType.FIELD)
public class Vertex {

	@XmlAttribute(required = true)
	private String type;
	
	@XmlElement
	private List<Edge> edge;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Edge> getEdge() {
		return edge;
	}

	public void setEdge(List<Edge> edge) {
		this.edge = edge;
	}
	
}
