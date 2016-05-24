package app;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.terasoluna.gfw.common.exception.ExceptionLogger;
import org.terasoluna.gfw.common.exception.SimpleMappingExceptionCodeResolver;
import org.terasoluna.gfw.web.exception.ExceptionLoggingFilter;
import org.terasoluna.gfw.web.logging.HttpSessionEventLoggingListener;
import org.terasoluna.gfw.web.logging.mdc.MDCClearFilter;
import org.terasoluna.gfw.web.logging.mdc.XTrackMDCPutFilter;

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
	
	@Value("classpath*:/META-INF/dozer/**/*-mapping.xml")
	private Resource[] mappingFiles;
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer(){
	    return new CustomizerImpl();
	}

	private static class CustomizerImpl implements EmbeddedServletContainerCustomizer {

	    @Override
	    public void customize(ConfigurableEmbeddedServletContainer container) {
	        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/WEB-INF/views/common/error/systemError.jsp"),
	                                new ErrorPage(HttpStatus.NOT_FOUND, "/WEB-INF/views/common/error/resourceNotFoundError.jsp"),
    		                        new ErrorPage(Exception.class, "/WEB-INF/views/common/error/unhandledSystemError.jsp"));
	    }

	}
	
	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventLoggingListener>  httpSessionEventLoggingListener() {
		ServletListenerRegistrationBean<HttpSessionEventLoggingListener> listenerRegBean = new ServletListenerRegistrationBean<HttpSessionEventLoggingListener>();
		listenerRegBean.setListener(new HttpSessionEventLoggingListener());
		return listenerRegBean;
	}
	
	@Bean
	public FilterRegistrationBean registMdcClearFilter() {
	    FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
	    filterRegBean.setName("MDCClearFilter");
	    filterRegBean.setFilter(new MDCClearFilter());
	    List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("/*");
	    filterRegBean.setUrlPatterns(urlPatterns);
	    return filterRegBean;
	}
	
	@Bean
	public FilterRegistrationBean registExceptionLoggingFilter() {
	    FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
	    filterRegBean.setName("exceptionLoggingFilter");
	    filterRegBean.setFilter(new DelegatingFilterProxy());
	    List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("/*");
	    filterRegBean.setUrlPatterns(urlPatterns);
	    return filterRegBean;
	}
	
	

	@Bean
	public FilterRegistrationBean registXtrackMDCPutFilter() {
	    FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
	    filterRegBean.setName("XTrackMDCPutFilter");
	    filterRegBean.setFilter(new XTrackMDCPutFilter());
	    List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("/*");
	    filterRegBean.setUrlPatterns(urlPatterns);
	    return filterRegBean;
	}

	@Bean
	public FilterRegistrationBean registCharacterEncodingFilter() {
	    FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
	    filterRegBean.setName("CharacterEncodingFilter");
	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    characterEncodingFilter.setEncoding("UTF-8");
	    characterEncodingFilter.setForceEncoding(true);
	    filterRegBean.setFilter(characterEncodingFilter);
	    List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("/*");
	    filterRegBean.setUrlPatterns(urlPatterns);
	    return filterRegBean;
	}

	@Bean
	public FilterRegistrationBean registSpringSecurityFilterChain() {
	    FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
	    filterRegBean.setName("springSecurityFilterChain");
	    filterRegBean.setFilter(new DelegatingFilterProxy());
	    List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("/*");
	    filterRegBean.setUrlPatterns(urlPatterns);
	    return filterRegBean;
	}

	@Bean(name="beanMapper")
	public DozerBeanMapperFactoryBean beanMapper() {
		DozerBeanMapperFactoryBean beanMapper = new DozerBeanMapperFactoryBean();
		beanMapper.setMappingFiles(mappingFiles);
		
		return beanMapper;
	}
	
	@Bean
	public SimpleMappingExceptionCodeResolver exceptionCodeResolver() {
		SimpleMappingExceptionCodeResolver exceptionCodeResolver = new SimpleMappingExceptionCodeResolver();
		
		LinkedHashMap<String, String> exceptionMappings = new LinkedHashMap<String, String>();
		exceptionMappings.put("ResourceNotFoundException", "e.xx.fw.7001");
		exceptionMappings.put("BusinessException", "e.xx.fw.8001");
		exceptionMappings.put(".DataAccessException", "e.xx.fw.9002");
		
		exceptionCodeResolver.setExceptionMappings(exceptionMappings);
		exceptionCodeResolver.setDefaultExceptionCode("e.xx.fw.9001");
		
		return exceptionCodeResolver;
	}
	
	@Bean 
	public ExceptionLogger exceptionLogger() {
		ExceptionLogger exceptionLogger = new ExceptionLogger();
		exceptionLogger.setExceptionCodeResolver(exceptionCodeResolver());
		
		return exceptionLogger;
	}
	
	@Bean ExceptionLoggingFilter exceptionLoggingFilter() {
		ExceptionLoggingFilter exceptionLoggingFilter = new ExceptionLoggingFilter();
		exceptionLoggingFilter.setExceptionLogger(exceptionLogger());
		
		return exceptionLoggingFilter;
	}
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("i18n/application-messages");
		
		return messageSource;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
