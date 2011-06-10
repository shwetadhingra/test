package com.palm.cloud.services.cmdb.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MetaClass implements Serializable {

	private static final long serialVersionUID = -8367229341171585918L;

	private String name;
	
	private MetaClass parent;
	
	private boolean isRelationshipType;
	
	private List<MetaAttribute> attributes;

	public MetaClass() {
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MetaClass getParent() {
		return parent;
	}

	public void setParent(MetaClass parent) {
		this.parent = parent;
	}

	public boolean isRelationshipType() {
		return isRelationshipType;
	}

	public void setRelationshipType(boolean isRelationshipType) {
		this.isRelationshipType = isRelationshipType;
	}

	public List<MetaAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<MetaAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute(MetaAttribute attribute) {
		if (this.attributes == null) {
			this.attributes = new ArrayList<MetaAttribute>();
		}
		this.attributes.add(attribute);
	}

}
