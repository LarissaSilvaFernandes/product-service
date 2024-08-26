package lavin.product_service.exceptions;

public class ExceptionProductNotFound extends RuntimeException {
    public ExceptionProductNotFound() {
        super("Product not found");
    }
}
