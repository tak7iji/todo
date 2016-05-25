package app.config.web;

import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.support.RequestDataValueProcessor;
import org.terasoluna.gfw.common.exception.ExceptionLogger;
import org.terasoluna.gfw.web.codelist.CodeListInterceptor;
import org.terasoluna.gfw.web.exception.HandlerExceptionResolverLoggingInterceptor;
import org.terasoluna.gfw.web.logging.TraceLoggingInterceptor;
import org.terasoluna.gfw.web.mvc.support.CompositeRequestDataValueProcessor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenInterceptor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenRequestDataValueProcessor;

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

}
