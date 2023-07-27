package nl.bos;

import com.eibus.util.logger.CordysLogger;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MqttService implements IMqttService {
    private static final CordysLogger log = CordysLogger.getCordysLogger(CustomTransaction.class);
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
    public boolean connect(String username, String password, int keepAlive) {
        client.connectWith()
                .simpleAuth()
                .username(username)
                .password(UTF_8.encode(password))
                .applySimpleAuth()
                .keepAlive(keepAlive)
                .send();
        return true;
    }

    @Override
    public boolean disconnect() {
        if(!client.getState().isConnected()) {
            return false;
        } else {
            client.disconnect();
            return true;
        }
    }

    @Override
    public boolean ping() {
        if(!client.getState().isConnected()) {
            return false;
        } else {
            client.publishWith()
                    .topic("PINGREQ")
                    .retain(false)
                    .send();
            return true;
        }
    }

    @Override
    public boolean publish(String topic, String payload, boolean retain) {
        if(!client.getState().isConnected()) {
            return false;
        } else {
            client.publishWith()
                    .topic(topic)
                    .retain(retain)
                    .payload(UTF_8.encode(payload))
                    .send();
            return true;
        }
    }

    @Override
    public boolean subscribe(String topic) {
        if(!client.getState().isConnected()) {
            return false;
        } else {
            client.toAsync()
                    .subscribeWith()
                    .topicFilter(topic)
                    .callback(publish -> {
                        String payload = new String(publish.getPayloadAsBytes(), UTF_8);
                        if (log.isDebugEnabled()) {
                            log.debug(payload);
                        }
                    })
                    .send();
            return true;
        }
    }

    @Override
    public boolean unsubscribe(String topic) throws ExecutionException, InterruptedException {
        if(!client.getState().isConnected()) {
            return false;
        } else {
            CompletableFuture<Mqtt5UnsubAck> send = client.toAsync()
                    .unsubscribeWith()
                    .topicFilter(topic)
                    .send();
            return send.get().getType() == Mqtt5MessageType.UNSUBACK;
        }
    }
}
