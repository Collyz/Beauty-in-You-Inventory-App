package com.inventory.inventory;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {

    public void sendMail(String recipient, String subjectMsg, String subjectText) throws Exception {
        System.out.println("Attempting to send email...");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host","smtp.office365.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "biyinventoryapplication@hotmail.com";
        String password = "ThisGirlIsOnFire1!1!";

        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(session, myAccountEmail, recipient, subjectMsg, subjectText);

        Transport.send(message);
        System.out.println("Success! Probably.");
    }

    private Message prepareMessage(Session session, String myAccountEmail, String recipient, String subjectMsg, String subjectText){
        try{

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subjectMsg);
            message.setText(subjectText);
            return message;
        } catch (Exception ex) {
            System.out.println("Well I hope this doesn't break shit");
        }
        return null;
    }
}