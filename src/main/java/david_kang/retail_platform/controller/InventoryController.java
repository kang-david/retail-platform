package david_kang.retail_platform.controller;

import david_kang.retail_platform.dto.AddInventoryRequest;
import david_kang.retail_platform.entity.Inventory;
import david_kang.retail_platform.service.InventoryService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Inventory addInventory(
            @Valid @RequestBody AddInventoryRequest request) {

//         Example Request Body:
//         {
//             "productId": 1,
//                 "quantity": 50
//         }
        return inventoryService.addInventory(request);
    }

    // GET /api/inventory/{productId}
    @GetMapping("/{productId}")
    public Inventory getInventory(
            @PathVariable Long productId) {

        return inventoryService.getInventory(productId);
    }

    // GET /api/inventory
    @GetMapping
    public List<Inventory> getAllInventory() {

        return inventoryService.getAllInventory();
    }

    // Potential circular reference error during serialization.
    // Can be fixed with response DTO (ideal).
    // Can also be fixed with @JsonIgnore.

    // TODO: Create and use response DTOs instead of returning entities directly (eg. InventoryResponse)
}