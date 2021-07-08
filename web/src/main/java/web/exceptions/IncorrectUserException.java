package web.exceptions;

public class IncorrectUserException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public IncorrectUserException(String message) {
        super(message);
    }
}
