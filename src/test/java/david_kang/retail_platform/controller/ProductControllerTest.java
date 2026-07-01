package david_kang.retail_platform.controller;

import david_kang.retail_platform.dto.CreateProductRequest;
import david_kang.retail_platform.entity.Product;
import david_kang.retail_platform.exception.ProductNotFoundException;
import david_kang.retail_platform.exception.SkuAlreadyExistsException;
import david_kang.retail_platform.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    void shouldReturnProductById() throws Exception {

        Product product = new Product();

        product.setId(1L);
        product.setSku("ABC123");
        product.setName("Laptop");

        when(productService.getById(1L))
                .thenReturn(product);

        mockMvc.perform(
                        get("/api/products/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.sku").value("ABC123"))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

//     Test Failed

    @Test
    void shouldReturn404WhenProductNotFound() throws Exception {

        when(productService.getById(999L))
                .thenThrow(
                        new ProductNotFoundException(999L)
                );

        mockMvc.perform(
                        get("/api/products/999")
                )
                .andExpect(status().isNotFound())
                .andExpect(
                        jsonPath("$.status")
                                .value(404)
                )
                .andExpect(
                        jsonPath("$.error")
                                .value("Product Not Found")
                );
    }

    @Test
    void shouldCreateProduct() throws Exception {

        CreateProductRequest request =
                new CreateProductRequest();

        request.setSku("ABC123");
        request.setName("Laptop");

        Product product = new Product();

        product.setId(1L);
        product.setSku("ABC123");
        product.setName("Laptop");

        when(productService.create(any()))
                .thenReturn(product);

        mockMvc.perform(
                        post("/api/products")
                                .contentType(
                                        MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper
                                                .writeValueAsString(
                                                        request
                                                )
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(
                        jsonPath("$.id")
                                .value(1)
                );
    }

    @Test
    void shouldReturn409WhenSkuExists()
            throws Exception {

        CreateProductRequest request =
                new CreateProductRequest();

        request.setSku("ABC123");
        request.setName("Laptop");
        request.setPrice(
                BigDecimal.valueOf(999.99));

        when(productService.create(any()))
                .thenThrow(
                        new SkuAlreadyExistsException(
                                "ABC123"
                        )
                );

        mockMvc.perform(
                        post("/api/products")
                                .contentType(
                                        MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper
                                                .writeValueAsString(
                                                        request
                                                )
                                )
                )
                .andExpect(status().isConflict())
                .andExpect(
                        jsonPath("$.status")
                                .value(409)
                )
                .andExpect(
                        jsonPath("$.error")
                                .value("Duplicate SKU")
                );
    }

    @Test
    void shouldDeleteProduct()
            throws Exception {

        mockMvc.perform(
                        delete("/api/products/1")
                )
                .andExpect(
                        status().isNoContent()
                );

        verify(productService)
                .delete(1L);
    }

    @Test
    void shouldReturn400ForInvalidRequest()
            throws Exception {

        String invalidJson =
                """
                {
                  "sku": ""
                }
                """;

        mockMvc.perform(
                        post("/api/products")
                                .contentType(
                                        MediaType.APPLICATION_JSON)
                                .content(invalidJson)
                )
                .andExpect(
                        status().isBadRequest()
                )
                .andExpect(
                        jsonPath("$.error")
                                .value("Validation Error")
                );
    }
}