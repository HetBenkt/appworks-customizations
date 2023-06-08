package nl.bos.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneralAppException extends RuntimeException {

    private static final Logger logger = Logger.getLogger(GeneralAppException.class.getName());

    public GeneralAppException(Exception e) {
        logger.log(Level.SEVERE, e.getMessage(), e);
    }

    public GeneralAppException(String message) {
        logger.log(Level.SEVERE, message);
    }
}
