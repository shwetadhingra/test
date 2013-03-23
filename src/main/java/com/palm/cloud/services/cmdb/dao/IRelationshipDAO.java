package com.palm.cloud.services.cmdb.dao;

import java.util.List;

import com.palm.cloud.services.cmdb.entity.RelationshipDO;

public interface IRelationshipDAO extends IGenericDAO<RelationshipDO, Integer> {

	RelationshipDO findByNameAndNamespace(String name, String namespace);
	
	RelationshipDO findByName(String name);
	
	List<RelationshipDO> findByFromObjectNameAndNamespace(String objectName, 
			String namespace);

	List<RelationshipDO> findByFromObjectName(String objectName);

	List<RelationshipDO> findByToObjectNameAndNamespace(String objectName, 
			String namespace);

	List<RelationshipDO> findByToObjectName(String objectName);

	List<RelationshipDO> findByFromObjectNamespaceAndClass(String objectName, 
			String namespace, String className);

	List<RelationshipDO> findByFromObjectNameAndClass(String objectName, 
			String className);

	List<RelationshipDO> findByToObjectNamespaceAndClass(String objectName, 
			String namespace, String className);

	List<RelationshipDO> findByToObjectNameAndClass(String objectName, 
			String className);

	List<RelationshipDO> findAllByClassAndNamespace(String className, 
			String namespace, int offset, int maxResults);
	
	List<RelationshipDO> findAllByClass(String className, int offset, 
			int maxResults);
	
	List<RelationshipDO> findAllByNamespace(String namespace, int offset, 
			int maxResults);
	
	List<RelationshipDO> findAll(int offset, int maxResults);
	
}
