package app;

import java.util.LinkedHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.DelegatingAccessDeniedHandler;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.terasoluna.gfw.security.web.logging.UserIdMDCPutFilter;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.headers().cacheControl().contentTypeOptions().httpStrictTransportSecurity().xssProtection().and().
		csrf().and().
		exceptionHandling().accessDeniedHandler(accessDeniedHandler()).and().
		addFilterAfter(userIdMDCPutFilter(), AnonymousAuthenticationFilter.class).
		sessionManagement();
    }


	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
	}
	
	@Bean
	public DelegatingAccessDeniedHandler accessDeniedHandler() {
		LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler> handlers = new LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler>();
		handlers.put(InvalidCsrfTokenException.class, handler("/WEB-INF/views/common/error/invalidCsrfTokenError.jsp"));
		handlers.put(MissingCsrfTokenException.class, handler("/WEB-INF/views/common/error/invalidCsrfTokenError.jsp"));
		
		DelegatingAccessDeniedHandler handler = new DelegatingAccessDeniedHandler(handlers,
				handler("/WEB-INF/views/common/error/missingCsrfTokenError.jsp"));
		return handler;
	}
	
	@Bean
	AccessDeniedHandlerImpl handler(String errorPage) {
		AccessDeniedHandlerImpl handler = new AccessDeniedHandlerImpl();
		handler.setErrorPage(errorPage);
		return handler;
	}
	
	@Bean
	public UserIdMDCPutFilter userIdMDCPutFilter() {
		return new UserIdMDCPutFilter();
	}
}
