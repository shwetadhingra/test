package com.palm.cloud.services.cmdb.collection.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.palm.cloud.services.cmdb.condition.Condition;

@XmlRootElement(name = "vertex")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Condition.class})
public class Vertex {

	@XmlAttribute(required = true)
	private String type;
	
	@XmlAnyElement(lax = true)
	private List<? extends Condition> conditions;
	
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

	public void setConditions(List<? extends Condition> conditions) {
		this.conditions = conditions;
	}

	public List<? extends Condition> getConditions() {
		return conditions;
	}
	
}
