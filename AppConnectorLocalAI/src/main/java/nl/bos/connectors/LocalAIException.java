package nl.bos.connectors;

import com.eibus.util.logger.CordysLogger;

public class LocalAIException extends RuntimeException {

    private final CordysLogger log = CordysLogger.getCordysLogger(this.getClass());

    public LocalAIException(Exception e) {
        log.info(e.getMessage());
    }
}
