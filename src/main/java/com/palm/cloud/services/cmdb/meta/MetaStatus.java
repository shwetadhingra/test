package com.palm.cloud.services.cmdb.meta;

import java.io.Serializable;

public class MetaStatus implements Serializable {

	private static final long serialVersionUID = 6118497883818216633L;

	public MetaStatus() {
		
	}
	
	public MetaStatus(String name) {
		this.name = name;
	}
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
