package taskmanagement.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import taskmanagement.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, String> {
    @NotNull List<Comment> findAllCommentsByTaskId(String taskId, @NotNull Sort sort);
}
