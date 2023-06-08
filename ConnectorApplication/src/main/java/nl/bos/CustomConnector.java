package nl.bos;

import com.eibus.soap.*;

public class CustomConnector extends ApplicationConnector {
    //This is invoked by the service container after establishing the connection to the AppWorks Platform ESB.
    @Override
    public void open(Processor processor) {
        super.open(processor);
    }

    //This is invoked by the service container after closing the connection to the AppWorks Platform ESB.
    @Override
    public void close(Processor processor) {
        super.close(processor);
    }

    //This is invoked by the service container to clear the cache.
    @Override
    public void reset(Processor processor) {
        super.reset(processor);
    }

    //This is invoked by the service container to indicate the arrival of a new message and creation of a SOAPTransaction for it.
    @Override
    public ApplicationTransaction createTransaction(SOAPTransaction soapTransaction) {
        return super.createTransaction(soapTransaction);
    }
}
