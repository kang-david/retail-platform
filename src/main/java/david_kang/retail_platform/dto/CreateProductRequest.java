package david_kang.retail_platform.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {

    @NotBlank
    public String sku;

    @NotBlank
    private String name;

    @Positive
    private BigDecimal price;
}