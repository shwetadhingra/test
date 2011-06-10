package com.palm.cloud.services.cmdb.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.entity.MetaAttributeDO;

@Repository
@Transactional
public class MetaAttributeDAOImpl 
		extends GenericDAOImpl<MetaAttributeDO, Integer>
		implements IMetaAttributeDAO {

	public MetaAttributeDAOImpl() {
		
	}
	
	public MetaAttributeDAOImpl(EntityManager em) {
		super(em);
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public MetaAttributeDO findByName(String attributeName) {
		Query query = this.getEntityManager()
			.createNamedQuery("MetaAttributeDO.findByName");
		query.setParameter("attributeName", attributeName);
		return (MetaAttributeDO) query.getSingleResult();
	}
	
}
