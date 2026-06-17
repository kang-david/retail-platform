package david_kang.retail_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {


    @NotBlank
    @Schema(
            description = "Unique product identifier (SKU)",
            example = "ABC1DEF2"
    )
    public String sku;

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