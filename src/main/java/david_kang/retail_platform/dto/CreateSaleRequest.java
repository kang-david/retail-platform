package david_kang.retail_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSaleRequest {

    @Schema(
            description = "Unique product identifier",
            example = "1"
    )
    private Long productId;

    @Schema(
            description = "Sales quantity",
            example = "3"
    )
    private Integer quantity;
}