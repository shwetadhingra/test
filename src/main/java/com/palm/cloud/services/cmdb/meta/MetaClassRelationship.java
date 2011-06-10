package com.palm.cloud.services.cmdb.meta;

import java.io.Serializable;

public class MetaClassRelationship implements Serializable {

	private static final long serialVersionUID = -4548874386665583122L;

	private MetaClass fromType;
	
	private MetaClass relationshipType;
	
	private MetaClass toType;

	public MetaClassRelationship() {
		
	}
	
	public MetaClass getFromType() {
		return fromType;
	}

	public void setFromType(MetaClass fromType) {
		this.fromType = fromType;
	}

	public MetaClass getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(MetaClass relationshipType) {
		this.relationshipType = relationshipType;
	}

	public MetaClass getToType() {
		return toType;
	}

	public void setToType(MetaClass toType) {
		this.toType = toType;
	}

}
