package taskmanagement.model;

import jakarta.validation.constraints.Pattern;

public record StatusRequestDto(
        @Pattern(regexp = "(?i)created|in_progress|completed", message = "Invalid status")
        String status
) {
}
