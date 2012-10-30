package com.palm.cloud.services.cmdb.dao;

import java.util.List;

import com.palm.cloud.services.cmdb.condition.Condition;
import com.palm.cloud.services.cmdb.entity.ObjectDO;

public interface IObjectDAO extends IGenericDAO<ObjectDO, Integer> {

	ObjectDO findByNameAndNamespace(String name, String namespace);
	
	ObjectDO findByName(String name);
	
	List<ObjectDO> findAllByClassAndNamespace(String className, 
			String namespace, int offset, int maxResults);
	
	List<ObjectDO> findAllByClass(String className, int offset, int maxResults);

	List<ObjectDO> findFromObjectsByClassAndNamespace(String name, 
			String namespace, String relationClass, String className);
	
	List<ObjectDO> findFromObjectsByClass(String name, String relationClass, 
			String className);
	
	List<ObjectDO> findToObjectsByClassAndNamespace(String name, 
			String namespace, String relationClass, String className);
	
	List<ObjectDO> findToObjectsByClass(String name, String relationClass, 
			String className);
	
	List<ObjectDO> findFromObjectsByRelationClassAndNamespace(String name, 
			String namespace, String relationClass);
	
	List<ObjectDO> findFromObjectsByRelationClass(String name, 
			String relationClass);
	
	List<ObjectDO> findToObjectsByRelationClassAndNamespace(String name, 
			String namespace, String relationClass);
	
	List<ObjectDO> findToObjectsByRelationClass(String name, 
			String relationClass);
	
	List<ObjectDO> findFromObjectsByNamespace(String name, String namespace);
	
	List<ObjectDO> findFromObjects(String name);
	
	List<ObjectDO> findToObjectsByNamespace(String name, String namespace);
	
	List<ObjectDO> findToObjects(String name);

	List<ObjectDO> findAllByConditionsAndNamespace(
			String className, String namespace, int offset, int maxResults, 
			Condition... conditions);	

	List<ObjectDO> findAllByConditions(
			String className, int offset, int maxResults,
			Condition... conditions);	

	List<ObjectDO> findFromObjectsByConditionsAndNamespace(String name,
			String namespace, String relationClass, String className, 
			Condition... conditions);

	List<ObjectDO> findFromObjectsByConditions(String name,
			String relationClass, String className,	Condition... conditions);

	List<ObjectDO> findToObjectsByConditionsAndNamespace(String name,
			String namespace, String relationClass, String className, 
			Condition... conditions);

	List<ObjectDO> findToObjectsByConditions(String name,
			String relationClass, String className,	Condition... conditions);

	List<ObjectDO> findAllByNameClassAndNamespace(String nameLike, 
			String className, String namespace, int offset, int maxResults);
	
	List<ObjectDO> findAllByNameAndClass(String nameLike, String className, 
			int offset, int maxResults);

}
