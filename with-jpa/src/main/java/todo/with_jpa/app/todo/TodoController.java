package todo.with_jpa.app.todo;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.common.message.StandardResultMessageType;

import todo.with_jpa.app.todo.TodoForm.TodoDelete;
import todo.with_jpa.domain.model.Todo;
import todo.with_jpa.domain.service.todo.TodoService;

import javax.inject.Inject;
import javax.validation.groups.Default;

/**
 * Created by mash on 2015/10/01.
 */
@Controller
@RequestMapping("todo")
public class TodoController {
	private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
	
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
    public String list(@PageableDefault(5) Pageable pageable, 
    		Model model) {
        Page<Todo> todos = todoService.findAll(pageable);
        logger.info("list:page={}", pageable.getPageNumber());
        model.addAttribute("todos", todos);
        return "todo/list";
    }
    
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(@PageableDefault(5) Pageable pageable, 
    		Model model) {
    	return list(pageable, model);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Validated({ Default.class, TodoForm.TodoCreate.class }) TodoForm todoForm,
                         BindingResult bindingResult,
                         @PageableDefault(5) Pageable pageable,
                         Model model, RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
//            return list(pageable, model);
        	ResultMessages rms = new ResultMessages(StandardResultMessageType.ERROR);
        	for(FieldError fe : bindingResult.getFieldErrors()) {
        		rms.add(ResultMessage.fromText(fe.getDefaultMessage()));
        	}
        	attributes.addFlashAttribute(rms);
        	return "redirect:/todo/list";
        }

        Todo todo = beanMapper.map(todoForm, Todo.class);

        try {
            todoService.create(todo);
        } catch (BusinessException e) {
//            model.addAttribute(e.getResultMessages());
//            return list(pageable, model);
        	attributes.addFlashAttribute(e.getResultMessages());
        	return "redirect:/todo/list";
        }

        attributes.addFlashAttribute(ResultMessages.success().add(
                ResultMessage.fromText("Created successfully!")));
        logger.info("create:page={}", pageable.getPageNumber());
        attributes.addAttribute("page", pageable.getPageNumber());
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
        attributes.addAttribute("page", pageable.getPageNumber());
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
        attributes.addAttribute("page", pageable.getPageNumber());
        return "redirect:/todo/list";
    }

}
