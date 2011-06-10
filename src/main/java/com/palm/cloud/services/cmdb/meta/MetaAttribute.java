package com.palm.cloud.services.cmdb.meta;

import java.io.Serializable;

public class MetaAttribute implements Serializable {

	private static final long serialVersionUID = -8726190231170708652L;

	private String name;
	
	public MetaAttribute() {
		
	}
	
	public MetaAttribute(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
