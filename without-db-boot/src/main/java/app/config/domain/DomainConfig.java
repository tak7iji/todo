package app.config.domain;
import javax.inject.Inject;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.terasoluna.gfw.common.exception.ExceptionLogger;
import org.terasoluna.gfw.common.exception.ResultMessagesLoggingInterceptor;

@Configuration
@ComponentScan("todo.domain")
public class DomainConfig {
	
	@Bean
	@Inject
	public ResultMessagesLoggingInterceptor resultMessagesLoggingInterceptor(ExceptionLogger exceptionLogger) {
		ResultMessagesLoggingInterceptor resultMessagesLoggingInterceptor = new ResultMessagesLoggingInterceptor();
		resultMessagesLoggingInterceptor.setExceptionLogger(exceptionLogger);
		
		return resultMessagesLoggingInterceptor;
	}

	@Bean
	@Inject
	public Advisor resultMessagesLogging(ResultMessagesLoggingInterceptor resultMessagesLoggingInterceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("@within(org.springframework.stereotype.Service)");
        return new DefaultPointcutAdvisor(pointcut, resultMessagesLoggingInterceptor);
	}
}
