package io.gupshup.workshop.chatgroup.routes;

import io.gupshup.workshop.chatgroup.annotations.Authenticated;
import io.gupshup.workshop.chatgroup.authentication.impl.JWTAuthenticator;
import io.gupshup.workshop.chatgroup.constants.Constants;
import io.gupshup.workshop.chatgroup.models.Message;
import io.gupshup.workshop.chatgroup.models.User;
import io.gupshup.workshop.chatgroup.responses.SuccessResponse;
import io.gupshup.workshop.chatgroup.services.MessageService;
import io.gupshup.workshop.chatgroup.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("messages")
public class MessageRoute {
    @Inject
    private MessageService messageService;

    @Inject
    private UserService userService;

    @Context
    private SecurityContext securityContext;


    @GET
    public Response listMessages (
            @QueryParam("fromId")
            @DefaultValue
                    ("")
                    String fromId) {
        final List <Message> messages = messageService.listMessages(fromId);
        return new SuccessResponse().addJsonElement("messages", Constants.GSON.toJsonTree(messages)).build();
    }


    @POST
    @Authenticated(by = JWTAuthenticator.class)
    public Response addMessage (
            @FormParam("content")
                    String messageContent) {
        User    user    = userService.getUserFromSecurityContext(securityContext);
        Message message = messageService.sendMessage(user, messageContent);
        return new SuccessResponse().addJsonElement("message", Constants.GSON.toJsonTree(message)).build();
    }
}
