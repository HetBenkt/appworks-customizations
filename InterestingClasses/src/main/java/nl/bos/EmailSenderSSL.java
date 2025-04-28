package nl.bos;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailSenderSSL {

    public static void main(String[] args) {
        // Replace with your credentials and email details
        final String username = "xxx@gmail.com"; // Your email
        final String password = "xxxx xxxx xxxx xxxx"; // Your email password or app password
        final String recipientEmail = ""; // Recipient's email
        final String subject = "Test Email using SSL and Jakarta Mail";
        final String body = "Hello! This is a plain text email sent via SSL on port 465 using Jakarta Mail.";

        // Set up SMTP server properties for SSL
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
        props.put("mail.smtp.port", "465");           // SSL Port
        props.put("mail.smtp.auth", "true");          // Enable authentication
        props.put("mail.smtp.socketFactory.port", "465"); // SSL Socket Factory port
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory class
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");

        // Create a session with an authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a plain text message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body); // Plain text message content

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send the email.");
        }
    }

}
