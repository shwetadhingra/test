package com.palm.cloud.services.cmdb.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.entity.RelationshipDO;

@Repository
@Transactional
public class RelationshipDAOImpl 
		extends GenericDAOImpl<RelationshipDO, Integer>
		implements IRelationshipDAO {

	public RelationshipDAOImpl() {

	}
	
	public RelationshipDAOImpl(EntityManager em) {
		super(em);
	}

	@Override
	public RelationshipDO create(RelationshipDO entity) {
		entity.getFromObject().addFromRelationship(entity);
		entity.getToObject().addToRelationship(entity);
		return super.create(entity);
	}

	@Override
	public void delete(RelationshipDO entity) {
		entity.getFromObject().getFromRelationships().remove(entity);
		entity.getToObject().getToRelationships().remove(entity);
		super.delete(entity);
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public RelationshipDO findByNameAndNamespace(String name, 
			String namespace) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"RelationshipDO.findByNameAndNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		return (RelationshipDO) query.getSingleResult();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public RelationshipDO findByName(String name) {
		return findByNameAndNamespace(name, DEFAULT_NAMESPACE);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<RelationshipDO> findByFromObjectNameAndNamespace(
			String objectName, String namespace) {

		Query query = this.getEntityManager().createNamedQuery(
				"RelationshipDO.findByFromObjectAndNamespace");
		query.setParameter("objectName", objectName);
		query.setParameter("namespace", namespace);
		return (List<RelationshipDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<RelationshipDO> findByFromObjectName(String objectName) {
		return this.findByFromObjectNameAndNamespace(objectName, 
				DEFAULT_NAMESPACE);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<RelationshipDO> findByToObjectNameAndNamespace(
			String objectName, String namespace) {

		Query query = this.getEntityManager().createNamedQuery(
				"RelationshipDO.findByToObjectAndNamespace");
		query.setParameter("objectName", objectName);
		query.setParameter("namespace", namespace);
		return (List<RelationshipDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<RelationshipDO> findByToObjectName(String objectName) {
		return this.findByToObjectNameAndNamespace(objectName, 
				DEFAULT_NAMESPACE);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<RelationshipDO> findByFromObjectNamespaceAndClass(
			String objectName, String namespace, String className) {

		Query query = this.getEntityManager().createNamedQuery(
				"RelationshipDO.findByFromObjectNamespaceAndClass");
		query.setParameter("objectName", objectName);
		query.setParameter("namespace", namespace);
		query.setParameter("className", className);
		return (List<RelationshipDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<RelationshipDO> findByFromObjectNameAndClass(String objectName,
			String className) {
		
		return this.findByFromObjectNamespaceAndClass(objectName, 
				DEFAULT_NAMESPACE, className);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<RelationshipDO> findByToObjectNamespaceAndClass(
			String objectName, String namespace, String className) {

		Query query = this.getEntityManager().createNamedQuery(
				"RelationshipDO.findByToObjectNamespaceAndClass");
		query.setParameter("objectName", objectName);
		query.setParameter("namespace", namespace);
		query.setParameter("className", className);
		return (List<RelationshipDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<RelationshipDO> findByToObjectNameAndClass(String objectName,
			String className) {
		
		return this.findByToObjectNamespaceAndClass(objectName, 
				DEFAULT_NAMESPACE, className);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<RelationshipDO> findAllByClassAndNamespace(String className,
			String namespace, int offset, int maxResults) {

		Query query = this.getEntityManager().createNamedQuery(
				"RelationshipDO.findAllByClassAndNamespace");
		query.setParameter("className", className);
		query.setParameter("namespace", namespace);
		query.setFirstResult(offset);
		query.setMaxResults(maxResults);
		return (List<RelationshipDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<RelationshipDO> findAllByClass(String className, int offset,
			int maxResults) {
		
		return this.findAllByClassAndNamespace(className, DEFAULT_NAMESPACE, 
				offset, maxResults);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<RelationshipDO> findAllByNamespace(String namespace, int offset, 
			int maxResults) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"RelationshipDO.findAllByNamespace");
		query.setParameter("namespace", namespace);
		query.setFirstResult(offset);
		query.setMaxResults(maxResults);
		return (List<RelationshipDO>) query.getResultList();
	}
	
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<RelationshipDO> findAll(int offset, int maxResults) {
		return this.findAllByNamespace(DEFAULT_NAMESPACE, offset, maxResults);
	}
	
}
