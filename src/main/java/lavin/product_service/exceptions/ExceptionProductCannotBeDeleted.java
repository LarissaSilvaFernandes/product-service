package lavin.product_service.exceptions;

public class ExceptionProductCannotBeDeleted extends RuntimeException {
    public ExceptionProductCannotBeDeleted() {
        super("Product cannot be deleted");
    }
}
