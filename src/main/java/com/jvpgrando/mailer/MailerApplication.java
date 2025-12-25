package com.jvpgrando.mailer;

import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.Quarkus;

@QuarkusMain
public class MailerApplication {
    public static void main(String... args) {
        Quarkus.run(args);
    }
}

