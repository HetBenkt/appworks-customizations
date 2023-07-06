package nl.bos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Keep in mind:
 * As long as the connection is open, you can subscribe to a topic and publish to it (which will do the callback directly)
 * Once the connection is lost, the non-retained messages will not get a callback; Only the retained messages will!
 * Retained messages can be overridden with a new message.
 * So, when our service container starts, it makes a connection; We have 2 options:
 * 1. Publish a retained message and get a callback when you subscribe to the topic;
 * If you first publish a non-retained message, you will NOT get a callback from your subscription!
 * 2. Subscribe to a topic and get a direct callback on a published message (this can be retained or non-retained!)
 */
class MqttServiceTest {

    private static final String TOPIC = "my/test/topic";
    private MqttService mqttService;

    @BeforeEach
    void setup() {
        mqttService = new MqttService(
                EConfiguration.INSTANCE.getValue("host"),
                Integer.parseInt(EConfiguration.INSTANCE.getValue("port"))
        );

        assertThat(mqttService.connect(
                EConfiguration.INSTANCE.getValue("username"),
                EConfiguration.INSTANCE.getValue("password"))
        ).isTrue();
    }

    @AfterEach
    void tearDown() {
        assertThat(mqttService.disconnect()).isTrue();
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