package david_kang.retail_platform.controller;

import david_kang.retail_platform.dto.CreateSaleRequest;
import david_kang.retail_platform.dto.ProductSalesSummary;
import david_kang.retail_platform.entity.Sale;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import david_kang.retail_platform.service.SaleService;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(
            SaleService saleService) {

        this.saleService = saleService;
    }

    // POST /api/sales
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sale createSale(
            @Valid
            @RequestBody
            CreateSaleRequest request) {

//         Example Request Body:
//         {
//             "productId": 1,
//                 "quantity": 3
//         }
        return saleService.createSale(
                request);
    }

    // GET /api/sales
    @GetMapping
    public List<Sale> getSales() {

        return saleService.getAllSales();
    }

    // GET /api/sales/product/{productId}
    @GetMapping("/product/{productId}")
    public List<Sale> getSalesForProduct(
            @PathVariable Long productId) {

        return saleService
                .getSalesForProduct(productId);
    }

    // GET /api/sales/product/{productId}/total
    @GetMapping("/product/{productId}/total")
    public ProductSalesSummary
    getTotalSalesForProduct(
            @PathVariable Long productId) {

        return saleService
                .getTotalSalesForProduct(
                        productId);
    }

    // TODO: Create and use response DTOs instead of returning entities directly (eg. SaleResponse)
}


