package taskmanagement.model;

import jakarta.validation.constraints.Pattern;

public record AssigneeRequestDto(
        @Pattern(regexp = "(none)|^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email")
        String assignee
) {
}
