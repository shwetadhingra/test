package com.palm.cloud.services.cmdb.dao;

import com.palm.cloud.services.cmdb.entity.MetaClassAttributeDO;

public interface IMetaClassAttributeDAO 
		extends IGenericDAO<MetaClassAttributeDO, Integer> {

	MetaClassAttributeDO findByTypeNameAndAttributeName(String typeName, 
			String attributeName);

}
