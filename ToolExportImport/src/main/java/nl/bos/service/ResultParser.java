package nl.bos.service;

import org.w3c.dom.NodeList;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.logging.Logger;

public class ResultParser {
    private static final Logger LOGGER = Logger.getLogger(ResultParser.class.getSimpleName());
    private static ResultParser instance;
    private SOAPMessage soapMessage;

    public static ResultParser getInstance() {
        if (instance == null)
            instance = new ResultParser();
        return instance;
    }

    NodeList getEntityData(String expression) throws XPathExpressionException, SOAPException {
        LOGGER.info(expression);
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        return (NodeList) xPath.compile(expression).evaluate(soapMessage.getSOAPBody(), XPathConstants.NODESET);
    }

    SOAPMessage getSoapMessage() {
        return this.soapMessage;
    }

    public void setSoapMessage(SOAPMessage soapMessage) {
        this.soapMessage = soapMessage;
    }

}
