package david_kang.retail_platform.service;

import david_kang.retail_platform.dto.AddInventoryRequest;
import david_kang.retail_platform.entity.Inventory;
import david_kang.retail_platform.entity.Product;
import david_kang.retail_platform.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import david_kang.retail_platform.repository.InventoryRepository;
import david_kang.retail_platform.repository.ProductRepository;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryService(
            InventoryRepository inventoryRepository,
            ProductRepository productRepository) {

        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public Inventory addInventory(
            AddInventoryRequest request) {

        // Find product.
        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                request.getProductId()));

        // Find inventory.
        Inventory inventory = inventoryRepository
                .findByProductId(product.getId())
                .orElse(null);

        // Check if record exists.
        if (inventory == null) {

            // NO -> create record.
            inventory = new Inventory();

            inventory.setProduct(product);
            inventory.setQuantity(request.getQuantity());

        } else {

            // YES -> add quantity.
            inventory.setQuantity(
                    inventory.getQuantity()
                            + request.getQuantity());
        }

        // Save.
        return inventoryRepository.save(inventory);
    }

    public Inventory getInventory(
            Long productId) {

        return inventoryRepository
                .findByProductId(productId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Inventory not found"));
    }

    public List<Inventory> getAllInventory() {

        return inventoryRepository.findAll();
    }
}