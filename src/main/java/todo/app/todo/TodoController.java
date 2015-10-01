package todo.app.todo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import todo.domain.model.Todo;
import todo.domain.service.todo.TodoService;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by mash on 2015/10/01.
 */
@Controller
@RequestMapping("todo")
public class TodoController {
    @Inject
    TodoService todoService;

    @ModelAttribute
    public TodoForm setUpForm() {
        TodoForm form = new TodoForm();
        return form;
    }

    @RequestMapping(value = "list")
    public String list(Model model) {
        Collection<Todo> todos = todoService.findAll();
        model.addAttribute("todos", todos);
        return "todo/list";
    }
}
