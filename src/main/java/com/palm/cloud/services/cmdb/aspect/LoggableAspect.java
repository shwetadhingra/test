package com.palm.cloud.services.cmdb.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect class that advises any method which is annotated 
 * with {@link Loggable} annotation.
 *  
 * @author jjanakiraman
 *
 */
@Aspect
public class LoggableAspect {

	private static Logger log = Logger.getLogger(LoggableAspect.class);
	
	/**
	 * Defines the point-cut for the aspect.
	 */
	@Pointcut("execution(@Loggable * *.*(..))")
	void loggableExecution() {
		
	}
	
	/**
	 * Advises a method by adding log statements at start and end of the
	 * method along with duration of execution.
	 * 
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	@Around("loggableExecution()")
	public Object log(ProceedingJoinPoint jp) throws Throwable {
		long startTime = System.currentTimeMillis();
		log.info(jp.getSignature() + "::Begin");
		try {
			return jp.proceed();
		} catch (Throwable t) {
			log.error(jp.getSignature(), t);
			throw t;
		} finally {
			log.info(jp.getSignature() + "::End - Duration: " 
					+ (System.currentTimeMillis() - startTime));
		}
	}

}
