package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import todo.app.todo.TodoController;

@SpringBootApplication
@ComponentScan
@ImportResource({"classpath:META-INF/spring/todo-domain.xml",
	             "classpath:META-INF/spring/spring-security.xml",
	             "classpath:META-INF/spring/spring-mvc.xml"})
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoController.class, args);
    }
}
