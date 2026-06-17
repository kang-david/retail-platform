package david_kang.retail_platform.controller;

import david_kang.retail_platform.dto.AddInventoryRequest;
import david_kang.retail_platform.dto.ErrorResponse;
import david_kang.retail_platform.entity.Inventory;
import david_kang.retail_platform.service.InventoryService;

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
        name = "Inventory",
        description = "Inventory management endpoints"
)
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(
            InventoryService inventoryService) {

        this.inventoryService = inventoryService;
    }

    // POST /api/inventory/add
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Add to inventory",
            description = "Adds stock of a single product to the inventory. Can also be used to subtract stock."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Inventory updated successfully"
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
                    description = "Inventory operation would result in negative stock",
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
    public Inventory addInventory(
            @Valid @RequestBody AddInventoryRequest request) {

        return inventoryService.addInventory(request);
    }

    // GET /api/inventory/{productId}
    @GetMapping("/{productId}")
    @Operation(
            summary = "Get Inventory by ID",
            description = "Returns the inventory of a single product."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Inventory retrieved successfully"
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
    public Inventory getInventory(
            @PathVariable Long productId) {

        return inventoryService.getInventory(productId);
    }

    // GET /api/inventory
    @GetMapping
    @Operation(
            summary = "Get All Inventory",
            description = "Returns the inventory of all products."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Inventory retrieved successfully"
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
    public List<Inventory> getAllInventory() {

        return inventoryService.getAllInventory();
    }

    // Potential circular reference error during serialization.
    // Can be fixed with response DTO (ideal).
    // Can also be fixed with @JsonIgnore.

    // TODO: Create and use response DTOs instead of returning entities directly (eg. InventoryResponse)
}