package david_kang.retail_platform.exception;

public class ProductNotFoundException
        extends RuntimeException {

    public ProductNotFoundException(String identifier) {

        super("Product not found: "
                + identifier);
    }

    public ProductNotFoundException(Long id) {

        this("id = " + id);
    }
}