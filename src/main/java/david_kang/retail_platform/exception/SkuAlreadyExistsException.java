package david_kang.retail_platform.exception;

public class SkuAlreadyExistsException
        extends RuntimeException {

    public SkuAlreadyExistsException(String sku) {

        super("SKU already exists: " + sku);
    }
}