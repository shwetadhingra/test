package com.palm.cloud.services.cmdb.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.entity.MetaAttributeDO;
import com.palm.cloud.services.cmdb.entity.MetaAttributeDO_;
import com.palm.cloud.services.cmdb.entity.MetaClassDO;
import com.palm.cloud.services.cmdb.entity.MetaClassDO_;
import com.palm.cloud.services.cmdb.entity.ObjectAttributeDO;
import com.palm.cloud.services.cmdb.entity.ObjectAttributeDO_;
import com.palm.cloud.services.cmdb.entity.ObjectDO;
import com.palm.cloud.services.cmdb.entity.ObjectDO_;

@Repository
@Transactional
public class ObjectDAOImpl extends GenericDAOImpl<ObjectDO, Integer> 
		implements IObjectDAO {

	public ObjectDAOImpl() {
		
	}
	
	public ObjectDAOImpl(EntityManager em) {
		super(em);
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public ObjectDO findByNameAndNamespace(String name, String namespace) {
		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findByNameAndNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		return (ObjectDO) query.getSingleResult();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public ObjectDO findByName(String name) {
		return this.findByNameAndNamespace(name, DEFAULT_NAMESPACE);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findAllByClassAndNamespace(String className, 
			String namespace, int offset, int maxResults) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findAllByClassAndNamespace");
		query.setParameter("className", className);
		query.setParameter("namespace", namespace);
		query.setFirstResult(offset);
		query.setMaxResults(maxResults);
		return (List<ObjectDO>) query.getResultList();
	}
	
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findAllByClass(String className, int offset, 
			int maxResults) {
		
		return this.findAllByClassAndNamespace(className, DEFAULT_NAMESPACE, 
				offset, maxResults);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findFromObjectsByClassAndNamespace(String name,
			String namespace, String relationClass, String className) {

		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findFromObjectsByClassAndNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		query.setParameter("relationClass", relationClass);
		query.setParameter("className", className);
		return (List<ObjectDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findFromObjectsByClass(String name,
			String relationClass, String className) {

		return this.findFromObjectsByClassAndNamespace(name, 
				DEFAULT_NAMESPACE, relationClass, className);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findToObjectsByClassAndNamespace(String name,
			String namespace, String relationClass, String className) {

		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findToObjectsByClassAndNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		query.setParameter("relationClass", relationClass);
		query.setParameter("className", className);
		return (List<ObjectDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findToObjectsByClass(String name,
			String relationClass, String className) {
		
		return this.findToObjectsByClassAndNamespace(name, 
				DEFAULT_NAMESPACE, relationClass, className);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findFromObjectsByRelationClassAndNamespace(
			String name, String namespace, String relationClass) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findFromObjectsByRelationshipClassAndNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		query.setParameter("relationClass", relationClass);
		return (List<ObjectDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findFromObjectsByRelationClass(String name,
			String relationClass) {
		
		return this.findFromObjectsByRelationClassAndNamespace(name, 
				DEFAULT_NAMESPACE, relationClass);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findToObjectsByRelationClassAndNamespace(String name,
			String namespace, String relationClass) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findToObjectsByRelationshipClassAndNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		query.setParameter("relationClass", relationClass);
		return (List<ObjectDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findToObjectsByRelationClass(String name,
			String relationClass) {
		
		return this.findToObjectsByRelationClassAndNamespace(name, 
				DEFAULT_NAMESPACE, relationClass);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findFromObjectsByNamespace(String name,
			String namespace) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findFromObjectsByNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		return (List<ObjectDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findFromObjects(String name) {
		return this.findFromObjectsByNamespace(name, DEFAULT_NAMESPACE);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findToObjectsByNamespace(String name, 
			String namespace) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findToObjectsByNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		return (List<ObjectDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findToObjects(String name) {
		return this.findToObjectsByNamespace(name, DEFAULT_NAMESPACE);
	}
	
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findAllByClassAndAttribute(String className, 
			String attributeName, String attributeValue) {
		
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<ObjectDO> cq = cb.createQuery(ObjectDO.class);
		Root<ObjectDO> o = cq.from(ObjectDO.class);
		Join<ObjectDO, ObjectAttributeDO> oa = o.join(ObjectDO_.attributes);
		Join<ObjectDO, MetaClassDO> om = o.join(ObjectDO_.klass);
		Join<ObjectAttributeDO, MetaAttributeDO> am = oa.join(
				ObjectAttributeDO_.attribute);
		cq.where(cb.and(
				cb.equal(om.get(MetaClassDO_.name), className),
				cb.equal(am.get(MetaAttributeDO_.name), attributeName),
				cb.equal(oa.get(ObjectAttributeDO_.value), attributeValue)));
		TypedQuery<ObjectDO> q = this.getEntityManager().createQuery(cq);
		List<ObjectDO> objects = q.getResultList();
		return objects;
	}
	
}
