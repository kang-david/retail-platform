package david_kang.retail_platform.controller;

import david_kang.retail_platform.dto.CreateProductRequest;
import david_kang.retail_platform.dto.UpdateProductRequest;
import david_kang.retail_platform.entity.Product;
import david_kang.retail_platform.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(
            ProductService productService) {
        this.productService = productService;
    }

    // POST /api/products
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(
            @Valid @RequestBody CreateProductRequest request) {

        return productService.create(request);
    }

    // GET /api/products
    @GetMapping
    public List<Product> getProducts() {
        return productService.getAll();
    }

    // GET /api/products/{id}
    @GetMapping("/{id}")
    public Product getProduct(
            @PathVariable Long id) {

        return productService.getById(id);
    }

    // GET /api/products/sku/{sku}
    @GetMapping("/sku/{sku}")
    public Product getProductBySku(
            @PathVariable String sku) {

        return productService.getBySku(sku);
    }

    // PUT /api/products/{id}
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @Valid
            @RequestBody
            UpdateProductRequest request) {

//         Example Request Body:
//         {
//             "name": "20oz Hammer",
//             "price": 17.99
//         }
        return productService.update(id, request);
    }

    // DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(
            @PathVariable Long id) {

        productService.delete(id);
    }

    // TODO: Create and use response DTOs instead of returning entities directly (eg. ProductResponse)
}