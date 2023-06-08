package nl.bos.service;

import nl.bos.Configuration;
import nl.bos.message.Log;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;
import java.net.HttpURLConnection;

public class GenericService implements IServiceCommand {
    private final ResultParser resultParser = ResultParser.getInstance();
    private final Configuration configuration = Configuration.getInstance();

    @Override
    public void execute(SOAPMessage soapMessage) throws NoSuchFieldException, IOException, SOAPException {
        HttpURLConnection connection = ServiceUtils.createConnection(configuration.getValue("soap.url"), "POST", "text/xml; charset=utf-8");

        SOAPMessage soapMessageResponse = ServiceUtils.callService(connection, soapMessage);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            Log.getInstance().send(ServiceUtils.soapMessageToString(soapMessageResponse));

            resultParser.setSoapMessage(soapMessageResponse);
            connection.disconnect();
        }
    }
}
