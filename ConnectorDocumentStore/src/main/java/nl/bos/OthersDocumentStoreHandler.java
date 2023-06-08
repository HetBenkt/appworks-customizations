package nl.bos;

import com.cordys.documentstore.*;
import com.cordys.documentstore.exceptions.DocumentStoreException;
import com.cordys.documentstore.exceptions.DocumentStoreRuntimeException;
import com.cordys.documentstore.exceptions.DocumentstoreInitializationException;
import com.cordys.documentstore.internal.soapmsgsniffer.*;
import com.cordys.documentstore.messages.Messages;
import com.cordys.documentstore.messages.OTCSMessages;
import com.cordys.documentstore.otcs.*;
import com.cordys.documentstore.search.GetNodesByPropertiesRequestInfo;
import com.cordys.documentstore.search.GetNodesByPropertiesResponseInfo;
import com.cordys.documentstore.search.SearchRequestInfo;
import com.cordys.documentstore.search.SearchResponseInfo;
import com.cordys.documentstore.util.DocumentStoreUtil;
import com.eibus.util.logger.CordysLogger;

import java.util.*;

public class OthersDocumentStoreHandler implements IDocumentStore {
    private static final CordysLogger LOGGER = CordysLogger.getCordysLogger(OthersDocumentStoreHandler.class);
    protected boolean isStoreInitialized = false;

    @Override
    public boolean initializeStore(StoreConfiguration storeConfiguration) throws DocumentStoreException, DocumentstoreInitializationException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("INTO initializeStore: classPath=%s; implClass=%s; extConfigXML=%s", storeConfiguration.getClassPath(), storeConfiguration.getImplementationClass(), storeConfiguration.getExtConfigXML()));
        }

        if (isStoreInitialized) {
            throw new DocumentStoreRuntimeException(OTCSMessages.STORE_WAS_ALREADY_INITIALIZED);
        }

        //This classcast fails for some reason in runtime? -> OthersDocumentStoreConfig othersDocumentStoreConfig = (OthersDocumentStoreConfig) storeConfiguration;
        OthersDocumentStoreInitializationHandler othersDocumentStoreInitializationHandler = new OthersDocumentStoreInitializationHandler(storeConfiguration);
        othersDocumentStoreInitializationHandler.initialize();
        isStoreInitialized = true;
        return true;
    }

    @Override
    public List<String> getSupportedServices() throws DocumentStoreException {
        SupportedServicesForOthers supportedServicesForOthers = new SupportedServicesForOthers();
        StoreSummaryResponseInfo response = (StoreSummaryResponseInfo) supportedServicesForOthers.execute();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("supportedService OTHERS = %s", Arrays.toString(response.getSupportedServices().toArray())));
        }
        return response.getSupportedServices();
    }

    @Override
    public String createDocument(int documentRequest) throws DocumentStoreException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("createDocument documentRequest = %s", documentRequest));
        }
        if (documentRequest <= 0)
            throw new DocumentStoreException(Messages.INVALID_DOC_STORE_REQ);
        Document document = DocumentStoreUtil.getDocumentFromXML(documentRequest);
        return createDocument(new CreateDocumentRequest(document)).getDocumentURL();
    }

    @Override
    public String createDocument(Document document) throws DocumentStoreException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("createDocument document = %s", document.getContentStreamUrl()));
        }
        return createDocument(new CreateDocumentRequest(document)).getDocumentURL();
    }

    @Override
    public CreateDocumentResponseInfo createDocument(CreateDocumentRequestInfo createDocumentRequestInfo) throws DocumentStoreException {
        Document doc = createDocumentRequestInfo.getDocument();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("createDocument createDocumentRequestInfo = [PATH = %s, NAME = %s]", doc.getContentStreamUrl(), doc.getName()));
        }

        OthersDocumentStoreUtil.createOthersDocumentContent(doc);

        return new CreateDocumentResponse(doc.getContentStreamUrl(), "1", new VersionInfo(0, 1));
    }

    @Override
    public Map<String, OperationResult> deleteNodes(Set<String> paths) throws DocumentStoreException {
        return null;
    }

    @Override
    public Map<String, String> createFolders(CreateFoldersRequestInfo createFoldersRequestInfo) throws DocumentStoreException {
        return null;
    }

    @Override
    public CopyNodeResponseInfo copyNode(CopyNodeRequestInfo copyNodeRequestInfo) throws DocumentStoreException {
        return null;
    }

    @Override
    public boolean deleteDocument(String url) throws DocumentStoreException {
        return false;
    }

    @Override
    public boolean deleteDocument(DeleteDocumentRequestInfo deleteDocumentRequestInfo) throws DocumentStoreException {
        return false;
    }

    @Override
    public boolean isNodeAvailable(String url) throws DocumentStoreException {
        return false;
    }

    @Override
    public Document[] getDocumentsInfo(String url) throws DocumentStoreException {
        return new Document[0];
    }

    @Override
    public Document getDocument(String url) throws DocumentStoreException {
        return null;
    }

    @Override
    public Document getDocument(NodeRequestInfo nodeRequestInfo) throws DocumentStoreException {
        String url = nodeRequestInfo.getNodeUrl();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Grab the document from the 'others' store with url: %s", url));
        }

        return OthersDocumentStoreUtil.createDocumentFromOthersContent(url, nodeRequestInfo.shouldFetchContent(), nodeRequestInfo.getFetchSetting());
    }

    @Override
    public List<Document> listNodesByParent(String parentUrl) throws DocumentStoreException {
        return null;
    }

    @Override
    public List<Document> listNodesByParent(ListNodesByParentRequestInfo listNodesByParentRequestInfo) throws DocumentStoreException {
        return null;
    }

    @Override
    public String updateDocument(Document document) throws DocumentStoreException {
        return null;
    }

    @Override
    public String updateDocument(int documentRequest) throws DocumentStoreException {
        return null;
    }

    @Override
    public OperationResult deleteParent(String parentUri) {
        return null;
    }

    @Override
    public Map<String, OperationResult> deleteParents(Set<String> parentUriSet) {
        return null;
    }

    @Override
    public UpdatePropertiesResponse updateProperties(UpdatePropertiesRequest updatePropertiesRequest) throws DocumentStoreException {
        return null;
    }

    @Override
    public CheckInResponseInfo checkin(CheckInRequestInfo checkInRequestInfo) throws DocumentStoreException {
        Document document = checkInRequestInfo.getDocument();
        return new CheckInResponse(document.getDocumentURL(), document.getVersionInfo(), document.getId());
    }

    @Override
    public CheckOutResponseInfo checkout(CheckOutRequestInfo checkOutRequestInfo) throws DocumentStoreException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("checkout checkOutRequestInfo = [NodeURL = %s; fetchSetting = %s]", checkOutRequestInfo.getNodeUrl(), checkOutRequestInfo.getFetchSetting()));
        }
        ExtendedDocument document = new ExtendedDocument();
        document.setName("dummy");
        return new CheckOutResponse(document);
    }

    @Override
    public ReleaseLockResponseInfo releaseLock(ReleaseLockRequestInfo releaseLockRequestInfo) throws DocumentStoreException {
        return null;
    }

    @Override
    public GetCategoriesResponseInfo getCategories(GetCategoriesRequestInfo getCategoriesRequestInfo) throws DocumentStoreException {
        return null;
    }

    @Override
    public ListCategoriesResponseInfo listCategories(ListCategoriesRequestInfo listCategoriesRequestInfo) throws DocumentStoreException {
        return null;
    }

    @Override
    public GetVersionHistoryResponseInfo getVersionHistory(GetVersionHistoryRequestInfo getVersionHistoryRequestInfo) throws DocumentStoreException {
        return null;
    }

    @Override
    public SearchResponseInfo search(SearchRequestInfo searchRequestInfo) throws DocumentStoreException {
        return null;
    }

    @Override
    public GetAuditRecordsForNodesResponseInfo getAuditRecordsForNodes(GetAuditRecordsForNodesRequestInfo getAuditRecordsForNodesRequestInfo) throws DocumentStoreException {
        return null;
    }

    @Override
    public CreateCategoryResponseInfo createCategory(CreateCategoryRequestInfo createCategoryRequestInfo) throws DocumentStoreException {
        return null;
    }

    @Override
    public AssociateCategoriesToNodeResponseInfo associateCategoriesToNode(AssociateCategoriesToNodeRequestInfo associateCategoriesToNodeRequestInfo) throws DocumentStoreException {
        return null;
    }

    @Override
    public GetCategoriesAssociatedWithNodeResponseInfo getCategoriesAssociatedWithNode(GetCategoriesAssociatedWithNodeRequestInfo getCategoriesAssociatedWithNodeRequestInfo) throws DocumentStoreException {
        return null;
    }

    @Override
    public GetNodesByPropertiesResponseInfo getNodesByProperties(GetNodesByPropertiesRequestInfo getNodesByPropertiesRequestInfo) throws DocumentStoreException {
        return null;
    }
}
