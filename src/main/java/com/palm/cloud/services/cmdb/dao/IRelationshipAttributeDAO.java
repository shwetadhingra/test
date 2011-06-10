package com.palm.cloud.services.cmdb.dao;

import java.util.List;

import com.palm.cloud.services.cmdb.entity.RelationshipAttributeDO;

public interface IRelationshipAttributeDAO 
		extends IGenericDAO<RelationshipAttributeDO, Integer> {

	RelationshipAttributeDO findByNameAndNamespace(String relationName, 
			String namespace, String attributeName);
	
	RelationshipAttributeDO findByName(String relationName, 
			String attributeName);
	
	List<RelationshipAttributeDO> findAllByRelationNameAndNamespace(
			String relationName, String namespace);

	List<RelationshipAttributeDO> findAllByRelationName(String relationName);

}
