package app.config.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.terasoluna.gfw.web.logging.HttpSessionEventLoggingListener;
import org.terasoluna.gfw.web.logging.mdc.MDCClearFilter;
import org.terasoluna.gfw.web.logging.mdc.XTrackMDCPutFilter;

@Configuration
public class WebXmlConfig extends WebMvcConfigurerAdapter {
	
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
}
