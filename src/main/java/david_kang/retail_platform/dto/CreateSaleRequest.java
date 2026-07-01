package david_kang.retail_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSaleRequest {

    @NotNull(message = "Product ID is required")
    @Schema(
            description = "Unique product identifier",
            example = "1"
    )
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    @Schema(
            description = "Sales quantity",
            example = "3"
    )
    private Integer quantity;
}