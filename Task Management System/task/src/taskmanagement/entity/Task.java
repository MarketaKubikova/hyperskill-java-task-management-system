package taskmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import taskmanagement.model.Status;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String author;
    private String assignee;

    @OneToMany(mappedBy = "taskId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    public Task() {}

    public Task(String title, String description, User author) {
        this.title = title;
        this.description = description;
        this.status = Status.CREATED;
        this.created = LocalDateTime.now();
        this.user = author;
        this.user.addTask(this);
        this.author = user.getEmail();
        this.assignee = "none";
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public User getUser() {
        return user;
    }

    public String getAuthor() {
        return author;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
