package nl.bos;

public class PdfGeneratorException extends RuntimeException {
    public PdfGeneratorException(String message, Exception e) {
        super(message, e);
    }
}
