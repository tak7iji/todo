package todo.app.todo;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData implements Serializable, HttpSessionBindingListener {
	private static final Logger logger = LoggerFactory.getLogger(SessionData.class);
	
	public String name = "sessionData";

	@PostConstruct
	public void initSessionData() {
		logger.info("SessionData initialized.");
	}
	
	@PreDestroy
	public void destroySessionData() {
        logger.info("SessionData destroyed.");	    
	}
	
	private static final long serialVersionUID = 1L;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void clearMessage() {
		this.message = null;
	}

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        logger.info("SessionData bound to session {}", event.getSession().getId());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        logger.info("SessionData unbound from session {}", event.getSession().getId());
    }
}
