package david_kang.retail_platform.exception;

public class InsufficientInventoryException
        extends RuntimeException {

    public InsufficientInventoryException(Long productId) {

        super("Insufficient inventory for product id: "
                + productId);
    }
}