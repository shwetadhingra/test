package com.palm.cloud.services.cmdb.dao;

import com.palm.cloud.services.cmdb.entity.MetaStatusDO;

public interface IMetaStatusDAO extends IGenericDAO<MetaStatusDO, Integer> {

	MetaStatusDO findByName(String status);

}
