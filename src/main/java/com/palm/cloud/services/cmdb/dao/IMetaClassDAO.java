package com.palm.cloud.services.cmdb.dao;

import com.palm.cloud.services.cmdb.entity.MetaClassDO;

public interface IMetaClassDAO 
		extends IGenericDAO<MetaClassDO, Integer> {

	MetaClassDO findByName(String name);

	MetaClassDO findByNameAndType(String name, boolean isRelationshipType);

}
