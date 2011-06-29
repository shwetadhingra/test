package com.palm.cloud.services.cmdb.condition;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "logical")
@XmlAccessorType(XmlAccessType.FIELD)
public class LogicalCondition extends Condition implements Serializable {

	private static final long serialVersionUID = -217328566267951161L;

	@XmlAnyElement(lax = true)
	private List<? extends Condition> conditions;

	public LogicalCondition() {
		
	}
	
	public LogicalCondition(Operator oper) {
		this.oper = oper;
	}
	
	public LogicalCondition(String oper) {
		this.oper = Operator.valueOf(oper);
	}
	
	public List<? extends Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<? extends Condition> conditions) {
		this.conditions = conditions;
	}
	
}
