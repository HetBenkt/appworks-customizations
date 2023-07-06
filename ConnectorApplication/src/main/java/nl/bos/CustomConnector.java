package nl.bos;

import com.eibus.soap.*;
import com.eibus.util.logger.CordysLogger;
import com.eibus.xml.nom.Node;

public class CustomConnector extends ApplicationConnector {
    private static CordysLogger log = CordysLogger.getCordysLogger(CustomConnector.class);
    IMqttService service;
    public CustomConnector() {
        log.info("In constructor");
        service = new MqttService("", 0); //TODO Input should be received from the service container configuration part
    }

    //This is invoked by the service container after establishing the connection to the AppWorks Platform ESB.
    @Override
    public void open(Processor processor) {
        log.info("Open connection");
        service.connect("", ""); //TODO Input should be received from the service container configuration part
        super.open(processor);
    }

    //This is invoked by the service container after closing the connection to the AppWorks Platform ESB.
    @Override
    public void close(Processor processor) {
        log.info("Close connection");
        service.disconnect();
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
        if(log.isDebugEnabled()) {
            log.debug(Node.writeToString(Node.getElement(soapTransaction.getRequestEnvelope(), "SOAP:Body"), true));
        }
        return new CustomTransaction(service);
    }

}
