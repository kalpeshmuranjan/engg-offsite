package io.gupshup.workshop.chatgroup.services.impl;

import io.gupshup.workshop.chatgroup.authentication.ApplicationSecurityContext;
import io.gupshup.workshop.chatgroup.authentication.Authenticator;
import io.gupshup.workshop.chatgroup.constants.Constants;
import io.gupshup.workshop.chatgroup.models.User;
import io.gupshup.workshop.chatgroup.services.JWTService;
import io.gupshup.workshop.chatgroup.services.UserService;
import org.jvnet.hk2.annotations.Service;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.ws.rs.core.SecurityContext;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private JWTService jwtService;


    @Override
    public String generateToken (String userName) {
        User user = new User();
        user.userName(userName);
        return jwtService.generateToken(Constants.GSON.toJson(user));
    }


    @Nullable
    @Override
    public User getUserFromSecurityContext (SecurityContext securityContext) {
        ApplicationSecurityContext.AuthenticationPayload jwtAuthPayload = ApplicationSecurityContext.extractAuthenticationPayloadFromUserPrincipalAndScheme(securityContext.getUserPrincipal(), Authenticator.AUTH_SCHEME.JWT);
        if (jwtAuthPayload != null) {
            return Constants.GSON.fromJson(jwtAuthPayload.payload(), User.class);
        }
        return null;
    }
}
