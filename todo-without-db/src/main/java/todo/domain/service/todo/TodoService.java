package todo.domain.service.todo;

import java.util.Collection;

import todo.domain.model.Todo;

/**
 * Created by mash on 2015/10/01.
 */
public interface TodoService {
    Collection<Todo> findAll();

    Todo create(Todo todo);

    Todo finish(String todoId);

    void delete(String todoId);
}
