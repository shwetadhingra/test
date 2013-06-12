package com.palm.cloud.services.cmdb.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.entity.RelationshipAttributeDO;

@Repository
@Transactional
public class RelationshipAttributeDAOImpl 
		extends GenericDAOImpl<RelationshipAttributeDO, Integer>
		implements IRelationshipAttributeDAO {

	public RelationshipAttributeDAOImpl() {

	}
	
	public RelationshipAttributeDAOImpl(EntityManager em) {
		super(em);
	}

	@Override
	public RelationshipAttributeDO create(RelationshipAttributeDO entity) {
		entity.getRelationship().addAttribute(entity);
		return super.create(entity);
	}

	@Override
	public void delete(RelationshipAttributeDO entity) {
		entity.getRelationship().getAttributes().remove(entity);
		super.delete(entity);
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public RelationshipAttributeDO findByNameAndNamespace(String relationName,
			String namespace, String attributeName) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"RelationshipAttributeDO.findByAttributeNameRelationNameAndNamespace");
		query.setParameter("relationName", relationName);
		query.setParameter("namespace", namespace);
		query.setParameter("attributeName", attributeName);
		return (RelationshipAttributeDO) query.getSingleResult();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public RelationshipAttributeDO findByName(String relationName,
			String attributeName) {
		
		return this.findByNameAndNamespace(relationName, DEFAULT_NAMESPACE, 
				attributeName);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<RelationshipAttributeDO> findAllByRelationNameAndNamespace(
			String relationName, String namespace) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"RelationshipAttributeDO.findAllByRelationNameAndNamespace");
		query.setParameter("relationName", relationName);
		query.setParameter("namespace", namespace);
		return (List<RelationshipAttributeDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<RelationshipAttributeDO> findAllByRelationName(
			String relationName) {

		return this.findAllByRelationNameAndNamespace(relationName, 
				DEFAULT_NAMESPACE);
	}
	
}
