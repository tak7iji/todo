package todo.domain.service.todo;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import todo.domain.model.Todo;
import todo.domain.repository.todo.TodoRepository;

/**
 * Created by mash on 2015/10/01.
 */
@Service
@Transactional
public class TodoServiceImpl implements TodoService {

	@Value("2")
	private long MAX_UNFINISHED_COUNT;

    @Inject
    TodoRepository todoRepository;

    @Inject
    JodaTimeDateFactory dateFactory;
    
    public Todo findOne(String todoId) {
        Todo todo = todoRepository.findOne(todoId);
        if (todo == null) {

            ResultMessages messages = ResultMessages.error();
            messages.add("e.td.sv.4404", todoId);
            throw new ResourceNotFoundException(messages);
        }
        return todo;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Todo create(Todo todo) {
        long unfinishedCount = todoRepository.countByFinished(false);
        if (unfinishedCount >= MAX_UNFINISHED_COUNT) {
            ResultMessages messages = ResultMessages.error();
            messages.add("e.td.sv.2001", MAX_UNFINISHED_COUNT);
            throw new BusinessException(messages);
        }

        String todoId = UUID.randomUUID().toString();
        Date createdAt = dateFactory.newDateTime().toDate();

        todo.setTodoId(todoId);
        todo.setCreatedAt(createdAt);
        todo.setFinished(false);

        todoRepository.create(todo);
        /* REMOVE THIS LINE IF YOU USE JPA
            todoRepository.save(todo); // 10
           REMOVE THIS LINE IF YOU USE JPA */

        return todo;
    }

    @Override
    public Todo finish(String todoId) {
        Todo todo = findOne(todoId);
        if (todo.isFinished()) {
            ResultMessages messages = ResultMessages.error();
            messages.add("e.td.sv.2002", todoId);
            throw new BusinessException(messages);
        }
        todo.setFinished(true);
        todoRepository.update(todo);
        /* REMOVE THIS LINE IF YOU USE JPA
            todoRepository.save(todo); // (11)
           REMOVE THIS LINE IF YOU USE JPA */
        return todo;
    }

    @Override
    public void delete(String todoId) {
        Todo todo = findOne(todoId);
        todoRepository.delete(todo);
    }
}
