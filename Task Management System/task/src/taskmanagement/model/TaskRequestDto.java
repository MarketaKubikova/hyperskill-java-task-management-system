package taskmanagement.model;

import jakarta.validation.constraints.NotBlank;

public record TaskRequestDto(
        @NotBlank(message = "title should not be blank/null/empty")
        String title,
        @NotBlank(message = "description should not be blank/null/empty")
        String description
) {
}
