package com.palm.cloud.services.cmdb.condition;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "conditional")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValueCondition extends Condition implements Serializable {

	private static final long serialVersionUID = 1711880017805424121L;

	@XmlAttribute(required = true)
	private String name;
	
	@XmlAttribute(required = true)
	private String value;
	
	public ValueCondition() {
		
	}
	
	public ValueCondition(String name, String oper, String value) {
		this.name = name;
		this.oper = Operator.valueOf(oper);
		this.value = value;
	}
	
	public ValueCondition(String name, Operator oper, String value) {
		this.name = name;
		this.oper = oper;
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
