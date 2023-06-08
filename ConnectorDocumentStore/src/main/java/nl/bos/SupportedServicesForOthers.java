package nl.bos;

import com.cordys.documentstore.IResponseInfo;
import com.cordys.documentstore.internal.soapmsgsniffer.StoreSummaryResponseInfoImpl;

import java.util.ArrayList;
import java.util.List;

public class SupportedServicesForOthers extends AbstractOthersOperationBase {
    public SupportedServicesForOthers() {
        super();
    }

    @Override
    protected IResponseInfo execute() {
        List<String> supportedServices = new ArrayList<>();

        supportedServices.add("CreateDocument");
        supportedServices.add("GetDocument");
        supportedServices.add("GetDocumentStoreSummary");
        supportedServices.add("CheckIn");
        supportedServices.add("CheckOut");

        return new StoreSummaryResponseInfoImpl(supportedServices);
    }
}
