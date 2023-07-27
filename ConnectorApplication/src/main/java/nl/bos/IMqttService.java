package nl.bos;

import java.util.concurrent.ExecutionException;

public interface IMqttService {

    boolean connect(String username, String password, int keepAlive);

    boolean disconnect();

    boolean ping();

    boolean publish(String topic, String payload, boolean retain);

    boolean subscribe(String topic);

    boolean unsubscribe(String topic) throws ExecutionException, InterruptedException;
}
