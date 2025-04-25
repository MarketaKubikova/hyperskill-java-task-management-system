package taskmanagement.mapper;

import org.springframework.stereotype.Component;
import taskmanagement.entity.Task;
import taskmanagement.model.TaskResponseDto;

@Component
public class TaskMapper {
    public TaskResponseDto toResponseDto(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().getValue(),
                task.getAuthor(),
                task.getAssignee(),
                task.getComments().size()
        );
    }
}
