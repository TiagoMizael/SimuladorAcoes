package br.com.softexpert.acoes.email;

import br.com.softexpert.acoes.objects.Conta;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.List;
import java.util.Properties;

public class EnviaEmail {
    public static void envia(List<Conta> contas, String file) {

        final String username = "javamail1994@gmail.com";
        final String password = "javamail@123";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            for(Conta conta: contas) {
                Multipart mps = new MimeMultipart();
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(conta.getEmail()));
                message.setSubject("Relatório Bolsa de Valores");
                message.setText("Novo Relatório em Anexo");

                MimeBodyPart body = new MimeBodyPart();
                String text = "Você tem um novo relatório em anexo!";
                String html = "<h1>"+text+"</h1>";

                body.setContent(html,"text/html; charset=utf-8");
                body.setText(text,"utf-8");

                MimeBodyPart attachFilePart = new MimeBodyPart();
                FileDataSource fds =   new FileDataSource(file);
                attachFilePart.setDataHandler(new DataHandler(fds));
                attachFilePart.setFileName(fds.getName());

                mps.addBodyPart(attachFilePart);
                mps.addBodyPart(body);
                message.setContent(mps);
                Transport.send(message);
                System.out.println("Pronto!");
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
