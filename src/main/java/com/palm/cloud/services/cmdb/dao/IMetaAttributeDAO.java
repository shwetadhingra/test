package com.palm.cloud.services.cmdb.dao;

import com.palm.cloud.services.cmdb.entity.MetaAttributeDO;

public interface IMetaAttributeDAO 
		extends IGenericDAO<MetaAttributeDO, Integer> {

	MetaAttributeDO findByName(String attributeName);

}
