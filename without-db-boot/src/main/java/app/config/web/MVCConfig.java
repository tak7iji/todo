package app.config.web;

import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.CacheControl;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.terasoluna.gfw.common.exception.ExceptionCodeResolver;
import org.terasoluna.gfw.common.exception.ExceptionLogger;
import org.terasoluna.gfw.web.codelist.CodeListInterceptor;
import org.terasoluna.gfw.web.exception.HandlerExceptionResolverLoggingInterceptor;
import org.terasoluna.gfw.web.exception.SystemExceptionResolver;
import org.terasoluna.gfw.web.logging.TraceLoggingInterceptor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenInterceptor;

@Configuration
@ComponentScan("todo.app")
public class MVCConfig extends WebMvcConfigurerAdapter {
	public static Logger logger = LoggerFactory.getLogger(WebMvcConfigurerAdapter.class);
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		logger.info("Add ArgumentResolvers");
		argumentResolvers.add(pageableHandlerMethodArgumentResolver());
		argumentResolvers.add(authenticationPrincipalArgumentResolver());
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		logger.info("Add Interceptors");
		registry.addInterceptor(traceLoggingInterceptor()).addPathPatterns("/**").excludePathPatterns("/resources/**", "/**/*.html");
		registry.addInterceptor(transactionTokenInterceptor()).addPathPatterns("/**").excludePathPatterns("/resources/**", "/**/*.html");
		registry.addInterceptor(codeListInterceptor()).addPathPatterns("/**").excludePathPatterns("/resources/**", "/**/*.html");
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.beanName();
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("resources/**").addResourceLocations("/resources/", "classpath:META-INF/resources/").setCachePeriod(3600);
	}

	@Bean
	public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver() {
		return new PageableHandlerMethodArgumentResolver();
	}
	
	@Bean
	public AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver() {
		return new AuthenticationPrincipalArgumentResolver();
	}
	
	@Bean
	public TraceLoggingInterceptor traceLoggingInterceptor() {
		TraceLoggingInterceptor traceLoggingInterceptor = new TraceLoggingInterceptor();
		return traceLoggingInterceptor;
	}
	
	@Bean
	public TransactionTokenInterceptor transactionTokenInterceptor() {
		TransactionTokenInterceptor transactionTokenInterceptor = new TransactionTokenInterceptor();
		return transactionTokenInterceptor;
	}
	
	@Bean
	public CodeListInterceptor codeListInterceptor() {
		CodeListInterceptor codeListInterceptor = new CodeListInterceptor();
		codeListInterceptor.setCodeListIdPattern(Pattern.compile("CL_.+"));
		return codeListInterceptor;
	}
	
	@Bean
	@Inject
	public HandlerExceptionResolverLoggingInterceptor handlerExceptionResolverLoggingInterceptor(ExceptionLogger exceptionLogger) {
		HandlerExceptionResolverLoggingInterceptor handlerExceptionResolverLoggingInterceptor = new HandlerExceptionResolverLoggingInterceptor();
		handlerExceptionResolverLoggingInterceptor.setExceptionLogger(exceptionLogger);
		return handlerExceptionResolverLoggingInterceptor;
	}
	
	@Bean
	@Inject
	public Advisor handlerExceptionResolverLogging(HandlerExceptionResolverLoggingInterceptor handlerExceptionResolverLoggingInterceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* org.springframework.web.servlet.HandlerExceptionResolver.resolveException(..))");
        return new DefaultPointcutAdvisor(pointcut, handlerExceptionResolverLoggingInterceptor);
	}

	@Bean(name="systemExceptionResolver")
	@Inject
	public SystemExceptionResolver systemExceptionResolver(ExceptionCodeResolver exceptionCodeResolver) {
		logger.info("Add SystemExceptionResolver");
		
		Properties mappings = new Properties();
		mappings.setProperty("ResourceNotFoundException", "common/error/resourceNotFoundError");
		mappings.setProperty("BusinessException", "common/error/businessError");
		mappings.setProperty("InvalidTransactionTokenException", "common/error/transactionTokenError");
		mappings.setProperty(".DataAccessException", "common/error/dataAccessError");
		
		Properties statusCodes = new Properties();
		statusCodes.setProperty("common/error/resourceNotFoundError", "404");
		statusCodes.setProperty("common/error/businessError", "409");
		statusCodes.setProperty("common/error/transactionTokenError", "409");
		statusCodes.setProperty("common/error/dataAccessError", "500");
		
		SystemExceptionResolver systemExceptionResolver = new SystemExceptionResolver();
		systemExceptionResolver.setExceptionCodeResolver(exceptionCodeResolver);
		systemExceptionResolver.setExceptionMappings(mappings);
		systemExceptionResolver.setStatusCodes(statusCodes);
		systemExceptionResolver.setOrder(3);
		systemExceptionResolver.setDefaultErrorView("common/error/systemError");
		systemExceptionResolver.setDefaultStatusCode(500);
		
		return systemExceptionResolver;
	}
}
