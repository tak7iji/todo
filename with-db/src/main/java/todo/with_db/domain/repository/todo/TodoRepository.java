package todo.with_db.domain.repository.todo;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import todo.with_db.domain.model.todo.Todo;

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
    
    void deleteAll();

    long countByFinished(boolean finished);
}
