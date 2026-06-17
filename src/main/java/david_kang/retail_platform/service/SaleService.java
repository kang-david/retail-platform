package david_kang.retail_platform.service;

import david_kang.retail_platform.dto.CreateSaleRequest;
import david_kang.retail_platform.dto.ProductSalesSummary;
import david_kang.retail_platform.entity.Inventory;
import david_kang.retail_platform.entity.Product;
import david_kang.retail_platform.entity.Sale;
import david_kang.retail_platform.exception.InsufficientInventoryException;
import david_kang.retail_platform.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import david_kang.retail_platform.repository.InventoryRepository;
import david_kang.retail_platform.repository.ProductRepository;
import david_kang.retail_platform.repository.SaleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public SaleService(
            SaleRepository salesRepository,
            InventoryRepository inventoryRepository,
            ProductRepository productRepository) {

        this.saleRepository = salesRepository;
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    // Create Sale.
    // If anything fails: Inventory not updated AND Sale not created
    @Transactional
    public Sale createSale(
            CreateSaleRequest request) {

        // Find product.
        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                request.getProductId()));

        // Find inventory.
        Inventory inventory = inventoryRepository
                .findByProductId(product.getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Inventory not found"));

        // Check stock.
        if (inventory.getQuantity()
                < request.getQuantity()) {

            throw new InsufficientInventoryException(
                    product.getId());
        }

        // Reduce stock.
        inventory.setQuantity(
                inventory.getQuantity()
                        - request.getQuantity());

        inventoryRepository.save(inventory);

        // Create sale record.
        Sale sale = new Sale();

        sale.setProduct(product);
        sale.setQuantity(request.getQuantity());
        sale.setCreatedAt(LocalDateTime.now());

        // Save.
        return saleRepository.save(sale);
    }

    // Get all sales.
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    // Get sales records for a specific product.
    public List<Sale> getSalesForProduct(
            Long productId) {

        return saleRepository
                .findByProductId(productId);
    }

    // Get sales summary for a specific product.
    public ProductSalesSummary
    getTotalSalesForProduct(
            Long productId) {

        List<Sale> sales =
                saleRepository
                        .findByProductId(productId);

        int totalUnitsSold = sales.stream()
                .mapToInt(Sale::getQuantity)
                .sum();

//        Logic for above:
//        total = 0; for each sale, total += sale.quantity

//        Example response body:
//        {
//            "productId": 1,
//                "totalUnitsSold": 37
//        }
        return new ProductSalesSummary(
                productId,
                totalUnitsSold);
    }
}

