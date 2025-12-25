package com.jvpgrando.mailer.service;

import com.jvpgrando.mailer.dto.ContactRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.StringWriter;
import java.util.Properties;

@ApplicationScoped
public class EmailService {

    @Inject
    TemplateEngine templateEngine;

    @Inject
    Session mailSession;

    public void sendContact(ContactRequest req) {
        try {
            String html = renderTemplate(req);
            MimeMessage message = new MimeMessage(mailSession);
            String from = mailSession.getProperty("mail.from");

            if (from == null || from.isBlank()) {
                throw new IllegalStateException("Remetente não configurado (mail.from).");
            }

            InternetAddress fromAddress = new InternetAddress(from);
            message.setFrom(fromAddress);
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[] { fromAddress });
            message.setSubject("Novo contato via portfólio");
            message.setContent(html, "text/html; charset=UTF-8");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }

    private String renderTemplate(ContactRequest req) {
        Context ctx = new Context();
        ctx.setVariable("name", req.name);
        ctx.setVariable("email", req.email);
        ctx.setVariable("phone", req.phone);
        ctx.setVariable("message", req.message);

        StringWriter writer = new StringWriter();
        templateEngine.process("contact-email", ctx, writer);
        return writer.toString();
    }
}

