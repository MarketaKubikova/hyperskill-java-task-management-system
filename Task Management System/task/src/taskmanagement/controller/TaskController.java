package taskmanagement.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import taskmanagement.entity.Comment;
import taskmanagement.entity.Task;
import taskmanagement.model.*;
import taskmanagement.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getTasks(@RequestParam(value = "author", required = false, defaultValue = "") String author,
                                                          @RequestParam(value = "assignee", required = false, defaultValue = "") String assignee) {
        return ResponseEntity.ok(taskService.getTasksBy(author, assignee));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskRequestDto dto,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.createTask(dto, userDetails));
    }

    @PutMapping("/{taskId}/assign")
    public ResponseEntity<Task> assignTask(@PathVariable(value = "taskId") Long taskId,
                                           @RequestBody @Valid AssigneeRequestDto dto,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.assignTask(taskId, dto, userDetails));
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable(value = "taskId") Long taskId,
                                                 @RequestBody @Valid StatusRequestDto dto,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.updateTaskStatus(taskId, dto, userDetails));
    }

    @PostMapping("/{taskId}/comments")
    public ResponseEntity<Comment> createCommentOnTask(@PathVariable(value = "taskId") Long taskId,
                                                       @RequestBody @Valid CommentRequestDto request,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.createCommentOnTask(taskId, request, userDetails));
    }

    @GetMapping("/{taskId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByTaskId(@PathVariable(value = "taskId") Long taskId) {
        return ResponseEntity.ok(taskService.getCommentsByTaskId(taskId));
    }
}
