package todo.with_db.domain.service.todo;

import todo.with_db.domain.model.Todo;

import java.util.Collection;

/**
 * Created by mash on 2015/10/01.
 */
public interface TodoService {
    Collection<Todo> findAll();

    Todo create(Todo todo);

    Todo finish(String todoId);

    void delete(String todoId);
}
