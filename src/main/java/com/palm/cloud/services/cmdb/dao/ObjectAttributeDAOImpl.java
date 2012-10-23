package com.palm.cloud.services.cmdb.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.entity.ObjectAttributeDO;

@Repository
@Transactional
public class ObjectAttributeDAOImpl 
		extends GenericDAOImpl<ObjectAttributeDO, Integer> 
		implements IObjectAttributeDAO {

	public ObjectAttributeDAOImpl() {
		
	}
	
	public ObjectAttributeDAOImpl(EntityManager em) {
		super(em);
	}

	@Override
	public ObjectAttributeDO create(ObjectAttributeDO entity) {
		entity.getObject().addAttribute(entity);
		return super.create(entity);
	}

	@Override
	public void delete(ObjectAttributeDO entity) {
		entity.getObject().getAttributes().remove(entity);
		this.getEntityManager().flush();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public ObjectAttributeDO findByNameAndNamespace(String objectName,
			String namespace, String attributeName) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"ObjectAttributeDO.findByAttributeNameObjectNameAndNamespace");
		query.setParameter("objectName", objectName);
		query.setParameter("namespace", namespace);
		query.setParameter("attributeName", attributeName);
		return (ObjectAttributeDO) query.getSingleResult();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public ObjectAttributeDO findByName(String objectName, 
			String attributeName) {
		
		return this.findByNameAndNamespace(objectName, DEFAULT_NAMESPACE, 
				attributeName);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectAttributeDO> findAllByObjectNameAndNamespace(
			String objectName, String namespace) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"ObjectAttributeDO.findAllByObjectNameAndNamespace");
		query.setParameter("objectName", objectName);
		query.setParameter("namespace", namespace);
		return (List<ObjectAttributeDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectAttributeDO> findAllByObjectName(String objectName) {
		return this.findAllByObjectNameAndNamespace(objectName, 
				DEFAULT_NAMESPACE);
	}
	
}
