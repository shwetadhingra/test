package com.palm.cloud.services.cmdb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CIObject implements Serializable {

	private static final long serialVersionUID = -2797971295675445546L;

	private String name;
	
	private String namespace;
	
	private String type;
	
	private String status;
	
	private List<CIAttribute> attributes;

	public CIObject() {
		
	}
	
	public CIObject(String name, String namespace, String type, String status) {
		this.name = name;
		this.namespace = namespace;
		this.type = type;
		this.status = status;
	}
	
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

	public List<CIAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<CIAttribute> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(CIAttribute attribute) {
		if (this.attributes == null) {
			this.attributes = new ArrayList<CIAttribute>();
		}
		this.attributes.add(attribute);
	}
	
	public CIAttribute getAttribute(String attributeName) {
		CIAttribute attribute = null;
		if (this.attributes != null) {
			for (CIAttribute cia : this.attributes) {
				if (cia.getName() != null 
						&& cia.getName().equals(attributeName)) {
					
					attribute = cia;
					break;
				}
			}
		}
		return attribute;
	}

}
