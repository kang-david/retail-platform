package david_kang.retail_platform.service;

import david_kang.retail_platform.dto.AddInventoryRequest;
import david_kang.retail_platform.entity.Inventory;
import david_kang.retail_platform.entity.Product;
import david_kang.retail_platform.exception.ProductNotFoundException;
import david_kang.retail_platform.repository.InventoryRepository;
import david_kang.retail_platform.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void shouldCreateInventoryRecordWhenNoneExists() {

        Product product = new Product();
        product.setId(1L);

        AddInventoryRequest request =
                new AddInventoryRequest();

        request.setProductId(1L);
        request.setQuantity(10);

        Inventory savedInventory =
                new Inventory();

        savedInventory.setProduct(product);
        savedInventory.setQuantity(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(inventoryRepository.findByProductId(1L))
                .thenReturn(Optional.empty());

        when(inventoryRepository.save(any(Inventory.class)))
                .thenReturn(savedInventory);

        Inventory result =
                inventoryService.addInventory(request);

        assertEquals(10, result.getQuantity());

        verify(inventoryRepository)
                .save(any(Inventory.class));
    }

    @Test
    void shouldIncreaseInventoryWhenRecordExists() {

        Product product = new Product();
        product.setId(1L);

        Inventory inventory =
                new Inventory();

        inventory.setProduct(product);
        inventory.setQuantity(5);

        AddInventoryRequest request =
                new AddInventoryRequest();

        request.setProductId(1L);
        request.setQuantity(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(inventoryRepository.findByProductId(1L))
                .thenReturn(Optional.of(inventory));

        when(inventoryRepository.save(inventory))
                .thenReturn(inventory);

        Inventory result =
                inventoryService.addInventory(request);

        assertEquals(15, result.getQuantity());

        verify(inventoryRepository)
                .save(inventory);
    }

    @Test
    void shouldThrowWhenProductDoesNotExist() {

        AddInventoryRequest request =
                new AddInventoryRequest();

        request.setProductId(1L);

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> inventoryService.addInventory(request)
        );

        verify(inventoryRepository, never())
                .save(any());
    }

    @Test
    void shouldReturnInventory() {

        Inventory inventory =
                new Inventory();

        inventory.setQuantity(25);

        when(inventoryRepository.findByProductId(1L))
                .thenReturn(Optional.of(inventory));

        Inventory result =
                inventoryService.getInventory(1L);

        assertEquals(
                25,
                result.getQuantity());
    }
}