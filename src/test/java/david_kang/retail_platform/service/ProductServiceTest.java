package david_kang.retail_platform.service;

import david_kang.retail_platform.dto.CreateProductRequest;
import david_kang.retail_platform.dto.UpdateProductRequest;
import david_kang.retail_platform.entity.Product;
import david_kang.retail_platform.exception.ProductNotFoundException;
import david_kang.retail_platform.exception.SkuAlreadyExistsException;
import david_kang.retail_platform.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldReturnProductById() {

        Product product = new Product();
        product.setId(1L);
        product.setSku("ABC123");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Product result = productService.getById(1L);

        assertEquals(1L, result.getId());

        verify(productRepository)
                .findById(1L);
    }

    @Test
    void shouldCreateProduct() {

        CreateProductRequest request = new CreateProductRequest();

        request.setSku("ABC123");
        request.setName("Laptop");
        request.setPrice(BigDecimal.valueOf(999.99));

        Product savedProduct = new Product();

        savedProduct.setId(1L);
        savedProduct.setSku("ABC123");
        savedProduct.setName("Laptop");
        savedProduct.setPrice(BigDecimal.valueOf(999.99));

        when(productRepository.findBySku("ABC123"))
                .thenReturn(Optional.empty());

        when(productRepository.save(any(Product.class)))
                .thenReturn(savedProduct);

        Product result = productService.create(request);

        assertEquals(1L, result.getId());
        assertEquals("ABC123", result.getSku());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenSkuAlreadyExists() {

        CreateProductRequest request = new CreateProductRequest();

        request.setSku("ABC123");

        Product existing = new Product();

        when(productRepository.findBySku("ABC123"))
                .thenReturn(Optional.of(existing));

        assertThrows(
                SkuAlreadyExistsException.class,
                () -> productService.create(request)
        );

        verify(productRepository, never())
                .save(any(Product.class));
    }

    @Test
    void shouldThrowWhenProductNotFoundById() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.getById(1L)
        );

        verify(productRepository)
                .findById(1L);
    }

    @Test
    void shouldUpdateProduct() {

        Product existing = new Product();

        existing.setId(1L);
        existing.setSku("ABC123");
        existing.setName("Old Name");
        existing.setPrice(BigDecimal.valueOf(100));

        UpdateProductRequest request =
                new UpdateProductRequest();

        request.setName("New Name");
        request.setPrice(BigDecimal.valueOf(200));

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(productRepository.save(existing))
                .thenReturn(existing);

        Product result =
                productService.update(1L, request);

        assertEquals(
                "New Name",
                result.getName());

        assertEquals(
                BigDecimal.valueOf(200),
                result.getPrice());

        verify(productRepository)
                .save(existing);
    }

    @Test
    void shouldDeleteProduct() {

        Product product = new Product();

        product.setId(1L);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        productService.delete(1L);

        verify(productRepository)
                .delete(product);
    }
}