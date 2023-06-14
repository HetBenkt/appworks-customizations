package nl.bos;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MqttService implements IMqttService {

    private final Mqtt5BlockingClient client;

    public MqttService(String host, int port) {
        client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(host)
                .serverPort(port)
                .sslWithDefaultConfig()
                .buildBlocking();
    }

    @Override
    public boolean connect(String username, String password) {
        client.connectWith()
                .simpleAuth()
                .username(username)
                .password(UTF_8.encode(password))
                .applySimpleAuth()
                .send();
        return true;
    }

    @Override
    public boolean send(String topic, String payload) {
        client.publishWith()
                .topic(topic)
                .payload(UTF_8.encode(payload))
                .send();
        return true;
    }
}
