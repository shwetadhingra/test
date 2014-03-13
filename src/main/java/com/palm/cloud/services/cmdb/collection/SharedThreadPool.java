package com.palm.cloud.services.cmdb.collection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SharedThreadPool {

	private static final int DEFAULT_NORMAL_THREAD_POOL_SIZE = 8;
	
	private static SharedThreadPool INSTANCE;
	
	private ExecutorService normalThreadPool;
	
	private SharedThreadPool() {
		this.normalThreadPool = Executors.newFixedThreadPool(
				DEFAULT_NORMAL_THREAD_POOL_SIZE);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				SharedThreadPool.this.normalThreadPool.shutdown();
			}
		});
	}
	
	public static synchronized SharedThreadPool getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SharedThreadPool();
		}
		return INSTANCE;
	}
	
	public ExecutorService getNormalThreadPool() {
		return this.normalThreadPool;
	}
	
}
