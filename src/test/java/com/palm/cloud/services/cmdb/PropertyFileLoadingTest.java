package com.palm.cloud.services.cmdb;

import java.util.Properties;

import org.junit.Test;

public class PropertyFileLoadingTest {

	@Test
	public void testLoad() {
		try {
			Properties properties = new Properties();
			properties.load(this.getClass()
					.getResourceAsStream("/jdbc-test.properties"));
			for (Object key : properties.keySet()) {
				System.out.println("Key: " + key + " - Value: " 
						+ properties.getProperty(key.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
