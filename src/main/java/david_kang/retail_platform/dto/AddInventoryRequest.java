package david_kang.retail_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddInventoryRequest {

    private Long productId;

    private Integer quantity;
}