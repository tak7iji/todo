package todo.app.todo;

import java.util.Collection;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import todo.app.todo.TodoForm.TodoDelete;
import todo.domain.model.Todo;
import todo.domain.service.todo.TodoService;

/**
 * Created by mash on 2015/10/01.
 */
@Controller
@RequestMapping("todo")
@TransactionTokenCheck("todo")
public class TodoController {
	private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

	@Inject
	TodoService todoService;

	@Inject
	Mapper beanMapper;

	@Inject
	SessionData sessionData;
	
	@ModelAttribute
	public TodoForm setUpTodoForm() {
		TodoForm form = new TodoForm();
		return form;
	}
	
//	@ModelAttribute
//	public SessionData setUpSessionData() {
//		return sessionData;
//	}

//	@TransactionTokenCheck(value="create", type=TransactionTokenType.BEGIN)
	@RequestMapping(value = "list")
	public String list(Model model) {
		Collection<Todo> todos = todoService.findAll();
		model.addAttribute("todos", todos);
		logger.info("Hash: {}", new BCryptPasswordEncoder().matches("demo", "$2a$10$knIF9TtzQuoz4SfDXOiQ8.XIoAaWGfi3uA7yvoC7l9AxaF4DZETmq"));
		return "todo/list";
	}

	@TransactionTokenCheck(value="create", type=TransactionTokenType.BEGIN)
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(
	        @Validated({ Default.class,
	                TodoForm.TodoCreate.class }) TodoForm todoForm,
	        BindingResult bindingResult, Model model,
	        RedirectAttributes attributes) {

		if (bindingResult.hasErrors()) {
			return list(model);
		}

		logger.info("Message: {}", sessionData.getMessage());
		sessionData.setMessage(todoForm.getTodoTitle());

		// Todo todo = beanMapper.map(todoForm, Todo.class);
		//
		// try {
		// todoService.create(todo);
		// } catch (BusinessException e) {
		// model.addAttribute(e.getResultMessages());
		// return list(model);
		// }

		// attributes.addFlashAttribute(ResultMessages.success().add("i.td.ct.0001"));
		// return "redirect:/todo/list";
		return "todo/confirm";
	}

	@TransactionTokenCheck(value="create", type=TransactionTokenType.IN)
	@RequestMapping(value = "confirm", method = RequestMethod.POST)
	public String confirm(TodoForm todoForm, Model model, RedirectAttributes attributes) {
		logger.info("Message: {}", sessionData.getMessage());

		Todo todo = beanMapper.map(todoForm, Todo.class);

		try {
			todoService.create(todo);
		} catch (BusinessException e) {
			logger.error("{}", e.getCause());

			model.addAttribute(e.getResultMessages());
			return list(model);
		}

		logger.info("Add new Todo.");

		attributes.addFlashAttribute(ResultMessages.success().add("i.td.ct.0001"));
		return "redirect:/todo/complete";
	}
	
	@RequestMapping(value = "complete", method = RequestMethod.GET)
	public String complete(Model model) {
		logger.info("Message: {}", sessionData.getMessage());
		sessionData.clearMessage();
		return "todo/complete";
	}

	@RequestMapping(value = "finish", method = RequestMethod.POST)
	public String finish(
	        @Validated({ Default.class,
	                TodoForm.TodoFinish.class }) TodoForm form,
	        BindingResult bindingResult, Model model,
	        RedirectAttributes attributes) {
		if (bindingResult.hasErrors()) {
			return list(model);
		}

		try {
			todoService.finish(form.getTodoId());
		} catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return list(model);
		}

		attributes.addFlashAttribute(
		        ResultMessages.success().add("i.td.ct.0002"));
		return "redirect:/todo/list";
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(
	        @Validated({ Default.class, TodoDelete.class }) TodoForm form,
	        BindingResult bindingResult, Model model,
	        RedirectAttributes attributes) {

		if (bindingResult.hasErrors()) {
			return list(model);
		}

		try {
			todoService.delete(form.getTodoId());
		} catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return list(model);
		}

		attributes.addFlashAttribute(
		        ResultMessages.success().add("i.td.ct.0003"));
		return "redirect:/todo/list";
	}

}
