package todo.with_db.domain.service.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import todo.with_db.domain.model.todo.Todo;

/**
 * Created by mash on 2015/10/01.
 */
public interface TodoService {
    Page<Todo> findAll(Pageable pageable);

    Todo create(Todo todo);

    Todo finish(String todoId);

    void delete(String todoId);
    
    void deleteAll();
}
