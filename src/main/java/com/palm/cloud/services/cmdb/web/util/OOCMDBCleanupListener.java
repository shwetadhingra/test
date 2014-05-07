package com.palm.cloud.services.cmdb.web.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.palm.cloud.services.cmdb.collection.SharedThreadPool;

public class OOCMDBCleanupListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		SharedThreadPool.getInstance().shutdown();
	}
	
}
