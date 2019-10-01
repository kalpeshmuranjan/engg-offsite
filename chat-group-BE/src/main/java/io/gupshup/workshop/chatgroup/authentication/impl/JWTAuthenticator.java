package io.gupshup.workshop.chatgroup.authentication.impl;

import io.gupshup.workshop.chatgroup.authentication.Authenticator;
import io.gupshup.workshop.chatgroup.services.JWTService;
import lombok.extern.log4j.Log4j2;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;

@Log4j2
@Service
public class JWTAuthenticator implements Authenticator {

    @Inject
    private JWTService jwtService;

    @Context
    private ResourceInfo resourceInfo;


    @Override
    public boolean isAuthenticated (ContainerRequestContext requestContext) {
        String jwtToken = getAuthorizationHeaderToken(requestContext);
        try {
            String payload = jwtService.verifyToken(jwtToken);
            if (payload == null) {
                return false;
            } else {
                includeSecurityContext(requestContext, payload);
                return true;
            }
        } catch (Exception e) {
            log.error("Invalid JWT Token Passed => {}", e.getMessage());
        }
        return false;
    }


    @Override
    public String getAuthenticationScheme () {
        return AUTH_SCHEME.JWT;
    }
}
