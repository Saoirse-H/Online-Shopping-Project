package seller.exceptions;

public class SellerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SellerNotFoundException(String message) {
        super(message);
    }
}
