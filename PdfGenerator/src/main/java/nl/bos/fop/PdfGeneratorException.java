package nl.bos.fop;

public class PdfGeneratorException extends RuntimeException {
    public PdfGeneratorException(String message, Exception e) {
        super(message, e);
    }
}
