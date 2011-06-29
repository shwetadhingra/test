package com.palm.cloud.services.cmdb.condition;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ValueCondition.class, LogicalCondition.class})
@XmlTransient
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({
	@JsonSubTypes.Type(name="logical", value=com.palm.cloud.services.cmdb.condition.LogicalCondition.class),
	@JsonSubTypes.Type(name="conditional", value=com.palm.cloud.services.cmdb.condition.ValueCondition.class)
})
public abstract class Condition implements Serializable {

	private static final long serialVersionUID = -4420173513359125615L;

	@XmlAttribute(required = true)
	protected Operator oper;

	public Operator getOper() {
		return oper;
	}

	public void setOper(Operator oper) {
		this.oper = oper;
	}
	
}
