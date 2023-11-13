package nl.bos;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
otds.login_url=https://pass_basic_user:pass_basic_password@{host}:{port}/otdsws/login...incl. the redirect info!
otds.username=...
otds.password=...
 */
public class PropertiesReader {

    private final Properties properties;

    public PropertiesReader(String fileName) {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + fileName);
            }

            // load a properties file from class path, inside static method
            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getPropertyValue(String key) {
        return properties.getProperty(key);
    }
}