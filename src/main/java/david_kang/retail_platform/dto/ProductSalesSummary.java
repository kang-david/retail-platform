package david_kang.retail_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSalesSummary {

    private Long productId;

    private Integer totalUnitsSold;

    public ProductSalesSummary(
            Long productId,
            Integer totalUnitsSold) {

        this.productId = productId;
        this.totalUnitsSold = totalUnitsSold;
    }

    // getters
}