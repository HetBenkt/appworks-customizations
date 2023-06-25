package nl.bos;

public interface IMqttService {

    boolean connect(String username, String password);

    boolean disconnect();

    boolean publish(String topic, String payload, boolean retain);

    boolean subscribe(String topic);
}
