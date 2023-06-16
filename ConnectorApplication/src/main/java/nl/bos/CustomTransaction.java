package nl.bos;

import com.eibus.soap.*;
import com.eibus.util.logger.CordysLogger;
import com.eibus.xml.nom.Node;

public class CustomTransaction implements ApplicationTransaction {

    private static final String TYPE_VALUE = "Custom App Connector"; //Must match the type-value in the 'implementation' XML!
    private static final String METHOD_NAME = "send"; //Must match the service operation name!
    private static CordysLogger log = CordysLogger.getCordysLogger(CustomTransaction.class);

    public CustomTransaction() {
        if (log.isDebugEnabled()) {
            log.debug("Transaction created.");
        }
    }

    //This is invoked by the SOAPTransaction, when the entire SOAPTransaction succeeds and can be committed.
    @Override
    public void commit() {

    }

    //This is invoked by the SOAPTransaction, when one of the ApplicationTransactions has returned false during the process() method.
    @Override
    public void abort() {

    }

    //This method returns true, if the XML mapper can process Web service operations defined of the specified type such as SQL, Java, LDAP and so on.
    @Override
    public boolean canProcess(String typeValue) {
        return typeValue.equalsIgnoreCase(TYPE_VALUE);
    }

    //This is invoked to process the relevant body blocks within this transaction.
    @Override
    public boolean process(BodyBlock request, BodyBlock response) {
        if(Node.getLocalName(request.getXMLNode()).equalsIgnoreCase(METHOD_NAME)) {
            //todo this is where the magic will happen...
            return true;
        }
        return false;
    }
}
