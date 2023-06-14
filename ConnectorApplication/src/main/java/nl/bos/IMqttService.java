package nl.bos;

public interface IMqttService {

    boolean connect(String username, String password);

    boolean send(String topic, String payload);
}
