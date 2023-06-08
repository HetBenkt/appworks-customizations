package nl.bos.message;

import org.junit.Test;

import javax.mail.PasswordAuthentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MailAuthenticatorTest {

    @Test
    public void getPasswordAuthentication() {
        String username = "John";
        String password = "demo";
        MailAuthenticator mailAuthenticator = new MailAuthenticator(username, password);
        PasswordAuthentication passwordAuthentication = mailAuthenticator.getPasswordAuthentication();
        assertNotNull(passwordAuthentication);
        assertEquals(username, passwordAuthentication.getUserName());
        assertEquals(password, passwordAuthentication.getPassword());
    }
}