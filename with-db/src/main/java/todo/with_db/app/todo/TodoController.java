package todo.with_db.app.todo;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

import todo.with_db.app.todo.TodoForm.TodoDelete;
import todo.with_db.domain.model.Todo;
import todo.with_db.domain.service.todo.TodoService;

import javax.inject.Inject;
import javax.validation.groups.Default;

/**
 * Created by mash on 2015/10/01.
 */
@Controller
@RequestMapping("todo")
public class TodoController {
    @Inject
    TodoService todoService;

    @Inject
    Mapper beanMapper;

    @ModelAttribute
    public TodoForm setUpForm() {
        TodoForm form = new TodoForm();
        return form;
    }

    @RequestMapping(value = "list")
    public String list(@PageableDefault(5) Pageable pageable, Model model) {
        Page<Todo> todos = todoService.findAll(pageable);
        model.addAttribute("todos", todos);
        return "todo/list";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Validated({ Default.class, TodoForm.TodoCreate.class }) TodoForm todoForm,
                         BindingResult bindingResult,
                         @PageableDefault(5) Pageable pageable,
                         Model model, RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            return list(pageable, model);
        }

        Todo todo = beanMapper.map(todoForm, Todo.class);

        try {
            todoService.create(todo);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return list(pageable, model);
        }

        attributes.addFlashAttribute(ResultMessages.success().add(
                ResultMessage.fromText("Created successfully!")));
        return "redirect:/todo/list";
    }

    @RequestMapping(value = "finish", method = RequestMethod.POST)
    public String finish(
            @Validated({ Default.class, TodoForm.TodoFinish.class }) TodoForm form,
            BindingResult bindingResult, 
            @PageableDefault(5) Pageable pageable,
            Model model,
            RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return list(pageable, model);
        }

        try {
            todoService.finish(form.getTodoId());
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return list(pageable, model);
        }

        attributes.addFlashAttribute(ResultMessages.success().add(
                ResultMessage.fromText("Finished successfully!")));
        return "redirect:/todo/list";
    }
    
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(
            @Validated({ Default.class, TodoDelete.class }) TodoForm form,
            BindingResult bindingResult, 
            @PageableDefault(5) Pageable pageable,
            Model model,
            RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            return list(pageable, model);
        }

        try {
            todoService.delete(form.getTodoId());
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return list(pageable, model);
        }

        attributes.addFlashAttribute(ResultMessages.success().add(
                ResultMessage.fromText("Deleted successfully!")));
        return "redirect:/todo/list";
    }

    @RequestMapping(value = "deleteAll", method = RequestMethod.POST)
    public String deleteAll(
            TodoForm form,
            @PageableDefault(5) Pageable pageable,
            Model model,
            RedirectAttributes attributes) {

        try {
            todoService.deleteAll();
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return list(pageable, model);
        }

        attributes.addFlashAttribute(ResultMessages.success().add(
                ResultMessage.fromText("Deleted all todos successfully!")));
        return "redirect:/todo/list";
    }
}
