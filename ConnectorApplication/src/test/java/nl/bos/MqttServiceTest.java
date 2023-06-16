package nl.bos;

import org.junit.jupiter.api.AfterEach;
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
        connect();
    }

    @AfterEach
    void breakdown() {
        disconnect();
    }

    @Test
    void connect() {
        assertThat(mqttService.connect(
                EConfiguration.INSTANCE.getValue("username"),
                EConfiguration.INSTANCE.getValue("password"))
        ).isTrue();
    }

    @Test
    void disconnect() {
        assertThat(mqttService.disconnect()).isTrue();
    }

    @Test
    void subscribe() {
        assertThat(mqttService.subscribe(
                "my/test/topic")
        ).isTrue();
        send();
    }

    @Test
    void send() {
        assertThat(mqttService.send(
                "my/test/topic",
                "Hello world...",
                false)
        ).isTrue();
    }

    @Test
    void sendRetained() {
        assertThat(mqttService.send(
                "my/test/topic",
                "Hello world...RETAIN",
                true)
        ).isTrue();
    }

    @Test
    void deleteRetained() {
        assertThat(mqttService.send(
                "my/test/topic",
                "",
                true)
        ).isTrue();
    }
}