package david_kang.retail_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddInventoryRequest {

    @Schema(
            description = "Unique product identifier",
            example = "1"
    )
    private Long productId;

    @Schema(
            description = "Number of stock to add/subtract from inventory",
            example = "50"
    )
    private Integer quantity;
}