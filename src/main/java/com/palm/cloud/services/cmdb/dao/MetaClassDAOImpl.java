package com.palm.cloud.services.cmdb.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.entity.MetaClassDO;

@Repository
@Transactional
public class MetaClassDAOImpl extends GenericDAOImpl<MetaClassDO, Integer> 
	implements IMetaClassDAO {

	public MetaClassDAOImpl() {

	}
	
	public MetaClassDAOImpl(EntityManager em) {
		super(em);
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public MetaClassDO findByName(String name) {
		Query query = this.getEntityManager().createNamedQuery(
				"MetaClassDO.findByName");
		query.setParameter("name", name);
		return (MetaClassDO) query.getSingleResult();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public MetaClassDO findByNameAndType(String name, 
			boolean isRelationshipType) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"MetaClassDO.findByNameAndType");
		query.setParameter("name", name);
		query.setParameter("isRelationshipType", isRelationshipType);
		return (MetaClassDO) query.getSingleResult();
	}
	
}
