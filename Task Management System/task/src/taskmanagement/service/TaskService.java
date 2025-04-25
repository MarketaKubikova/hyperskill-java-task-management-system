package taskmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import taskmanagement.entity.Comment;
import taskmanagement.entity.Task;
import taskmanagement.entity.User;
import taskmanagement.exception.ForbiddenException;
import taskmanagement.exception.TaskNotFoundException;
import taskmanagement.exception.UsernameNotFoundException;
import taskmanagement.mapper.TaskMapper;
import taskmanagement.model.*;
import taskmanagement.repository.CommentRepository;
import taskmanagement.repository.TaskRepository;
import taskmanagement.repository.UserRepository;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, CommentRepository commentRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.taskMapper = taskMapper;
    }

    public Task createTask(TaskRequestDto dto, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UsernameNotFoundException::new);
        Task task = new Task(dto.title(), dto.description(), user);
        return taskRepository.save(task);
    }

    public List<TaskResponseDto> getTasksBy(String author, String assignee) {
        List<Task> tasks;
        if (author.isEmpty() && assignee.isEmpty()) {
            tasks = taskRepository.findAll(Sort.by(Sort.Direction.DESC, "created"));
        } else if (!author.isEmpty() && assignee.isEmpty()) {
            tasks = taskRepository.findByAuthorIgnoreCaseOrderByCreatedDesc(author);
        } else if (author.isEmpty()) {
            tasks = taskRepository.findByAssigneeIgnoreCaseOrderByCreatedDesc(assignee);
        } else {
            tasks = taskRepository.findByAuthorIgnoreCaseAndAssigneeIgnoreCaseOrderByCreatedDesc(author, assignee);
        }

        return tasks.stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    public Task assignTask(Long taskId, AssigneeRequestDto dto, UserDetails userDetails) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UsernameNotFoundException::new);
        String author = task.getAuthor();
        String assignee = dto.assignee();

        if (!user.getEmail().equals(author)) {
            throw new ForbiddenException();
        } else if (assignee.equals("none") || userRepository.existsByEmail(assignee)) {
            task.setAssignee(assignee);
            return taskRepository.save(task);
        } else throw new UsernameNotFoundException();
    }

    public Task updateTaskStatus(Long taskId, StatusRequestDto dto, UserDetails userDetails) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UsernameNotFoundException::new);
        String author = task.getAuthor();
        String assignee = task.getAssignee();

        if (!author.equalsIgnoreCase(user.getEmail()) && ("none".equalsIgnoreCase(assignee) || (!assignee.equalsIgnoreCase(user.getEmail())))) {
            throw new ForbiddenException();
        }

        task.setStatus(Status.valueOfString(dto.status()));
        return taskRepository.save(task);
    }

    public Comment createCommentOnTask(Long taskId, CommentRequestDto request, UserDetails userDetails) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        String author = userDetails.getUsername();

        Comment comment = new Comment();
        comment.setText(request.text());
        comment.setTaskId(task.getId());
        comment.setAuthor(author);

        Comment savedComment = commentRepository.save(comment);

        task.addComment(savedComment);
        taskRepository.save(task);

        return savedComment;
    }

    public List<Comment> getCommentsByTaskId(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        return commentRepository.findAllCommentsByTaskId(task.getId(), Sort.by(Sort.Direction.DESC, "created"));
    }
}
