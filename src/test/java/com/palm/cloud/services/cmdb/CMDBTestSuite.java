package com.palm.cloud.services.cmdb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.palm.cloud.services.cmdb.collection.CollectionServiceTests;
import com.palm.cloud.services.cmdb.collection.xml.XMLParserTests;
import com.palm.cloud.services.cmdb.dao.DAOTests;
import com.palm.cloud.services.cmdb.entity.EntityTests;
import com.palm.cloud.services.cmdb.resource.ResourceTests;
import com.palm.cloud.services.cmdb.service.DataServiceTests;
import com.palm.cloud.services.cmdb.service.MetaServiceTests;

@RunWith(Suite.class)
@SuiteClasses({
	EntityTests.class,
	DAOTests.class,
	MetaServiceTests.class,
	DataServiceTests.class,
	ResourceTests.class,
	XMLParserTests.class,
	CollectionServiceTests.class
})
public class CMDBTestSuite {

}
