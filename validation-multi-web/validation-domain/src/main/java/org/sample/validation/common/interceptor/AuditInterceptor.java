package org.sample.validation.common.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jboss.logging.MDC;
import org.sample.validation.common.annotation.AuditParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.util.ClassUtils;

public class AuditInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory
            .getLogger(AuditInterceptor.class);
    
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String methodName = invocation.getMethod().getName();
		MDC.put("METHOD", methodName);
		
		logger.info("{}", invocation.toString());
		
		Class<?> klass = invocation.getThis().getClass();
		Method method = klass.getMethod(methodName, invocation.getMethod().getParameterTypes());
		
		logger.info("{}", method.getDeclaringClass().getName());
		Object[] arguments = invocation.getArguments();
		int i=0;
		for(Annotation[] annotations : method.getParameterAnnotations()){
			for(Annotation annotation: annotations){
				if(annotation instanceof AuditParam)
					logger.info("{} : {}", annotation.toString(), arguments[i]);
			}
			i++;
		}

		
		
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
