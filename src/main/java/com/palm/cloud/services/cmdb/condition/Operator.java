package com.palm.cloud.services.cmdb.condition;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public enum Operator {
	equal(OperatorType.conditional, "equal", Expression.class, Object.class),
	notEqual(OperatorType.conditional, "notEqual", 
			Expression.class, Object.class),
	like(OperatorType.conditional, "like", Expression.class, String.class),
	notLike(OperatorType.conditional, "notLike", 
			Expression.class, String.class),
	and(OperatorType.logical, "and", Predicate[].class),
	or(OperatorType.logical, "or", Predicate[].class);

	public enum OperatorType {
		conditional, logical;
	}
	private final OperatorType operatorType;
	
	private final String operation;
	
	private final Class<?>[] parameterTypes;
	
	Operator(OperatorType operatorType, String operation, 
			Class<?>... parameterTypes) {
		this.operatorType = operatorType;
		this.operation = operation;
		this.parameterTypes = parameterTypes;
	}

	public OperatorType getOperatorType() {
		return operatorType;
	}
	
	public String getOperation() {
		return operation;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
	
	public boolean isConditional() {
		return OperatorType.conditional == operatorType;
	}

	public boolean isLogical() {
		return OperatorType.logical == operatorType;
	}

}
