package nl.bos.message;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import nl.bos.Configuration;
import nl.bos.Storage;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Mail.class, Transport.class})
public class MailTest {
    private final static String MAIL_MESSAGE = "<html>\r\n<body>\r\n<p>Thank You %s. We have received your request on %s</p>\r\n<p>We will keep you posted</p>\r\n</body>\r\n</html>";
    private final static String NAME = "John Doe";
    private final static String DATE = String.valueOf(Calendar.getInstance().getTime());
    private final static Map<String, String> paramMap = new HashMap<>();
    private final Storage storage;
    private Template mailTemplate;

    public MailTest() throws IOException, NoSuchFieldException, ConfigurationException {
        storage = Storage.getInstance();
        mailTemplate = storage.getMailTemplate(Configuration.getInstance().getValue("mail.template"));

        paramMap.put("name", NAME);
        paramMap.put("date", DATE);
    }

    @Test
    public void buildMailMessageFromTemplateTest() throws IOException, TemplateException, MessagingException, NoSuchFieldException {
        Mail mail = Mail.getInstance();
        mail.buildMailMessageFromTemplate(mailTemplate, paramMap);
        Assert.assertNotNull(mail.getMessage());
        Assert.assertEquals(String.format(MAIL_MESSAGE, NAME, DATE), getTextFromMessage(mail.getMessage()));
    }

    @Test(expected = TemplateException.class)
    public void buildMailMessageFromTemplateFailTest() throws IOException, TemplateException, MessagingException, NoSuchFieldException {
        Template mockTemplate = Mockito.mock(Template.class);
        Mockito.doThrow(TemplateException.class).when(mockTemplate).process(Mockito.anyObject(), Mockito.anyObject());

        mailTemplate = storage.getMailTemplate(Configuration.getInstance().getValue("mail.template"));
        Mail.getInstance().buildMailMessageFromTemplate(mockTemplate, paramMap);
    }

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
        BodyPart bodyPart = mimeMultipart.getBodyPart(0);
        return String.valueOf(bodyPart.getContent());
    }

    @Ignore("It's a real test; just to double check on a real server")
    @Test
    public void sendRealTest() throws IOException, TemplateException, MessagingException, NoSuchFieldException {
        Mail mail = Mail.getInstance();
        mail.buildMailMessageFromTemplate(mailTemplate, paramMap);
        Assert.assertNotNull(mail.getMessage());

        mail.send("Test");
    }

    @Test
    public void sendMockedTest() throws IOException, TemplateException, MessagingException, NoSuchFieldException {
        PowerMockito.spy(Transport.class);
        PowerMockito.doNothing().when(Transport.class);
        Transport.send(Mockito.anyObject());

        Mail mail = Mail.getInstance();
        mail.buildMailMessageFromTemplate(mailTemplate, paramMap);
        Assert.assertNotNull(mail.getMessage());

        mail.send("Test");
    }

    @Test(expected = MessagingException.class)
    public void sendFailTest() throws Exception {
        PowerMockito.whenNew(InternetAddress.class).withArguments(Mockito.anyString()).thenThrow(new AddressException());

        Mail mail = Mail.getInstance();
        mail.buildMailMessageFromTemplate(mailTemplate, paramMap);
        Assert.assertNotNull(mail.getMessage());

        mail.send("Test");
    }
}