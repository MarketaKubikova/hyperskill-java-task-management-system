package taskmanagement.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import taskmanagement.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>, ListPagingAndSortingRepository<Task, Long> {
    @NotNull List<Task> findAll(@NotNull Sort sort);

    List<Task> findByAuthorIgnoreCaseOrderByCreatedDesc(String author);

    List<Task> findByAssigneeIgnoreCaseOrderByCreatedDesc(String assignee);

    List<Task> findByAuthorIgnoreCaseAndAssigneeIgnoreCaseOrderByCreatedDesc(String author, String assignee);
}
