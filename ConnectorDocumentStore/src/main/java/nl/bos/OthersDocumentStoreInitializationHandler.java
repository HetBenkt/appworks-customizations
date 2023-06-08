package nl.bos;

import com.cordys.documentstore.StoreConfiguration;
import com.eibus.util.logger.CordysLogger;

public class OthersDocumentStoreInitializationHandler {
    private static final CordysLogger LOGGER = CordysLogger.getCordysLogger(OthersDocumentStoreInitializationHandler.class);
    private final StoreConfiguration storeConfiguration;

    public OthersDocumentStoreInitializationHandler(StoreConfiguration storeConfiguration) {
        this.storeConfiguration = storeConfiguration;
    }

    public void initialize() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Successfully established a connection to the 'others' identified by %s", storeConfiguration.getStoreName()));
        }
    }
}
