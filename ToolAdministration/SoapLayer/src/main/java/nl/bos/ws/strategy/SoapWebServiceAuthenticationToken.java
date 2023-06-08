package nl.bos.ws.strategy;

import jakarta.xml.soap.*;

public class SoapWebServiceAuthenticationToken extends SoapWebServiceToken {
    private final String otdsTicket;

    public SoapWebServiceAuthenticationToken(final String url, final String otdsTicket) {
        super(url);
        this.otdsTicket = otdsTicket;
    }

    @Override
    void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String namespaceSamlp = "samlp";
        String namespaceSamlpURI = "urn:oasis:names:tc:SAML:1.0:protocol";
        String namespaceSaml = "saml";
        String namespaceSamlURI = "urn:oasis:names:tc:SAML:1.0:assertion";
        String namespaceWsse = "wsse";
        String namespaceWsseURI = "urn:api.ecm.opentext.com";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(namespaceSamlp, namespaceSamlpURI);
        envelope.addNamespaceDeclaration(namespaceSaml, namespaceSamlURI);
        envelope.addNamespaceDeclaration(namespaceWsse, namespaceWsseURI);

        // SOAP Header
        SOAPHeader soapHeader = soapMessage.getSOAPHeader();
        SOAPElement soapOTAuthenticationElem = soapHeader.addChildElement("OTAuthentication", namespaceWsse);
        SOAPElement soapAuthenticationTokenElem = soapOTAuthenticationElem.addChildElement("AuthenticationToken", namespaceWsse);
        soapAuthenticationTokenElem.addTextNode(otdsTicket);

        buildSoapBody(namespaceSamlp, namespaceSaml, envelope);
    }
}
