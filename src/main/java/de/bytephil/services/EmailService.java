package de.bytephil.services;

import de.bytephil.main.Main;
import de.bytephil.utils.ServerConfiguration;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

    public static void send(String to,String sub,String msg){
        ServerConfiguration configuration = Main.config;
        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", configuration.emailhost);
        if (configuration.emailSecureMethod.equalsIgnoreCase("SSL")) {
            props.put("mail.smtp.socketFactory.port", configuration.emailport);
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
        } else {
            props.put("mail.smtp.starttls.enable", "true");
        }
        props.put("mail.smtp.port", configuration.emailport);
        props.put("mail.smtp.auth", "true");

        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(configuration.emailuser,configuration.emailpassword);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            if (configuration.emailDisplayName.isEmpty()) {
                message.setFrom(new InternetAddress(configuration.emailuser));
            } else {
                message.setFrom(new InternetAddress(configuration.emailuser, configuration.emailDisplayName));
            }
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            Transport.send(message);
        } catch (MessagingException e) {throw new RuntimeException(e);} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    //  EmailService.send(config.emailuser, config.emailpassword, "phitho2018@gmail.com", "Hallo", "Hallo du");
}
