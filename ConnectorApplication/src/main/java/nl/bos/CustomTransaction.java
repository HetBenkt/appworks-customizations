package nl.bos;

import com.eibus.soap.*;
import com.eibus.util.logger.CordysLogger;
import com.eibus.xml.nom.Node;
import nl.bos.command.ICommand;
import nl.bos.command.CommandPublish;
import nl.bos.command.CommandSubscribe;

public class CustomTransaction implements ApplicationTransaction {

    private static final String TYPE_VALUE = "Custom App Connector"; //Must match the type-value in the 'implementation' XML!
    private static final String METHOD_NAME_SUBSCRIBE = "subscribe"; //Must match the service operation name!
    private static final String METHOD_NAME_PUBLISH = "publish"; //Must match the service operation name!
    private static CordysLogger log = CordysLogger.getCordysLogger(CustomTransaction.class);
    private final IMqttService service;


    public CustomTransaction(IMqttService service) {
        if (log.isDebugEnabled()) {
            log.debug("Transaction created.");
        }
        this.service = service;
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
        if(log.isDebugEnabled()) {
            log.debug(Node.writeToString(request.getMethodDefinition().getImplementation(), true));
        }


        switch(Node.getLocalName(request.getXMLNode())) {
            case METHOD_NAME_SUBSCRIBE -> {
                String topic = Node.getData(Node.getElement(request.getXMLNode(), "topic"));
                ICommand subscribe = new CommandSubscribe(service, topic);
                return subscribe.apply(); //TODO A response on the subscription will happen a publication; And we want to see it!?!? I guess?
            }
            case METHOD_NAME_PUBLISH -> {
                String topic = Node.getData(Node.getElement(request.getXMLNode(), "topic"));
                String payload = Node.getData(Node.getElement(request.getXMLNode(), "payload"));
                boolean retain = Boolean.parseBoolean(Node.getData(Node.getElement(request.getXMLNode(), "retain")));
                ICommand publish = new CommandPublish(service, topic, payload, retain);
                return publish.apply();
            }
            default -> {
                return false;
            }
        }
    }
}
