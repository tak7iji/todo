package todo.app.todo;

import java.util.Collection;

import javax.validation.groups.Default;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;

import todo.app.todo.TodoForm.TodoCreate;
import todo.app.todo.TodoForm.TodoDelete;
import todo.domain.model.Todo;
import todo.domain.service.todo.TodoService;

/**
 * Created by mash on 2015/10/01.
 */
@EnableAutoConfiguration
@Controller
@RequestMapping("todo")
public class TodoController {
	private static final Logger logger = LoggerFactory
	        .getLogger(TodoController.class);

	@Autowired
	TodoService todoService;

	@Autowired
	Mapper beanMapper;

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

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(
	        @Validated({ Default.class,
	                TodoCreate.class }) TodoForm todoForm,
	        BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return list(model);
		}

		Todo todo = beanMapper.map(todoForm, Todo.class);

		try {
			todoService.create(todo);
		} catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return list(model);
		}

		todoForm.setTodoTitle("");
		model.addAttribute("todoForm", todoForm);
		model.addAttribute(
		        ResultMessages.success().add("i.td.ct.0001"));
		return list(model);
	}

	@RequestMapping(value = "finish", method = RequestMethod.POST)
	public String finish(
	        @Validated({ Default.class,
	                TodoForm.TodoFinish.class }) TodoForm form,
	        BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return list(model);
		}

		try {
			todoService.finish(form.getTodoId());
		} catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return list(model);
		}

		model.addAttribute(
		        ResultMessages.success().add("i.td.ct.0002"));
		return list(model);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(
	        @Validated({ Default.class, TodoDelete.class }) TodoForm form,
	        BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return list(model);
		}

		try {
			todoService.delete(form.getTodoId());
		} catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return list(model);
		}

		model.addAttribute(
		        ResultMessages.success().add("i.td.ct.0003"));
		return list(model);
	}
}
