package nl.bos;

import com.eibus.util.logger.CordysLogger;

import java.util.Date;

public class MqttRunnable implements Runnable {
    private static final CordysLogger log = CordysLogger.getCordysLogger(CustomConnector.class);
    private final IMqttService service;

    public MqttRunnable(IMqttService service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.ping();
        if(log.isDebugEnabled()) {
            log.debug(String.format("Task performed on: %tc with thread's name: %s", new Date(), Thread.currentThread().getName()));
        }
    }
}
