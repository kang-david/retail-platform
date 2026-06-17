package david_kang.retail_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductRequest {

    @NotBlank
    @Schema(
            description = "Product name",
            example = "Joe's Hammer"
    )
    private String name;

    @Positive
    @Schema(
            description = "Product price",
            example = "19.99"
    )
    private BigDecimal price;
}
