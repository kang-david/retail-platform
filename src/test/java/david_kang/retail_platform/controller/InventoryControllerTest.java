package david_kang.retail_platform.controller;

import david_kang.retail_platform.dto.AddInventoryRequest;
import david_kang.retail_platform.entity.Inventory;
import david_kang.retail_platform.entity.Product;
import david_kang.retail_platform.exception.ProductNotFoundException;
import david_kang.retail_platform.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InventoryService inventoryService;

    @Test
    void shouldReturn200WhenInventoryAdded()
            throws Exception {

        Product product = new Product();
        product.setId(1L);

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity(10);

        AddInventoryRequest request =
                new AddInventoryRequest();

        request.setProductId(1L);
        request.setQuantity(10);

        when(inventoryService.addInventory(any()))
                .thenReturn(inventory);

        mockMvc.perform(
                        post("/api/inventory/add")
                                .contentType(
                                        MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper
                                                .writeValueAsString(
                                                        request
                                                )
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.quantity")
                                .value(10)
                );
    }

    @Test
    void shouldReturn404WhenProductNotFound()
            throws Exception {

        AddInventoryRequest request =
                new AddInventoryRequest();

        request.setProductId(999L);
        request.setQuantity(10);

        when(inventoryService.addInventory(any()))
                .thenThrow(
                        new ProductNotFoundException(
                                999L
                        )
                );

        mockMvc.perform(
                        post("/api/inventory/add")
                                .contentType(
                                        MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper
                                                .writeValueAsString(
                                                        request
                                                )
                                )
                )
                .andExpect(
                        status().isNotFound()
                )
                .andExpect(
                        jsonPath("$.status")
                                .value(404)
                )
                .andExpect(
                        jsonPath("$.error")
                                .value(
                                        "Product Not Found"
                                )
                );
    }

    @Test
    void shouldReturn400ForInvalidInventoryRequest()
            throws Exception {

        String invalidJson =
                """
                {
                    "productId": null,
                    "quantity": 10
                }
                """;

        mockMvc.perform(
                        post("/api/inventory/add")
                                .contentType(
                                        MediaType.APPLICATION_JSON)
                                .content(invalidJson)
                )
                .andExpect(
                        status().isBadRequest()
                )
                .andExpect(
                        jsonPath("$.error")
                                .value(
                                        "Validation Error"
                                )
                );
    }

    @Test
    void shouldReturnInventoryForProduct()
            throws Exception {

        Product product = new Product();
        product.setId(1L);

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity(25);

        when(
                inventoryService.getInventory(1L)
        ).thenReturn(inventory);

        mockMvc.perform(
                        get("/api/inventory/1")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.quantity")
                                .value(25)
                );
    }

    @Test
    void shouldReturnAllInventory()
            throws Exception {

        Inventory inv1 = new Inventory();
        inv1.setQuantity(10);

        Inventory inv2 = new Inventory();
        inv2.setQuantity(20);

        when(
                inventoryService.getAllInventory()
        ).thenReturn(
                List.of(inv1, inv2)
        );

        mockMvc.perform(
                        get("/api/inventory")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.length()")
                                .value(2)
                );
    }
}
