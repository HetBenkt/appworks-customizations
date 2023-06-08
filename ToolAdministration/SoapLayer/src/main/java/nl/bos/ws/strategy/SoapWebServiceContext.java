package nl.bos.ws.strategy;

public class SoapWebServiceContext {
    private final SoapWebServiceStrategy soapWebServiceStrategy;

    public SoapWebServiceContext(final SoapWebServiceStrategy soapWebServiceStrategy) {
        this.soapWebServiceStrategy = soapWebServiceStrategy;
    }

    public String execute() {
        return soapWebServiceStrategy.run();
    }
}
