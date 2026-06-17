package david_kang.retail_platform.service;

import david_kang.retail_platform.dto.CreateSaleRequest;
import david_kang.retail_platform.dto.ProductSalesSummary;
import david_kang.retail_platform.entity.Inventory;
import david_kang.retail_platform.entity.Product;
import david_kang.retail_platform.entity.Sale;
import david_kang.retail_platform.exception.InsufficientInventoryException;
import david_kang.retail_platform.exception.ProductNotFoundException;
import david_kang.retail_platform.repository.InventoryRepository;
import david_kang.retail_platform.repository.ProductRepository;
import david_kang.retail_platform.repository.SaleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private SaleService saleService;

    @Test
    void shouldCreateSale() {

        Product product = new Product();
        product.setId(1L);

        Inventory inventory =
                new Inventory();

        inventory.setProduct(product);
        inventory.setQuantity(10);

        CreateSaleRequest request =
                new CreateSaleRequest();

        request.setProductId(1L);
        request.setQuantity(3);

        Sale savedSale = new Sale();

        savedSale.setProduct(product);
        savedSale.setQuantity(3);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(inventoryRepository.findByProductId(1L))
                .thenReturn(Optional.of(inventory));

        when(saleRepository.save(any(Sale.class)))
                .thenReturn(savedSale);

        Sale result =
                saleService.createSale(request);

        assertEquals(
                3,
                result.getQuantity());

        assertEquals(
                7,
                inventory.getQuantity());

        verify(inventoryRepository)
                .save(inventory);

        verify(saleRepository)
                .save(any(Sale.class));
    }

    @Test
    void shouldThrowWhenInventoryInsufficient() {

        Product product = new Product();
        product.setId(1L);

        Inventory inventory =
                new Inventory();

        inventory.setQuantity(2);

        CreateSaleRequest request =
                new CreateSaleRequest();

        request.setProductId(1L);
        request.setQuantity(5);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(inventoryRepository.findByProductId(1L))
                .thenReturn(Optional.of(inventory));

        assertThrows(
                InsufficientInventoryException.class,
                () -> saleService.createSale(request)
        );

        verify(saleRepository, never())
                .save(any());

        verify(inventoryRepository, never())
                .save(any());
    }

    @Test
    void shouldThrowWhenProductNotFound() {

        CreateSaleRequest request =
                new CreateSaleRequest();

        request.setProductId(1L);
        request.setQuantity(1);

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> saleService.createSale(request)
        );
    }

    @Test
    void shouldCalculateTotalSalesForProduct() {

        Sale sale1 = new Sale();
        sale1.setQuantity(5);

        Sale sale2 = new Sale();
        sale2.setQuantity(3);

        when(saleRepository.findByProductId(1L))
                .thenReturn(
                        List.of(
                                sale1,
                                sale2
                        ));

        ProductSalesSummary summary =
                saleService
                        .getTotalSalesForProduct(1L);

        assertEquals(
                8,
                summary.getTotalUnitsSold());
    }
}