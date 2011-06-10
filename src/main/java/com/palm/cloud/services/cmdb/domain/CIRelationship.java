package com.palm.cloud.services.cmdb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CIRelationship implements Serializable {

	private static final long serialVersionUID = 5279970399348403079L;

	private String name;
	
	private String namespace;
	
	private String type;
	
	private String status;
	
	private String fromObject;
	
	private String toObject;
	
	public CIRelationship() {
		
	}
	
	public CIRelationship(String namespace, String fromObject, String toObject, 
			String type, String status) {
		
		this.namespace = namespace;
		this.fromObject = fromObject;
		this.toObject = toObject;
		this.type = type;
		this.status = status;
	}
	
	private List<CIRelationshipAttribute> attributes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public String getFromObject() {
		return fromObject;
	}

	public void setFromObject(String fromObject) {
		this.fromObject = fromObject;
	}

	public String getToObject() {
		return toObject;
	}

	public void setToObject(String toObject) {
		this.toObject = toObject;
	}

	public List<CIRelationshipAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<CIRelationshipAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute(CIRelationshipAttribute attribute) {
		if (this.attributes == null) {
			this.attributes = new ArrayList<CIRelationshipAttribute>();
		}
		this.attributes.add(attribute);
	}

}
