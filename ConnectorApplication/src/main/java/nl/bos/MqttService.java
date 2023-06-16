package nl.bos;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MqttService implements IMqttService {
    Logger logger = Logger.getLogger(MqttService.class.getName());
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
    public boolean disconnect() {
        client.disconnect();
        return true;
    }

    @Override
    public boolean send(String topic, String payload, boolean retain) {
        client.publishWith()
                .topic(topic)
                .retain(retain)
                .payload(UTF_8.encode(payload))
                .send();
        return true;
    }

    @Override
    public boolean subscribe(String topic) {
        client.toAsync()
                .subscribeWith()
                .topicFilter(topic)
                .callback(publish -> {
                    String payload = new String(publish.getPayloadAsBytes(), UTF_8);
                    logger.log(Level.ALL, payload);
                    System.out.println(payload);
                })
                .send();
        return true;
    }
}
