package com.palm.cloud.services.cmdb;

import org.springframework.test.jpa.AbstractJpaTests;

@SuppressWarnings("deprecation")
public class BaseJpaTest extends AbstractJpaTests {

	@Override
	protected String[] getConfigLocations() {
		return new String[] {
			"file:src/test/resources/applicationContext-test.xml"
		};
	}

}
