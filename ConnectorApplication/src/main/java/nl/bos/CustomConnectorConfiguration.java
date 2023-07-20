package nl.bos;

import com.cordys.security.encryption.Password;
import com.eibus.soap.Services;
import com.opentext.platform.configuration.api.Component;
import com.opentext.platform.configuration.api.ComponentDefinition;
import com.opentext.platform.configuration.api.InstanceSettingComponentBase;

@ComponentDefinition(name = "nl.bos.CustomConnector", parent = Services.class)
public class CustomConnectorConfiguration extends InstanceSettingComponentBase {
    private final CustomConfiguration customConfiguration;

    public CustomConnectorConfiguration(Component parent, String name) {
        super(parent, name);
        this.customConfiguration = new CustomConfiguration(this);
    }

    public String getUserName() {
        return customConfiguration.getUserName();
    }

    public Password getPassword() {
        return customConfiguration.getPassword();
    }

    public String getHost() {
        return customConfiguration.getHost();
    }

    public int getPort() {
        return customConfiguration.getPort();
    }

    public int getKeepAlive() {
        return customConfiguration.getKeepAlive();
    }

    public void setConfigurationNode(int configurationNode) {
        customConfiguration.setConfigurationNode(configurationNode);
    }
}
