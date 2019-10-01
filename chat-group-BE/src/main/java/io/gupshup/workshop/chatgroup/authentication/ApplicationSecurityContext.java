package io.gupshup.workshop.chatgroup.authentication;

import com.google.gson.reflect.TypeToken;
import io.gupshup.workshop.chatgroup.constants.Constants;
import lombok.Data;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationSecurityContext implements SecurityContext {

    private List <AuthenticationPayload> authenticationPayloads;

    @Data
    public static class AuthenticationPayload {
        private String payload;
        private String authenticationScheme;
    }


    ApplicationSecurityContext (String payload, String authenticationScheme) {
        authenticationPayloads = new ArrayList <>();
        addAuthenticationPayload(payload, authenticationScheme);
    }


    public void addAuthenticationPayload (String payload, String authenticationScheme) {
        authenticationPayloads.add(new AuthenticationPayload().payload(payload).authenticationScheme(authenticationScheme));
    }


    @Override
    public Principal getUserPrincipal () {
        return () -> Constants.GSON.toJson(authenticationPayloads);
    }


    @Override
    public boolean isUserInRole (String role) {
        return false;
    }


    @Override
    public boolean isSecure () {
        return false;
    }


    @Override
    public String getAuthenticationScheme () {
        return authenticationPayloads.stream().map(authPayload -> authPayload.authenticationScheme).collect(Collectors.joining(","));
    }


    public static AuthenticationPayload extractAuthenticationPayloadFromUserPrincipalAndScheme (Principal userPrincipal, String scheme) {
        List <AuthenticationPayload> incoming = Constants.GSON.fromJson(userPrincipal.getName(), new TypeToken <ArrayList <AuthenticationPayload>>() {
        }.getType());
        return incoming.stream().filter(authenticationPayload -> authenticationPayload.authenticationScheme().equals(scheme)).findFirst().orElse(null);
    }
}