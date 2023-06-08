package nl.bos;

import com.cordys.documentstore.IRequestInfo;
import com.cordys.documentstore.IResponseInfo;
import com.cordys.documentstore.exceptions.DocumentStoreException;
import com.eibus.util.logger.CordysLogger;

public abstract class AbstractOthersOperationBase {
    private static final CordysLogger LOGGER = CordysLogger.getCordysLogger(AbstractOthersOperationBase.class);

    public AbstractOthersOperationBase(IRequestInfo requestInfo) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("RequestInfo for class %s", requestInfo.getClass().getSimpleName()));
        }
    }

    public AbstractOthersOperationBase() {
    }

    protected abstract IResponseInfo execute() throws DocumentStoreException;
}
