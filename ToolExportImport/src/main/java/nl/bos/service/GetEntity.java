package nl.bos.service;

import nl.bos.message.Log;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.util.logging.Level;

public class GetEntity extends GenericService implements ISoapMessage {

    private final String token;
    private final String entityName;

    public GetEntity(String token, String entityName) {
        this.token = token;
        this.entityName = entityName;
    }

    /**
     * <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
     * <SOAP:Header>
     * <OTAuthentication xmlns="urn:api.bpm.opentext.com">
     * <AuthenticationToken>[TOKEN]</AuthenticationToken>
     * </OTAuthentication>
     * </SOAP:Header>
     * <SOAP:Body>
     * <get xmlns="http://schemas/MyCompanynlboshelloworld/{entityName}/operations">
     * <ns0:Cursor xmlns:ns0="http://schemas.opentext.com/bps/entity/core" offset="0" limit="100" />
     * </get>
     * </SOAP:Body>
     * </SOAP:Envelope>
     */
    @Override
    public SOAPMessage buildSoapMessage() {
        try {
            SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
            SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
            ServiceUtils.addAuthenticationHeader(soapEnvelope, token);

            SOAPBody soapBody = soapEnvelope.getBody();

            QName operationGetAllCategory = new QName(String.format("http://schemas/MyCompanynlboshelloworld/%s/operations", entityName), "get");
            soapBody.addBodyElement(operationGetAllCategory);

            Log.getInstance().send(ServiceUtils.soapMessageToString(soapMessage));
            return soapMessage;
        } catch (SOAPException e) {
            Log.getInstance().send(e.getMessage(), Level.SEVERE);
        }
        return null;
    }
}
