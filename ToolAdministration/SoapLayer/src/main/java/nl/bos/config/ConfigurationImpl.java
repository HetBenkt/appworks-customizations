package nl.bos.config;

import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.exception.GeneralAppException;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigurationImpl implements Configuration {
    private final Properties properties = new Properties();

    public ConfigurationImpl(String fileName) throws IOException {
        if (fileName == null || fileName.isBlank() || fileName.isEmpty()) {
            throw new GeneralAppException("Config file is not valid");
        }

        InputStream inStream = AppWorksPlatformImpl.class.getClassLoader().getResourceAsStream(fileName);
        if (inStream == null) {
            throw new GeneralAppException("Config file not found");
        }

        properties.load(inStream);
        String message = MessageFormat.format("Config file ''{0}'' loaded...", fileName);
        Logger.getLogger(Configuration.class.getName()).info(message);
    }

    public ConfigurationImpl() throws IOException {
        this("config.properties");
    }

    @Override
    public Properties getProperties() {
        return properties;
    }
}
