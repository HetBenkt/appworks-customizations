package nl.bos;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
otds.login_url=https://pass_basic_user:pass_basic_password@{host}:{port}/otdsws/login...incl. the redirect info!
otds.username=...
otds.password=...
solution.creatable_option=Case
option.thread_sleep=2
option.headless=false
 */
public class PropertiesReader {

    private final Properties properties;

    public PropertiesReader(String fileName) {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + fileName);
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getStringPropertyValue(String key) {
        return properties.getProperty(key);
    }

    public int getIntPropertyValue(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public boolean getBooleanPropertyValue(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }
}