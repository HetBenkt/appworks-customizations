package nl.bos.config;

import nl.bos.exception.GeneralAppException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

class ConfigurationImplTest {

    @Test
    void getPropertiesIndirect() throws IOException {
        Configuration config = new ConfigurationImpl();
        Properties properties = config.getProperties();
        Assertions.assertThat(properties.getProperty("health_url")).isNotEmpty();
    }

    @Test
    void getPropertiesDirect() throws IOException {
        Configuration config = new ConfigurationImpl("config.properties");
        Properties properties = config.getProperties();
        Assertions.assertThat(properties.getProperty("health_url")).isNotEmpty();
    }

    @Test
    void getPropertiesEmptyFailure() {
        Assertions.assertThatExceptionOfType(GeneralAppException.class).isThrownBy(() -> {
            new ConfigurationImpl("");
        });
    }

    @Test
    void getPropertiesNotAvailableFailure() {
        Assertions.assertThatExceptionOfType(GeneralAppException.class).isThrownBy(() -> {
            new ConfigurationImpl("dummy.properties");
        });
    }
}