package com.palm.cloud.services.cmdb.condition;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public enum Operator {
	equal(Expression.class, Object.class),
	notEqual(Expression.class, Object.class),
	like(Expression.class, String.class),
	notLike(Expression.class, String.class),
	and(Predicate[].class),
	or(Predicate[].class);

	private final Class<?>[] parameterTypes;
	
	Operator(Class<?>... parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
	
}
