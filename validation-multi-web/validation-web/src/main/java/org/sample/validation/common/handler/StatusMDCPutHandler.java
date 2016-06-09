package org.sample.validation.common.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class StatusMDCPutHandler extends HandlerInterceptorAdapter {
	   private static final Logger logger = LoggerFactory
	            .getLogger(StatusMDCPutHandler.class);
	   
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("preHandle called!");
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        MDC.put("METHOD", handlerMethod.getMethod().getName());
        return true;
	}
	
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		MDC.put("STATUS", (ex != null) ? "EXCEPTION" : "SUCCESS");
		logger.info("");
	}

}
