package nl.bos.ws.strategy;

import jakarta.xml.soap.*;
import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.config.Configuration;

public class SoapWebServiceUsernameToken extends SoapWebServiceToken {
    private static final Configuration config = AppWorksPlatformImpl.getInstance().getConfig();

    public SoapWebServiceUsernameToken(final String url) {
        super(url);
    }

    @Override
    void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String namespaceSamlp = "samlp";
        String namespaceSamlpURI = "urn:oasis:names:tc:SAML:1.0:protocol";
        String namespaceSaml = "saml";
        String namespaceSamlURI = "urn:oasis:names:tc:SAML:1.0:assertion";
        String namespaceWsse = "wsse";
        String namespaceWsseURI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(namespaceSamlp, namespaceSamlpURI);
        envelope.addNamespaceDeclaration(namespaceSaml, namespaceSamlURI);
        envelope.addNamespaceDeclaration(namespaceWsse, namespaceWsseURI);

        // SOAP Header
        SOAPHeader soapHeader = soapMessage.getSOAPHeader();
        SOAPElement soapSecurityElem = soapHeader.addChildElement("Security", namespaceWsse);
        SOAPElement soapUsernameTokenElem = soapSecurityElem.addChildElement("UsernameToken", namespaceWsse);
        SOAPElement soapUsernameElem = soapUsernameTokenElem.addChildElement("Username", namespaceWsse);
        soapUsernameElem.addTextNode(config.getProperties().getProperty("admin_username"));
        SOAPElement soapPasswordElem = soapUsernameTokenElem.addChildElement("Password", namespaceWsse);
        soapPasswordElem.addTextNode(config.getProperties().getProperty("admin_password"));

        buildSoapBodyWithUser(namespaceSamlp, namespaceSaml, envelope);
    }
}
