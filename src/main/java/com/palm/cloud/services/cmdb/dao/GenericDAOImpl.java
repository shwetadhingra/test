package com.palm.cloud.services.cmdb.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

public class GenericDAOImpl<T, ID extends Serializable> 
		implements IGenericDAO<T, ID> {

	public static final String DEFAULT_NAMESPACE = "DEFAULT";
	
	private final Class<T> entityClass;
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public GenericDAOImpl(EntityManager entityManager) {
		this();
		this.entityManager = entityManager;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Class<T> getEntityClass() {
		return this.entityClass;
	}

	public T create(T entity) {
		this.getEntityManager().persist(entity);
		this.getEntityManager().flush();
		this.getEntityManager().refresh(entity);
		return entity;
	}

	@Transactional(readOnly = true)
	public T findById(ID id) {
		return this.getEntityManager().find(this.entityClass, id);
	}

	public T update(T entity) {
		entity = this.getEntityManager().merge(entity);
		this.getEntityManager().flush();
		this.getEntityManager().refresh(entity);
		return entity;
	}

	public void delete(T entity) {
		this.getEntityManager().merge(entity);
		this.getEntityManager().remove(entity);
		this.getEntityManager().flush();
	}

	public void delete(ID id) {
		T entity = this.getEntityManager().find(this.entityClass, id);
		if (entity != null) {
			this.delete(entity);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<T> findAll() {
		String className = this.entityClass.getSimpleName();
		Query query = this.getEntityManager().createQuery("SELECT t from " 
				+ className + " t");
		return (List<T>) query.getResultList();
	}

}
