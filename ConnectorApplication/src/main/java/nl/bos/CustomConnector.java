package nl.bos;

import com.cordys.security.encryption.Password;
import com.eibus.soap.*;
import com.eibus.util.logger.CordysLogger;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.XPath;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CustomConnector extends ApplicationConnector {
    private static final CordysLogger log = CordysLogger.getCordysLogger(CustomConnector.class);
    IMqttService mqttService;
    ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

    //This is invoked by the service container after establishing the connection to the AppWorks Platform ESB.
    @Override
    public void open(Processor processor) {
        log.info("Open connection");

        CustomConnectorConfiguration customConnectorConfig = processor.getApplicationConnectorConfiguration(CustomConnectorConfiguration.class);
        int configurationsNode = processor.getProcessorConfigurationNode();
        int configurationNode = XPath.getXPathInstance("./configuration").firstMatch(configurationsNode, null);
        if(log.isDebugEnabled()) {
            log.debug("SOAP Processor configuration node: " + Node.writeToString(configurationNode, true));
        }
        customConnectorConfig.setConfigurationNode(configurationNode);

        int keepAlive = customConnectorConfig.getKeepAlive();
        String username = customConnectorConfig.getUserName();
        Password password = customConnectorConfig.getPassword();
        String host = customConnectorConfig.getHost();
        int port = customConnectorConfig.getPort();

        mqttService = new MqttService(host, port);

        mqttService.connect(username, String.valueOf(password.getValue()), keepAlive);
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
