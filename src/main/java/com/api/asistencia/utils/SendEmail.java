/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.asistencia.utils;

import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.util.Properties;
/**
 *
 * @author USUARIO
 */
public class SendEmail 
{
      public static String format_email(String numeroverificacion)
    {
        return "<h3>El numero de verificación de su cuenta es: <b>"+numeroverificacion+"</b></h3>";
    }
    
    public static boolean SendEmail(String email,String asunto, String body)
    {
        try{
            String destinatario=email;
            String correo_envia="jleturnep@uteq.edu.ec";

            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com"); 
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props, null);

            MimeMultipart multiParte = new MimeMultipart();
            BodyPart texto = new MimeBodyPart();
            texto.setContent(body, "text/html"); //se puede utilizar etiquetas html
            multiParte.addBodyPart(texto);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correo_envia));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setContent(multiParte);
            message.saveChanges();

            Transport trans = session.getTransport("smtp");
            trans.connect(correo_envia, "jugtgdtcltalefvp");
            trans.sendMessage(message, message.getAllRecipients());
            trans.close();
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
}
