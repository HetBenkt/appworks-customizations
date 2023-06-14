package nl.bos;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

enum EConfiguration {
    INSTANCE;

    private final Properties props;

    EConfiguration() {
        props = new Properties();

        try (InputStream inputStream = EConfiguration.class.getClassLoader().getResourceAsStream("config.properties")) {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key) {
        return props.getProperty(key);
    }
}
