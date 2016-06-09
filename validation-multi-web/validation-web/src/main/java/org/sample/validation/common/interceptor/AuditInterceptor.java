package org.sample.validation.common.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory
            .getLogger(AuditInterceptor.class);
    
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String methodName = invocation.getMethod().getName();
		MDC.put("METHOD", methodName);

		Object returnObj = null;
		
		try {
	        returnObj = invocation.proceed();
	        if (returnObj == null) {
	            return returnObj;
	        }
		} catch (Throwable t) {
			MDC.put("STATUS", "Exception");
			throw t;
		}

		MDC.put("STATUS", "SUCCESS");
        return returnObj;
	}

}
