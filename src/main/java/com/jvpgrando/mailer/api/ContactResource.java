package com.jvpgrando.mailer.api;

import com.jvpgrando.mailer.dto.ContactRequest;
import com.jvpgrando.mailer.service.EmailService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.UUID;

@Path("/api/contact")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {

    @Inject
    EmailService emailService;

    @POST
    public Response send(ContactRequest request) {
        emailService.sendContact(request);
        String reference = UUID.randomUUID().toString();
        return Response.ok().entity(new ContactResponse("ok", reference)).build();
    }

    public record ContactResponse(String status, String referenceId) {
    }
}
