package io.gupshup.workshop.chatgroup.authentication.impl;

import io.gupshup.workshop.chatgroup.authentication.Authenticator;
import lombok.extern.log4j.Log4j2;
import org.jvnet.hk2.annotations.Service;

import javax.ws.rs.container.ContainerRequestContext;

@Log4j2
@Service
public class PassThroughAuthenticator implements Authenticator {
    @Override
    public boolean isAuthenticated (ContainerRequestContext requestContext) {
        return true;
    }


    @Override
    public String getAuthenticationScheme () {
        return AUTH_SCHEME.PASS_THROUGH;
    }
}
