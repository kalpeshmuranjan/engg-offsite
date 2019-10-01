package io.gupshup.workshop.chatgroup.authentication;

import io.gupshup.workshop.chatgroup.constants.Constants;
import org.jvnet.hk2.annotations.Contract;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;

@Contract
public interface Authenticator {
    boolean isAuthenticated (ContainerRequestContext requestContext);

    String getAuthenticationScheme ();

    default String getResourceMethodName (ContainerRequestContext requestContext) {
        return requestContext.getMethod();
    }

    default void includeSecurityContext (ContainerRequestContext requestContext, String payload) {
        SecurityContext securityContext = requestContext.getSecurityContext();
        if (securityContext instanceof ApplicationSecurityContext) {
            ((ApplicationSecurityContext) securityContext).addAuthenticationPayload(payload, getAuthenticationScheme());
        } else {
            securityContext = new ApplicationSecurityContext(payload, getAuthenticationScheme());
        }
        requestContext.setSecurityContext(securityContext);
    }

    default String getAuthorizationHeaderToken (ContainerRequestContext requestContext) {
        return requestContext.getHeaderString(Constants.HeaderPropertyKeys.AUTHORIZATION_HEADER_KEY);
    }

    default String getRequestHeader (ContainerRequestContext requestContext, String headerKey) {
        return requestContext.getHeaderString(headerKey);
    }

    interface AUTH_SCHEME {
        String PASS_THROUGH  = "PASS_THROUGH";
        String BASIC         = "Basic"; // Change this and Magic, Everything will break.. Bwahaahahaha
        String API_KEY       = "API_KEY";
        String STRIPE_SECRET = "STRIPE_SECRET";
        String JWT           = "JWT";
    }
}
