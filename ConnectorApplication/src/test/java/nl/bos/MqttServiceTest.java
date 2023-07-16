package nl.bos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Keep in mind:
 * As long as the connection is open, you can subscribe to a topic and publish to it (which will do the callback directly)
 * Once the connection is lost, the non-retained messages will not get a callback; Only the retained messages will!
 *    -> I saw also an automatic disconnection after a while; although my service container is still up and running (keepAlive option)!
 * Retained messages can be overridden with a new message.
 * So, when our service container starts, it makes a connection; We have 2 options:
 * 1. Publish a retained message and get a callback when you subscribe to the topic;
 *    -> If you first publish a non-retained message, you will NOT get a callback from your subscription!
 * 2. Subscribe to a topic and get a direct callback on a published message (this can be retained or non-retained!)
 */
class MqttServiceTest {

    private static final String TOPIC = "my/test/topic";
    private MqttService mqttService;
    ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

    @BeforeEach
    void setup() {
        mqttService = new MqttService(
                EConfiguration.INSTANCE.getValue("host"),
                Integer.parseInt(EConfiguration.INSTANCE.getValue("port"))
        );

        String username = EConfiguration.INSTANCE.getValue("username");
        String password = EConfiguration.INSTANCE.getValue("password");
        int keepAlive = Integer.parseInt(EConfiguration.INSTANCE.getValue("keepAlive"));

        assertThat(mqttService.connect(
                username,
                password,
                keepAlive)
        ).isTrue();

        exec.scheduleAtFixedRate(new MqttRunnable(mqttService), 0, (keepAlive * 1_000L) / 2, TimeUnit.MILLISECONDS);
    }

    @AfterEach
    void tearDown() {
        assertThat(mqttService.disconnect()).isTrue();
        exec.shutdown();
    }

    @Test
    void subscribe() {
        assertThat(mqttService.subscribe(
                TOPIC)
        ).isTrue();
    }

    @Test
    void publish() {
        assertThat(mqttService.publish(
                TOPIC,
                "Hello world...",
                false)
        ).isTrue();
    }

    @Test
    void sendRetained() {
        assertThat(mqttService.publish(
                TOPIC,
                "Hello world...RETAIN 1",
                true)
        ).isTrue();
    }

    @Test
    void deleteRetained() {
        assertThat(mqttService.publish(
                TOPIC,
                "",
                true)
        ).isTrue();
    }

    @Test
    void nonRetained() {
        assertThat(mqttService.publish(
                TOPIC,
                "Hello world...RETAIN 2",
                true)
        ).isTrue();
        assertThat(mqttService.subscribe(TOPIC)).isTrue();
        assertThat(mqttService.publish(
                TOPIC,
                "Hello world...NON-RETAIN",
                false)
        ).isTrue();
    }
}