package david_kang.retail_platform.controller;

import david_kang.retail_platform.dto.CreateSaleRequest;
import david_kang.retail_platform.dto.ErrorResponse;
import david_kang.retail_platform.dto.ProductSalesSummary;
import david_kang.retail_platform.entity.Sale;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import david_kang.retail_platform.service.SaleService;

import java.util.List;

@Tag(
        name = "Sales",
        description = "Sales management endpoints"
)
@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(
            SaleService saleService) {

        this.saleService = saleService;
    }

    // POST /api/sales
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Record Sale",
            description = "Records a single sale in the database."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Sale recorded successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Insufficient inventory"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public Sale createSale(
            @Valid
            @RequestBody
            CreateSaleRequest request) {

        return saleService.createSale(
                request);
    }

    // GET /api/sales
    @GetMapping
    @Operation(
            summary = "Get all sales",
            description = "Returns all sales records."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sales retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    public List<Sale> getSales() {

        return saleService.getAllSales();
    }

    // GET /api/sales/product/{productId}
    @GetMapping("/product/{productId}")
    @Operation(
            summary = "Get sales by product ID",
            description = "Returns sales records of a single product."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sales retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    public List<Sale> getSalesForProduct(
            @PathVariable Long productId) {

        return saleService
                .getSalesForProduct(productId);
    }

    // GET /api/sales/product/{productId}/total
    @GetMapping("/product/{productId}/total")
    @Operation(
            summary = "Get sales summary by product ID",
            description = "Returns the sum of all sales records of a single product"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sales summary retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    public ProductSalesSummary
    getTotalSalesForProduct(
            @PathVariable Long productId) {

        return saleService
                .getTotalSalesForProduct(
                        productId);
    }

    // TODO: Create and use response DTOs instead of returning entities directly (eg. SaleResponse)
}


