package nl.bos;

import com.cordys.security.encryption.Password;
import com.eibus.soap.Services;
import com.opentext.platform.configuration.api.Component;
import com.opentext.platform.configuration.api.ComponentDefinition;
import com.opentext.platform.configuration.api.InstanceSettingComponentBase;

import java.util.Objects;

@ComponentDefinition(name = "nl.bos.CustomConnector", parent = Services.class)
public class CustomConnectorConfiguration extends InstanceSettingComponentBase {
    private final CustomConfiguration customConfiguration;

    public CustomConnectorConfiguration(final Component parent, final String name) {
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

    public void setConfigurationNode(final int configurationNode) {
        customConfiguration.setConfigurationNode(configurationNode);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }

        return Objects.equals(customConfiguration, ((CustomConnectorConfiguration) other).customConfiguration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customConfiguration);
    }
}
