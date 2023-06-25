package nl.bos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
                "Hello world...RETAIN",
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
}