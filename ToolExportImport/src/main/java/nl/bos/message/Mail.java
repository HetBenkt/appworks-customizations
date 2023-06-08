package nl.bos.message;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import nl.bos.Configuration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

public class Mail extends AbstractMessage {
    private static Mail instance;
    private final Properties props = new Properties();
    private final Configuration configuration = Configuration.getInstance();
    private Message message;

    private Mail() throws NoSuchFieldException {
        super();
        props.put("mail.smtp.host", configuration.getValue("mail.server"));
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.port", configuration.getValue("mail.port"));
        props.put("mail.smtp.ssl.trust", configuration.getValue("mail.server"));
    }

    public static Mail getInstance() throws NoSuchFieldException {
        if (instance == null) {
            instance = new Mail();
        }
        return instance;
    }

    @Override
    public void send(String subject) throws MessagingException, NoSuchFieldException {
        message.setFrom(new InternetAddress(configuration.getValue("mail.from_address")));
        message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(configuration.getValue("mail.to_address")));
        message.setSubject(subject);
        Transport.send(message);
    }

    void buildMailMessageFromTemplate(Template mailTemplate, Map<String, String> paramMap) throws IOException, TemplateException, MessagingException, NoSuchFieldException {
        Writer out = new StringWriter();
        mailTemplate.process(paramMap, out);

        BodyPart body = new MimeBodyPart();
        body.setContent(out.toString(), "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(body);
        message = new MimeMessage(Session.getInstance(props, new MailAuthenticator(configuration.getValue("mail.user_name"), configuration.getValue("mail.password"))));
        message.setContent(multipart);
    }

    @Override
    public boolean isReady() throws NoSuchFieldException, MessagingException {
        if (instance == null) {
            return false;
        }

        mailServerReachable();
        return true;
    }

    private void mailServerReachable() throws MessagingException, NoSuchFieldException {
        Session session = Session.getInstance(props, new MailAuthenticator(configuration.getValue("mail.user_name"), configuration.getValue("mail.password")));
        session.getTransport("smtp").connect();
    }

    public Message getMessage() {
        return message;
    }
}
