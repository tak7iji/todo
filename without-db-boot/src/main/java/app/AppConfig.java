package app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.terasoluna.gfw.web.logging.HttpSessionEventLoggingListener;
import org.terasoluna.gfw.web.logging.mdc.MDCClearFilter;
import org.terasoluna.gfw.web.logging.mdc.XTrackMDCPutFilter;

@Configuration
@ImportResource({"classpath:META-INF/spring/applicationContext.xml",
    			 "classpath:META-INF/spring/spring-mvc.xml"})
public class AppConfig extends WebMvcConfigurerAdapter {
	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventLoggingListener>  httpSessionEventLoggingListener() {
		ServletListenerRegistrationBean<HttpSessionEventLoggingListener> listenerRegBean = new ServletListenerRegistrationBean<HttpSessionEventLoggingListener>();
		listenerRegBean.setListener(new HttpSessionEventLoggingListener());
		return listenerRegBean;
	}
	
	@Bean
	public FilterRegistrationBean mdcClearFilter() {
	    FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
	    filterRegBean.setName("MDCClearFilter");
	    filterRegBean.setFilter(new MDCClearFilter());
	    List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("/*");
	    filterRegBean.setUrlPatterns(urlPatterns);
	    return filterRegBean;
	}
	
	@Bean
	public FilterRegistrationBean exceptionLoggingFilter() {
	    FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
	    filterRegBean.setName("exceptionLoggingFilter");
	    filterRegBean.setFilter(new DelegatingFilterProxy());
	    List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("/*");
	    filterRegBean.setUrlPatterns(urlPatterns);
	    return filterRegBean;
	}

	@Bean
	public FilterRegistrationBean xtrackMDCPutFilter() {
	    FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
	    filterRegBean.setName("XTrackMDCPutFilter");
	    filterRegBean.setFilter(new XTrackMDCPutFilter());
	    List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("/*");
	    filterRegBean.setUrlPatterns(urlPatterns);
	    return filterRegBean;
	}

	@Bean
	public FilterRegistrationBean characterEncodingFilter() {
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
	public FilterRegistrationBean springSecurityFilterChain() {
	    FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
	    filterRegBean.setName("springSecurityFilterChain");
	    filterRegBean.setFilter(new DelegatingFilterProxy());
	    List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("/*");
	    filterRegBean.setUrlPatterns(urlPatterns);
	    return filterRegBean;
	}

}
