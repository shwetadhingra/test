package com.palm.cloud.services.cmdb.resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.palm.cloud.services.cmdb.collection.ICollectionService;
import com.palm.cloud.services.cmdb.service.ICMDBDataService;
import com.palm.cloud.services.cmdb.service.ICMDBMetaService;

public abstract class AbstractBaseResource {

	@Autowired
	protected ICMDBMetaService cmdbMetaService;
	
	@Autowired
	protected ICMDBDataService cmdbDataService;
	
	@Autowired
	protected ICollectionService collectionService;

}
