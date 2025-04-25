package taskmanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TaskResponseDto(
        String id,
        String title,
        String description,
        String status,
        String author,
        String assignee,
        @JsonProperty(value = "total_comments")
        int totalComments
) {
}
