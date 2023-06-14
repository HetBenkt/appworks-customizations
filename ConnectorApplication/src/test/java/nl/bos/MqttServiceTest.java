package nl.bos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MqttServiceTest {

    private MqttService mqttService;

    @BeforeEach
    void setup() {
        mqttService = new MqttService(
                EConfiguration.INSTANCE.getValue("host"),
                Integer.parseInt(EConfiguration.INSTANCE.getValue("port"))
        );
    }

    @Test
    void connect() {
        assertThat(mqttService.connect(
                EConfiguration.INSTANCE.getValue("username"),
                EConfiguration.INSTANCE.getValue("password"))
        ).isTrue();
    }

    @Test
    void send() {
        assertThat(mqttService.send(
                "my/test/topic",
                "Hello world...")
        ).isTrue();
    }
}