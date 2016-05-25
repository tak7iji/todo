package app.config.web;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.terasoluna.gfw.common.exception.ExceptionLogger;
import org.terasoluna.gfw.common.exception.SimpleMappingExceptionCodeResolver;
import org.terasoluna.gfw.web.exception.ExceptionLoggingFilter;

@Configuration
public class AppConfig {
	
	@Value("classpath*:/META-INF/dozer/**/*-mapping.xml")
	private Resource[] mappingFiles;
	
    @Bean
    public static PropertySourcesPlaceholderConfigurer  getPropertyPlaceholderConfigurer()
            throws IOException {
    	PropertySourcesPlaceholderConfigurer  ppc = new PropertySourcesPlaceholderConfigurer ();
        ppc.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/META-INF/spring/*.properties"));
        return ppc;
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
