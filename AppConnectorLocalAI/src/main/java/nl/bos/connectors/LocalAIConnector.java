package nl.bos.connectors;

import com.eibus.management.IManagedComponent;
import com.eibus.soap.ApplicationConnector;
import com.eibus.soap.ApplicationTransaction;
import com.eibus.soap.Processor;
import com.eibus.soap.SOAPTransaction;
import com.eibus.util.logger.CordysLogger;

public class LocalAIConnector extends ApplicationConnector {

    private final CordysLogger log = CordysLogger.getCordysLogger(this.getClass());

    @Override
    public ApplicationTransaction createTransaction(SOAPTransaction transaction) {
        if(log.isDebugEnabled()) {
            log.debug(String.format("createTransaction method %s", transaction.getProxyUser()));
        }
        return new LocalAITransaction(getProcessor(), transaction);
    }

    @Override
    public void open(Processor processor) {
        if(log.isDebugEnabled()) {
            log.debug(String.format("open method %s, %s", processor.getConnector().isOpen(), processor.isReady()));
        }
    }

    @Override
    public void close(Processor processor) {
        if(log.isDebugEnabled()) {
            log.debug(String.format("close method %s", processor.getProcessorStatus()));
        }
    }

    @Override
    public void reset(Processor processor) {
        if(log.isDebugEnabled()) {
            log.debug(String.format("reset method %s", processor.isReady()));
        }
    }

}
