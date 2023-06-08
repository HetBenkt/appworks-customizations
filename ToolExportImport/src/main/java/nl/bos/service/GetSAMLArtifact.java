package nl.bos.service;

import nl.bos.message.Log;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.util.logging.Level;

public class GetSAMLArtifact extends GenericService implements ISoapMessage {

    private final String token;

    public GetSAMLArtifact(String token) {
        this.token = token;
    }

    /**
     * <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
     * <SOAP:Header>
     * <OTAuthentication xmlns="urn:api.bpm.opentext.com">
     * <AuthenticationToken>[TOKEN]</AuthenticationToken>
     * </OTAuthentication>
     * </SOAP:Header>
     * <SOAP:Body>
     * <samlp:Request xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" MajorVersion="1" MinorVersion="1" IssueInstant="2018-09-07T16:47:13.359Z" RequestID="a5470c392e-264e-jopl-56ac-4397b1b416d">
     * <samlp:AuthenticationQuery>
     * <saml:Subject xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion">
     * <saml:NameIdentifier Format="urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified"/>
     * </saml:Subject>
     * </samlp:AuthenticationQuery>
     * </samlp:Request>
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

            QName operationGetAllCategory = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Request", "samlp");
            SOAPBodyElement soapBodyElement = soapBody.addBodyElement(operationGetAllCategory);
            soapBodyElement.addAttribute(new QName("MajorVersion"), "1");
            soapBodyElement.addAttribute(new QName("MinorVersion"), "1");
            SOAPElement authenticationQuery = soapBodyElement.addChildElement(new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthenticationQuery", "samlp"));
            QName operationSubject = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Subject", "saml");
            SOAPElement soapElement = authenticationQuery.addChildElement(operationSubject);
            SOAPElement nameIdentifier = soapElement.addChildElement(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifier", "saml"));
            nameIdentifier.addAttribute(new QName("Format"), "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");

            Log.getInstance().send(ServiceUtils.soapMessageToString(soapMessage));
            return soapMessage;
        } catch (SOAPException e) {
            Log.getInstance().send(e.getMessage(), Level.SEVERE);
        }
        return null;
    }
}
