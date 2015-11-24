package todo.with_db.domain.repository.todo;

import todo.with_db.domain.model.Todo;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

/**
 * Created by mash on 2015/10/01.
 */
public interface TodoRepository {
    Todo findOne(String todoId);

    long countAll();
    
    List<Todo> findAll(RowBounds rowBounds);

    void create(Todo todo);

    boolean update(Todo todo);

    void delete(Todo todo);

    long countByFinished(boolean finished);
}
