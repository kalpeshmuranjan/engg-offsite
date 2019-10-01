package io.gupshup.workshop.chatgroup.routes;

import io.gupshup.workshop.chatgroup.responses.SuccessResponse;
import io.gupshup.workshop.chatgroup.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("user")
public class UserRoute {

    @Inject
    private UserService userService;


    @POST
    @Path("register")
    public Response registerUser (
            @FormParam("userName")
                    String userName) {
        final String userToken = userService.generateToken(userName);
        return new SuccessResponse().addKeyValuePair("token", userToken).build();
    }
}
