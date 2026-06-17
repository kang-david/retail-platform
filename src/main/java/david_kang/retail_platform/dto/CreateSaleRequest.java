package david_kang.retail_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSaleRequest {

    private Long productId;

    private Integer quantity;
}