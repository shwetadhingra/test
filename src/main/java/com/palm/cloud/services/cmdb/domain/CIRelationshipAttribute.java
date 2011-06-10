package com.palm.cloud.services.cmdb.domain;

import java.io.Serializable;

public class CIRelationshipAttribute implements Serializable {

	private static final long serialVersionUID = 5688040685570126079L;

	private String name;
	
	private String value;

	public CIRelationshipAttribute() {
		
	}
	
	public CIRelationshipAttribute(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
