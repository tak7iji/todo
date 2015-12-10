package todo.app.todo;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionAttributeListener implements HttpSessionAttributeListener {
    private static final Logger logger = LoggerFactory.getLogger(SessionAttributeListener.class);

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        logger.info("Attribute {} added to {}", event.getName(), event.getSession().getId());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        logger.info("Attribute {} removed from {}", event.getName(), event.getSession().getId());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        logger.info("Attribute {} replaced on {}", event.getName(), event.getSession().getId());
    }

}
