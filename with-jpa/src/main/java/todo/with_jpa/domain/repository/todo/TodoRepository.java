package todo.with_jpa.domain.repository.todo;

import todo.with_jpa.domain.model.Todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by mash on 2015/10/01.
 */
public interface TodoRepository extends JpaRepository<Todo, String> {

    @Query("SELECT COUNT(t) FROM Todo t WHERE t.finished = :finished")
    long countByFinished(@Param("finished") boolean finished);

    Page<Todo> findAll(Pageable pageable);
}