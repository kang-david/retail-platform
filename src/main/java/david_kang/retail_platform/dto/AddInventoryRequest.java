package david_kang.retail_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddInventoryRequest {

    @NotNull(message = "Product ID is required")
    @Schema(
            description = "Unique product identifier",
            example = "1"
    )
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Schema(
            description = "Number of stock to add/subtract from inventory",
            example = "50"
    )
    private Integer quantity;
}