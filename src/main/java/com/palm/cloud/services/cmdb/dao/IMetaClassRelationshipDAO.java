package com.palm.cloud.services.cmdb.dao;

import java.util.List;

import com.palm.cloud.services.cmdb.entity.MetaClassRelationshipDO;

public interface IMetaClassRelationshipDAO 
		extends IGenericDAO<MetaClassRelationshipDO, Integer> {

	MetaClassRelationshipDO findByTypes(String fromType, String relationType, 
			String toType);
	
	List<MetaClassRelationshipDO> findByFromType(String fromType);
	
	List<MetaClassRelationshipDO> findByToType(String toType);

	List<MetaClassRelationshipDO> findByRelationType(String relationType);

}
