package com.jvpgrando.mailer.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Optional;
import java.util.Properties;

@ApplicationScoped
public class MailConfig {

    @ConfigProperty(name = "mail.smtp.host")
    String host;

    @ConfigProperty(name = "mail.smtp.port")
    String port;

    @ConfigProperty(name = "mail.smtp.auth")
    Optional<Boolean> auth;

    @ConfigProperty(name = "mail.smtp.starttls.enable")
    Optional<Boolean> starttls;

    @ConfigProperty(name = "mail.smtp.username")
    Optional<String> username;

    @ConfigProperty(name = "mail.smtp.password")
    Optional<String> password;

    @ConfigProperty(name = "mail.from")
    Optional<String> from;

    @Produces
    @Singleton
    public Session mailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", auth.orElse(true));
        props.put("mail.smtp.starttls.enable", starttls.orElse(true));

        final String user = username.orElse("").trim();
        final String pass = password.orElse("").trim();
        final String mailFrom = from.orElse(user).trim();

        if (user.isEmpty() || pass.isEmpty() || mailFrom.isEmpty()) {
            throw new IllegalStateException("Configuração SMTP inválida: defina GMAIL_USER e GMAIL_PASS.");
        }
        props.put("mail.from", mailFrom);

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
    }

    @Produces
    @Singleton
    public MimeMessage mimeMessage(Session session) {
        return new MimeMessage(session);
    }

    @Produces
    @Singleton
    public TemplateEngine templateEngine() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);
        return engine;
    }
}

