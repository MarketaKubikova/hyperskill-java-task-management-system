package taskmanagement.model;

import jakarta.validation.constraints.NotBlank;

public record CommentRequestDto(
        @NotBlank
        String text
) {
}
