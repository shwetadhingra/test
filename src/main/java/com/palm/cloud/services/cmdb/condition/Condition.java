package com.palm.cloud.services.cmdb.condition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ValueCondition.class, LogicalCondition.class})
public abstract class Condition {

	@XmlAttribute(required = true)
	protected Operator oper;

	public Operator getOper() {
		return oper;
	}

	public void setOper(Operator oper) {
		this.oper = oper;
	}
	
}
