package com.palm.cloud.services.cmdb.dao;

import java.util.List;

import com.palm.cloud.services.cmdb.entity.ObjectAttributeDO;

public interface IObjectAttributeDAO 
		extends IGenericDAO<ObjectAttributeDO, Integer> {

	ObjectAttributeDO findByNameAndNamespace(String objectName, 
			String namespace, String attributeName);
	
	ObjectAttributeDO findByName(String objectName, String attributeName);
	
	List<ObjectAttributeDO> findAllByObjectNameAndNamespace(String objectName, 
			String namespace);

	List<ObjectAttributeDO> findAllByObjectName(String objectName);

}
