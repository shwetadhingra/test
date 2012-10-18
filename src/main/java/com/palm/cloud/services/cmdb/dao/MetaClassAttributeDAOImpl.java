package com.palm.cloud.services.cmdb.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.entity.MetaClassAttributeDO;

@Repository
@Transactional
public class MetaClassAttributeDAOImpl 
		extends GenericDAOImpl<MetaClassAttributeDO, Integer> 
		implements IMetaClassAttributeDAO {

	public MetaClassAttributeDAOImpl() {
		
	}
	
	public MetaClassAttributeDAOImpl(EntityManager em) {
		super(em);
	}

	public void delete(MetaClassAttributeDO entity) {
		super.delete(entity);
	}

	public MetaClassAttributeDO findByTypeNameAndAttributeName(String typeName,
			String attributeName) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"MetaClassAttributeDO.findByClassNameAndAttributeName");
		query.setParameter("typeName", typeName);
		query.setParameter("attributeName", attributeName);
		return (MetaClassAttributeDO) query.getSingleResult();
	}

}
