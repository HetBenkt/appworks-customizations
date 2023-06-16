package nl.bos;

public interface IMqttService {

    boolean connect(String username, String password);

    boolean disconnect();

    boolean send(String topic, String payload, boolean retain);

    boolean subscribe(String topic);
}
