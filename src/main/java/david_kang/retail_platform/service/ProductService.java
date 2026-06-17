package david_kang.retail_platform.service;

import david_kang.retail_platform.dto.CreateProductRequest;
import david_kang.retail_platform.dto.UpdateProductRequest;
import david_kang.retail_platform.entity.Product;
import david_kang.retail_platform.exception.ProductNotFoundException;
import david_kang.retail_platform.exception.SkuAlreadyExistsException;
import org.springframework.stereotype.Service;
import david_kang.retail_platform.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(
            ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Helper method for error handling.
    private Product findProduct(Long id) {

        return productRepository
                .findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(id));
    }

    // Create product.
    public Product create(
            CreateProductRequest request) {

        if (productRepository
                .findBySku(request.getSku())
                .isPresent()) {

            throw new SkuAlreadyExistsException(
                    request.getSku());
        }

        Product product = new Product();

        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return productRepository.save(product);
    }

    // Get all products.
    public List<Product> getAll() {

        return productRepository.findAll();
    }

    // Get product by ID.
    public Product getById(Long id) {

        return findProduct(id);
    }

    public Product getBySku(String sku) {

        return productRepository
                .findBySku(sku)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "SKU: " + sku));
    }

    // Update product.
    public Product update(
            Long id,
            UpdateProductRequest request) {

        Product product = findProduct(id);

        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return productRepository.save(product);
    }

    // Delete product.
    public void delete(Long id) {

        Product product = findProduct(id);

        productRepository.delete(product);
    }
}