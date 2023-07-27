package nl.bos;

public class CustomException extends RuntimeException {
    public CustomException(String message, Exception exception) {
        super(message, exception);
    }
}
