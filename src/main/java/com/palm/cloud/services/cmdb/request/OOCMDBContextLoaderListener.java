package com.palm.cloud.services.cmdb.request;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.palm.cloud.services.cmdb.collection.SharedThreadPool;

public class OOCMDBContextLoaderListener extends ContextLoaderListener {

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		SharedThreadPool.getInstance().shutdown();
		super.contextDestroyed(event);
	}
	
}
