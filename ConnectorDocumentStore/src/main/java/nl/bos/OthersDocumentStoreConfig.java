package nl.bos;

import com.cordys.documentstore.StoreConfiguration;

public class OthersDocumentStoreConfig extends StoreConfiguration {

    public String getApiURL() {
            return getValueForXPath(".//storeconfiguration/api_url");
    }

    public String getUserName() {
        return getValueForXPath(".//storeconfiguration/username");
    }

    public String getPassword() {
        return getValueForXPath(".//storeconfiguration/password");
    }
}
