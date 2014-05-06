package com.palm.cloud.services.cmdb.collection;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class SharedThreadPool {
	
	private static final Logger log = Logger.getLogger(SharedThreadPool.class);

	private static final int DEFAULT_NORMAL_THREAD_POOL_SIZE = 8;
	
	private static final long SHUTDOWN_TIMEOUT_MS = 2000;
	
	private static SharedThreadPool INSTANCE;
	
	private ExecutorService normalThreadPool;
	
	private SharedThreadPool() {
		this.normalThreadPool = Executors.newFixedThreadPool(
				DEFAULT_NORMAL_THREAD_POOL_SIZE);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				shutdown();
			}
		});
	}
	
	public static synchronized SharedThreadPool getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SharedThreadPool();
		}
		return INSTANCE;
	}
	
	public <T> Future<T> submit(Callable<T> task) {
		return this.normalThreadPool.submit(task);
	}
	
	public Future<?> submit(Runnable task) {
		return this.normalThreadPool.submit(task);
	}
	
	public <T> Future<T> submit(Runnable task, T result) {
		return this.normalThreadPool.submit(task, result);
	}

	public void shutdown() {
		this.normalThreadPool.shutdown();
		try {
			if (!this.normalThreadPool.awaitTermination(
					SHUTDOWN_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
				log.info("Thread pool did not terminate within " 
						+ SHUTDOWN_TIMEOUT_MS + "ms");
				this.normalThreadPool.shutdownNow();
				log.info("Thead pool terminated forcefully");
			}
		} catch (InterruptedException ie) {
			log.error("Exception while shutting down thread pool", ie);
		}
	}

}
