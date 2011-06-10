package com.palm.cloud.services.cmdb.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.entity.MetaStatusDO;

@Repository
@Transactional
public class MetaStatusDAOImpl extends GenericDAOImpl<MetaStatusDO, Integer> 
		implements IMetaStatusDAO {

	public MetaStatusDAOImpl() {

	}
	
	public MetaStatusDAOImpl(EntityManager em) {
		super(em);
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public MetaStatusDO findByName(String status) {
		Query query = this.getEntityManager().createNamedQuery(
				"MetaStatusDO.findByName");
		query.setParameter("status", status);
		return (MetaStatusDO) query.getSingleResult();
	}
	
}
