package de.bytephil.utils;


import de.bytephil.enums.MessageType;
import de.bytephil.main.Main;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {

    private static Session mailSession;

    public static void setupEmail() {
        ServerConfiguration configuration = Main.config;

        final Properties prop = new Properties();
        prop.put("mail.smtp.username", configuration.emailuser);
        prop.put("mail.smtp.password", configuration.emailpassword);
        prop.put("mail.smtp.host", configuration.emailhost);
        prop.put("mail.smtp.port", configuration.emailport);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS
        mailSession = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(prop.getProperty("mail.smtp.username"),
                        prop.getProperty("mail.smtp.password"));
            }
        });
    }
    public static void sendEmail(String receiver)  {
        ServerConfiguration configuration = Main.config;
        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(configuration.emailuser));
            message.setSubject("Sending Mail with pure Java Mail API ");
            /* Mail body with plain Text */
            message.setText("Hello User,"
                    + "\n\n If you read this, means mail sent with Java Mail API is successful");
            InternetAddress[] toEmailAddresses =
                    InternetAddress.parse(receiver);
            InternetAddress[] ccEmailAddresses =
                    InternetAddress.parse(receiver);
            InternetAddress[] bccEmailAddresses =
                    InternetAddress.parse(receiver);

            message.setRecipients(Message.RecipientType.TO,toEmailAddresses);
            message.setRecipients(Message.RecipientType.CC,ccEmailAddresses);
            message.setRecipients(Message.RecipientType.BCC,bccEmailAddresses);

            Console.printout("Sent email to " + receiver, MessageType.INFO);
        } catch (AddressException e1) {
            Console.printout(e1.getMessage(), MessageType.ERROR);
        } catch (MessagingException e) {
            e.printStackTrace();
            Console.printout(e.getMessage(), MessageType.ERROR);
        }

    }

    public static void sendEmailOLD() {

        ServerConfiguration configuration = Main.config;

        final String username = configuration.emailuser;
        final String password = configuration.emailpassword;


        //TODO Get username and password from file


        Properties prop = new Properties();
        prop.put("mail.smtp.host", configuration.emailhost);
        prop.put("mail.smtp.port", configuration.emailport);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("phitho2018@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("phitho2018@gmail.com, phitho2018@gmail.com")
            );
            message.setSubject("Testing Gmail SSL");
            message.setText("Dear Mail Crawler,"
                    + "\n\n Please do not spam my email!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
