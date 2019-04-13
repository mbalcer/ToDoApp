package utility;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.ResourceBundle;

public class Email {

    private final String senderEmail = "mateusz75319@gmail.com";
    private final String senderPassword = "password";
    private final String emailSMTPserver = "smtp.gmail.com";
    private final String emailServerPort = "587";
    private String receiverEmail = null;
    private String emailSubject = null;
    private String emailBody = null;
    private ResourceBundle bundle;


    public Email(String receiverEmail) {
        this.bundle = ResourceBundle.getBundle("bundles.messages");
        this.receiverEmail = receiverEmail;
        this.emailSubject = "ToDoApp";
        this.emailBody = this.bundle.getString("email.message");

        Properties props = new Properties();
        props.put("mail.smtp.user", senderEmail);
        props.put("mail.smtp.host", emailSMTPserver);
        props.put("mail.smtp.port", emailServerPort);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", emailServerPort);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        SecurityManager security = System.getSecurityManager();

        try {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);

            Message msg = new MimeMessage(session);
            msg.setText(emailBody);
            msg.setSubject(emailSubject);
            msg.setFrom(new InternetAddress(senderEmail));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(receiverEmail));
            Transport.send(msg);
            System.out.println("send successfully");
        } catch (Exception ex) {
            System.err.println("Error occurred while sending.!");
        }

    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmail, senderPassword);
        }
    }
}
