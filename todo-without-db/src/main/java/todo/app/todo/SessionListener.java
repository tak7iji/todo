package todo.app.todo;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListener implements HttpSessionListener {
    private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        logger.info("Session {} created", event.getSession().getId());

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        logger.info("Session {} destroyed", event.getSession().getId());

    }

}
