package todo.app.todo;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(SessionData.class);
	
	public String name = "sessionData";

	@PostConstruct
	public void initSessionData() {
		logger.info("SessionData initialized.");
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
}
