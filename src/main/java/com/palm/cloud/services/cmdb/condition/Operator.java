package com.palm.cloud.services.cmdb.condition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public enum Operator {
	equal(OperatorType.conditional, "equal", CriteriaBuilder.class, 
			Expression.class, Object.class),
	notEqual(OperatorType.conditional, "notEqual", CriteriaBuilder.class, 
			Expression.class, Object.class),
	like(OperatorType.conditional, "like", CriteriaBuilder.class, 
			Expression.class, String.class),
	notLike(OperatorType.conditional, "notLike", CriteriaBuilder.class, 
			Expression.class, String.class),
	and(OperatorType.logical, "and", CriteriaBuilder.class, Predicate[].class),
	or(OperatorType.logical, "or", CriteriaBuilder.class, Predicate[].class),
	isNull(OperatorType.conditional, "isNull", Expression.class),
	isNotNull(OperatorType.conditional, "isNotNull", Expression.class),
	in(OperatorType.conditional, "in", Expression.class, Object[].class);

	public enum OperatorType {
		conditional, logical;
	}
	private final OperatorType operatorType;
	
	private final String operation;
	
	private final Class<?> objectType;
	
	private final Class<?>[] parameterTypes;
	
	Operator(OperatorType operatorType, String operation, 
			Class<?> objectType,
			Class<?>... parameterTypes) {
		this.operatorType = operatorType;
		this.operation = operation;
		this.objectType = objectType;
		this.parameterTypes = parameterTypes;
	}

	public OperatorType getOperatorType() {
		return operatorType;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public Class<?> getObjectType() {
		return objectType;
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
