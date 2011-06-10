package com.palm.cloud.services.cmdb.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T, ID extends Serializable> {

	Class<T> getEntityClass();
	
	T create(final T entity);
	
	T findById(final ID id);
	
	T update(final T entity);
	
	void delete(final T entity);

	void delete(final ID id);
	
	List<T> findAll();

}
