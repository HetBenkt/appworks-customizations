package nl.bos;

import com.eibus.soap.*;
import com.eibus.util.logger.CordysLogger;
import com.eibus.xml.nom.Node;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CustomConnector extends ApplicationConnector {
    private static final CordysLogger log = CordysLogger.getCordysLogger(CustomConnector.class);
    IMqttService mqttService;
    ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

    public CustomConnector() {
        log.info("In constructor");

        //TODO Input should be received from the service container configuration part
        String host = "";
        int port = 0;

        mqttService = new MqttService(host, port);
    }

    //This is invoked by the service container after establishing the connection to the AppWorks Platform ESB.
    @Override
    public void open(Processor processor) {
        log.info("Open connection");

        //TODO Input should be received from the service container configuration part
        int keepAlive = Integer.MAX_VALUE;
        String username = "";
        String password = "";

        mqttService.connect(username, password, keepAlive);
        exec.scheduleAtFixedRate(new MqttRunnable(mqttService), 0, (keepAlive * 1_000L) / 2, TimeUnit.MILLISECONDS);
        super.open(processor);
    }

    //This is invoked by the service container after closing the connection to the AppWorks Platform ESB.
    @Override
    public void close(Processor processor) {
        log.info("Close connection");
        mqttService.disconnect();
        exec.shutdown();
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
        return new CustomTransaction(mqttService);
    }

}
