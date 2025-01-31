package nl.bos.connectors;

import com.eibus.soap.ApplicationTransaction;
import com.eibus.soap.BodyBlock;
import com.eibus.soap.Processor;
import com.eibus.soap.SOAPTransaction;
import com.eibus.util.logger.CordysLogger;
import com.eibus.xml.nom.Node;
import org.apache.commons.lang3.NotImplementedException;

public class LocalAITransaction implements ApplicationTransaction {
    private static final String TYPE_VALUE = "LocalAI";
    private final CordysLogger log = CordysLogger.getCordysLogger(this.getClass());

    public LocalAITransaction(Processor processor, SOAPTransaction transaction) {
        if(log.isDebugEnabled()) {
            String processorStatus = processor.getProcessorStatus();
            String orgUserDN = transaction.getIdentity().getOrgUserDN();
            log.debug(String.format("%s, %s", processorStatus, orgUserDN));
        }
    }

    @Override
    public void commit() {
        throw new NotImplementedException();
    }

    @Override
    public void abort() {
        throw new NotImplementedException();
    }

    @Override
    public boolean canProcess(String typeValue) {
        return typeValue.equalsIgnoreCase(TYPE_VALUE);
    }

    @Override
    public boolean process(BodyBlock request, BodyBlock response) {
        if(log.isDebugEnabled()) {
            log.debug(Node.writeToString(request.getXMLNode(), true));
        }
        return true;
    }
}
