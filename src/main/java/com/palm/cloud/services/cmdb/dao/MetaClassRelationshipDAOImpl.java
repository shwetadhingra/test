package com.palm.cloud.services.cmdb.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.entity.MetaClassRelationshipDO;

@Repository
@Transactional
public class MetaClassRelationshipDAOImpl 
		extends GenericDAOImpl<MetaClassRelationshipDO, Integer>
		implements IMetaClassRelationshipDAO {

	public MetaClassRelationshipDAOImpl() {

	}
	
	public MetaClassRelationshipDAOImpl(EntityManager em) {
		super(em);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<MetaClassRelationshipDO> findByFromType(String fromType) {
		Query query = this.getEntityManager()
			.createNamedQuery("MetaClassRelationshipDO.findByFromType");
		query.setParameter("fromType", fromType);
		return (List<MetaClassRelationshipDO>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<MetaClassRelationshipDO> findByToType(String toType) {
		Query query = this.getEntityManager()
			.createNamedQuery("MetaClassRelationshipDO.findByToType");
		query.setParameter("toType", toType);
		return (List<MetaClassRelationshipDO>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<MetaClassRelationshipDO> findByRelationType(
			String relationType) {
		
		Query query = this.getEntityManager()
			.createNamedQuery("MetaClassRelationshipDO.findByRelationType");
		query.setParameter("relationType", relationType);
		return (List<MetaClassRelationshipDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public MetaClassRelationshipDO findByTypes(String fromType,
			String relationType, String toType) {
		
		Query query = this.getEntityManager()
			.createNamedQuery("MetaClassRelationshipDO.findByTypes");
		query.setParameter("fromType", fromType);
		query.setParameter("relationType", relationType);
		query.setParameter("toType", toType);
		return (MetaClassRelationshipDO) query.getSingleResult();
	}
	
}
