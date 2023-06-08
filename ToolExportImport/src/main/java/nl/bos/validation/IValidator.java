package nl.bos.validation;

import org.xml.sax.SAXException;

import javax.mail.MessagingException;
import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface IValidator {
    boolean isReady() throws JAXBException, NoSuchFieldException, IOException, SAXException, MessagingException;
}
