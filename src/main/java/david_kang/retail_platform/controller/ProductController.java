package david_kang.retail_platform.controller;

import david_kang.retail_platform.dto.CreateProductRequest;
import david_kang.retail_platform.dto.ErrorResponse;
import david_kang.retail_platform.dto.UpdateProductRequest;
import david_kang.retail_platform.entity.Product;
import david_kang.retail_platform.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Products",
        description = "Product management endpoints"
)
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(
            ProductService productService) {
        this.productService = productService;
    }

    // POST /api/products
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)    @Operation(
            summary = "Add new product",
            description = "Adds a new product to the database."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "SKU already exists"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public Product createProduct(
            @Valid @RequestBody CreateProductRequest request) {

        return productService.create(request);
    }

    // GET /api/products
    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Returns information on all products."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Products retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public List<Product> getProducts() {
        return productService.getAll();
    }

    // GET /api/products/{id}
    @GetMapping("/{id}")
    @Operation(
            summary = "Get product by ID",
            description = "Returns information on a single product."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product retrieved successfully"
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
    public Product getProduct(
            @PathVariable Long id) {

        return productService.getById(id);
    }

    // GET /api/products/sku/{sku}
    @GetMapping("/sku/{sku}")
    @Operation(
            summary = "Get product by SKU",
            description = "Returns information on a single product."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product retrieved successfully"
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
    public Product getProductBySku(
            @PathVariable String sku) {

        return productService.getBySku(sku);
    }

    // PUT /api/products/{id}
    @PutMapping("/{id}")
    @Operation(
            summary = "Update product by ID",
            description = "Changes the name or price of a product."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
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
                    responseCode = "409",
                    description = "SKU already exists",
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
    public Product updateProduct(
            @PathVariable Long id,
            @Valid
            @RequestBody
            UpdateProductRequest request) {

        return productService.update(id, request);
    }

    // DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete product by ID",
            description = "Removes a product from the database."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Product deleted successfully"
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
    public void deleteProduct(
            @PathVariable Long id) {

        productService.delete(id);
    }

    // TODO: Create and use response DTOs instead of returning entities directly (eg. ProductResponse)
}