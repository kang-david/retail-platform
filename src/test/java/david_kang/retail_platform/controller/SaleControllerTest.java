package david_kang.retail_platform.controller;

import david_kang.retail_platform.dto.CreateSaleRequest;
import david_kang.retail_platform.dto.ProductSalesSummary;
import david_kang.retail_platform.entity.Product;
import david_kang.retail_platform.entity.Sale;
import david_kang.retail_platform.exception.InsufficientInventoryException;
import david_kang.retail_platform.exception.ProductNotFoundException;
import david_kang.retail_platform.service.SaleService;
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
class SaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SaleService saleService;

    @Test
    void shouldReturn201WhenSaleCreated()
            throws Exception {

        Product product = new Product();
        product.setId(1L);

        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setQuantity(5);

        CreateSaleRequest request =
                new CreateSaleRequest();

        request.setProductId(1L);
        request.setQuantity(5);

        when(saleService.createSale(any()))
                .thenReturn(sale);

        mockMvc.perform(
                        post("/api/sales")
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
                        status().isCreated()
                )
                .andExpect(
                        jsonPath("$.quantity")
                                .value(5)
                );
    }

    @Test
    void shouldReturn404WhenProductNotFound()
            throws Exception {

        CreateSaleRequest request =
                new CreateSaleRequest();

        request.setProductId(999L);
        request.setQuantity(5);

        when(saleService.createSale(any()))
                .thenThrow(
                        new ProductNotFoundException(
                                999L
                        )
                );

        mockMvc.perform(
                        post("/api/sales")
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
                );
    }

    @Test
    void shouldReturn409WhenInventoryInsufficient()
            throws Exception {

        CreateSaleRequest request =
                new CreateSaleRequest();

        request.setProductId(1L);
        request.setQuantity(999);

        when(saleService.createSale(any()))
                .thenThrow(
                        new InsufficientInventoryException(
                                1L
                        )
                );

        mockMvc.perform(
                        post("/api/sales")
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
                        status().isConflict()
                )
                .andExpect(
                        jsonPath("$.error")
                                .value(
                                        "Insufficient Inventory"
                                )
                );
    }

    @Test
    void shouldReturn400ForInvalidSaleRequest()
            throws Exception {

        String invalidJson =
                """
                {
                    "productId": null,
                    "quantity": -1
                }
                """;

        mockMvc.perform(
                        post("/api/sales")
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
    void shouldReturnAllSales()
            throws Exception {

        Sale sale1 = new Sale();
        sale1.setQuantity(2);

        Sale sale2 = new Sale();
        sale2.setQuantity(3);

        when(
                saleService.getAllSales()
        ).thenReturn(
                List.of(sale1, sale2)
        );

        mockMvc.perform(
                        get("/api/sales")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.length()")
                                .value(2)
                );
    }

    @Test
    void shouldReturnSalesForProduct()
            throws Exception {

        Sale sale1 = new Sale();
        sale1.setQuantity(2);

        Sale sale2 = new Sale();
        sale2.setQuantity(3);

        when(
                saleService.getSalesForProduct(1L)
        ).thenReturn(
                List.of(sale1, sale2)
        );

        mockMvc.perform(
                        get("/api/sales/product/1")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.length()")
                                .value(2)
                );
    }

    @Test
    void shouldReturnSalesSummary()
            throws Exception {

        ProductSalesSummary summary =
                new ProductSalesSummary(
                        1L,
                        15
                );

        when(
                saleService.getTotalSalesForProduct(
                        1L
                )
        ).thenReturn(summary);

        mockMvc.perform(
                        get("/api/sales/product/1/total")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.productId")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$.totalUnitsSold")
                                .value(15)
                );
    }
}
