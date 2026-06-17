package david_kang.retail_platform.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Standard API error response")
public class ErrorResponse {
    private LocalDateTime timestamp;

    @Schema(
            description = "HTTP status code",
            example = "404"
    )
    private int status;

    @Schema(
            description = "Error category",
            example = "Product Not Found"
    )
    private String error;

    @Schema(
            description = "Detailed error message",
            example = "Product with id 123 not found"
    )
    private String message;

    public ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message) {

        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
