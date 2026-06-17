package david_kang.retail_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSalesSummary {

    @Schema(
            description = "Unique product identifier",
            example = "1"
    )
    private Long productId;

    @Schema(
            description = "Total units sold",
            example = "132"
    )
    private Integer totalUnitsSold;

    public ProductSalesSummary(
            Long productId,
            Integer totalUnitsSold) {

        this.productId = productId;
        this.totalUnitsSold = totalUnitsSold;
    }

    // getters
}