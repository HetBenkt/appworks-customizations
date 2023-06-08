package nl.bos.service;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;

public interface IServiceCommand {
    void execute(SOAPMessage soapMessage) throws NoSuchFieldException, IOException, SOAPException;
}
