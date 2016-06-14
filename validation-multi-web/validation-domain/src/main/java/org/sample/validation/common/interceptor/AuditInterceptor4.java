package org.sample.validation.common.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class AuditInterceptor4 {

    private static final Logger logger = LoggerFactory.getLogger(AuditInterceptor4.class);

    public Object auditMethod(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        logger.debug("{}[{}] is beeing executed{}", className, methodName,
                (args.length > 0) ? " with argument(s): ".concat(StringUtils.arrayToDelimitedString(args, ", ")) : ".");

        Object returnObj = null;

        try {
            returnObj = pjp.proceed();
        } catch (Throwable t) {
            logger.debug("Exception occurred in {}[{}].", className, methodName, t);
            throw t;
        }

        logger.debug("{}[{}] was executed successfully.", className, methodName);
        return returnObj;
    }
}
