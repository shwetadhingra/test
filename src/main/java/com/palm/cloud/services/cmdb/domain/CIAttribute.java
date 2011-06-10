package com.palm.cloud.services.cmdb.domain;

import java.io.Serializable;

public class CIAttribute implements Serializable {

	private static final long serialVersionUID = 1892037382066633580L;

	private String name;
	
	private String value;

	public CIAttribute() {
		
	}
	
	public CIAttribute(String name, String value) {
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
