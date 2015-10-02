package todo.domain.repository.todo;

import todo.domain.model.Todo;

import java.util.Collection;

/**
 * Created by mash on 2015/10/01.
 */
public interface TodoRepository {
    Todo findOne(String todoId);

    Collection<Todo> findAll();

    void create(Todo todo);

    boolean update(Todo todo);

    void delete(Todo todo);

    long countByFinished(boolean finished);
}
