package org.sample.validation.common.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.terasoluna.gfw.common.exception.ResultMessagesNotificationException;

public class AuditInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuditInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String className = invocation.getThis().getClass().getSimpleName();
        String methodName = invocation.getMethod().getName();
        Object[] args = invocation.getArguments();
        logger.debug("{}[{}] is beeing executed{}", className, methodName,
                (args.length > 0) ? " with argument(s): ".concat(StringUtils.arrayToDelimitedString(args, ", ")) : ".");

        Object returnObj = null;

        try {
            returnObj = invocation.proceed();
        } catch (ResultMessagesNotificationException re) {
            // don't output this type exception
            throw re;
        } catch (Throwable t) {
            logger.debug("Exception occurred in {}[{}].", className, methodName, t);
            throw t;
        }

        logger.debug("{}[{}] was executed successfully.", className, methodName);
        return returnObj;
    }
}
