package nl.bos;

import nl.bos.action.EnumActionTypes;
import nl.bos.validation.IValidator;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import javax.xml.bind.ValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class Configuration implements IValidator {
    private static final int CONFIG_PROPERTIES_SIZE = 21;
    private static Configuration instance;

    PropertiesConfiguration config;

    private boolean doDryRun;

    private boolean onlyMetadata;

    private EnumActionTypes mode;

    private Configuration() {
        doDryRun = false;
        onlyMetadata = false;
        mode = EnumActionTypes.EXPORT;
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public PropertiesConfiguration loadPropertyFile(String fileName) throws IOException, ConfigurationException {
        URL resource = getClass().getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new FileNotFoundException(String.format("Resource %s not found", fileName));
        }

        File propertiesFile = new File(resource.getFile());
        config = new PropertiesConfiguration(propertiesFile);
        config.setReloadingStrategy(new FileChangedReloadingStrategy());
        return config;
    }

    void cleanProperties() {
        config = null;
        doDryRun = false;
        onlyMetadata = false;
        mode = EnumActionTypes.EXPORT;
    }

    public String getValue(String propertyName) throws NoSuchFieldException {
        String property = config.getString(propertyName);
        if (property == null) {
            throw new NoSuchFieldException(String.format("Property %s not found!", propertyName));
        }
        return property;
    }

    boolean isDoDryRun() {
        return doDryRun;
    }

    public void setDoDryRun(boolean doDryRun) {
        this.doDryRun = doDryRun;
    }

    boolean isOnlyMetadata() {
        return onlyMetadata;
    }

    void setOnlyMetadata(boolean onlyMetadata) {
        this.onlyMetadata = onlyMetadata;
    }

    public EnumActionTypes getMode() {
        return mode;
    }

    public void setMode(EnumActionTypes mode) {
        this.mode = mode;
    }

    @Override
    public boolean isReady() throws ValidationException {
        if (instance == null) {
            return false;
        }

        //loadPropertyFile("config.properties"); is already done from the Storage initialization!
        correctNumberOfProperties();
        validPropertyNames();
        return true;
    }

    private void validPropertyNames() throws ValidationException {
        List<String> validPropertyNames = Arrays.asList("otds.auth_url", "otds.resource_id", "service.url", "soap.url", "rest.url", "health.url", "service.user_name", "service.password", "batch_size", "storage", "retries_on_failure", "log_level", "mapping_xml", "mail.server", "mail.port", "mail.user_name", "mail.password", "mail.to_address", "mail.from_address", "mail.template", "scaling_size");
        Iterator<String> propertyNames = config.getKeys();
        while (propertyNames.hasNext()) {
            String property = propertyNames.next();
            if (!validPropertyNames.contains(property)) {
                throw new ValidationException(String.format("Properties [%s] is missing!", property));
            }
        }
    }

    private void correctNumberOfProperties() throws ValidationException {
        Iterator<String> propertyNames = config.getKeys();
        int size = 0;
        while (propertyNames.hasNext()) {
            propertyNames.next();
            size++;
        }

        if (size != CONFIG_PROPERTIES_SIZE) {
            throw new ValidationException(String.format("Property size [%s] not valid, must be %s!", size, CONFIG_PROPERTIES_SIZE));
        }
    }
}
