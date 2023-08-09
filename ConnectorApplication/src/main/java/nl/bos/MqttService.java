package nl.bos;

import com.eibus.util.logger.CordysLogger;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MqttService implements IMqttService {
    private static final CordysLogger log = CordysLogger.getCordysLogger(CustomTransaction.class);
    public static final String PING_REQUEST = "PINGREQ";
    private final Mqtt5BlockingClient client;

    private String payload = "";
    private boolean callBackDone = false;

    public MqttService(final String host, final int port) {
        client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(host)
                .serverPort(port)
                .sslWithDefaultConfig()
                .buildBlocking();
    }

    @Override
    public boolean connect(final String username, final String password, final int keepAlive) {
        Mqtt5ConnAck response = client.connectWith()
                .simpleAuth()
                .username(username)
                .password(UTF_8.encode(password))
                .applySimpleAuth()
                .keepAlive(keepAlive)
                .send();
        return response.isSessionPresent();
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
                    .topic(PING_REQUEST)
                    .retain(false)
                    .send();
            return true;
        }
    }

    @Override
    public boolean publish(final String topic, final String payload, final boolean retain) {
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
    public boolean subscribe(final String topic) {
        if(!client.getState().isConnected()) {
            return false;
        } else {
            client.toAsync()
                    .subscribeWith()
                    .topicFilter(topic)
                    .callback(publish -> {
                        payload = new String(publish.getPayloadAsBytes(), UTF_8);
                        callBackDone = true;
                        if (log.isDebugEnabled()) {
                            log.debug(payload);
                        }
                    })
                    .send();
            return true;
        }
    }

    @Override
    public String getPayload() {
        return payload;
    }

    @Override
    public boolean getCallBackDone() {
        return callBackDone;
    }

    @Override
    public void resetCallBack() {
        this.payload = "";
        callBackDone = false;
    }

    @Override
    public boolean unsubscribe(final String topic) throws ExecutionException, InterruptedException {
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
