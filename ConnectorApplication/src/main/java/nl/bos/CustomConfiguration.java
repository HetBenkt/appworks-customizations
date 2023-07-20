package nl.bos;

import com.cordys.security.encryption.Password;
import com.eibus.util.Util;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.XPath;
import com.opentext.platform.configuration.api.Component;
import com.opentext.platform.configuration.api.ComponentDefinition;
import com.opentext.platform.configuration.api.SettingComponentBase;
import com.opentext.platform.configuration.api.SettingDefinition;

//TODO I misuse the FTP.caf data for the time being!
@ComponentDefinition(name = "custom", parent = CustomConnectorConfiguration.class)
public class CustomConfiguration extends SettingComponentBase {
    private int configurationNode;

    public CustomConfiguration(Component parent) {
        super(parent);
    }

    @SettingDefinition(name = "ftpProxyUsername")
    public String getUserName() {
        return getSetting(null, () -> getLdapProperty("ftpProxyUsername", ""));
    }

    @SettingDefinition(name = "ftpProxyPassword")
    public Password getPassword() {
        return getSetting(null, () -> makePassword(getLdapProperty("ftpProxyPassword", "")));
    }

    private Password makePassword(String password) {
        return Util.isStringEmpty(password) ? null : Password.fromBase64(password);
    }

    @SettingDefinition(name = "ftpProxyHost", defaultValue = "")
    public String getHost() {
        return getSetting(null, () -> getLdapProperty("ftpProxyHost", ""));
    }

    @SettingDefinition(name = "ftpProxyPort", defaultValue = "0")
    public int getPort() {
        return getSetting(null, () -> Integer.valueOf(getLdapProperty("ftpProxyPort", "0")));
    }

    @SettingDefinition(name = "keepAlive", defaultValue = "0")
    public int getKeepAlive() {
        return Integer.MAX_VALUE; //TODO Should be in the config, but we have no field (yet!)
    }

    private String getLdapProperty(String propName, String defaultValue) {
        int propNode = XPath.getFirstMatch(propName, null, configurationNode);
        return Node.getDataWithDefault(propNode, defaultValue);
    }

    public void setConfigurationNode(int configurationNode) {
        this.configurationNode = configurationNode;
    }
}
