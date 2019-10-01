package io.gupshup.workshop.chatgroup.filters;

import io.gupshup.workshop.chatgroup.annotations.Authenticated;
import io.gupshup.workshop.chatgroup.authentication.Authenticator;
import io.gupshup.workshop.chatgroup.inject.ServiceDependencyResolver;
import io.gupshup.workshop.chatgroup.responses.ErrorResponse;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;

@Log4j2
@Provider
@Authenticated
@SuppressWarnings("ALL")
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;


    @Override
    public void filter (ContainerRequestContext containerRequestContext) {
        Method method = resourceInfo.getResourceMethod();
        Class  clazz  = resourceInfo.getResourceClass();
        if (method.isAnnotationPresent(Authenticated.class) || clazz.isAnnotationPresent(Authenticated.class)) {
            Authenticated authenticatedAnnotation = method.getAnnotation(Authenticated.class);
            if (authenticatedAnnotation == null) {
                authenticatedAnnotation = (Authenticated) clazz.getAnnotation(Authenticated.class);
            }
            if (authenticatedAnnotation != null) {
                Class <? extends Authenticator> authenticatorClass = authenticatedAnnotation.by();

                Authenticator authenticator = ServiceDependencyResolver.getDependency(authenticatorClass);
                if (!authenticator.isAuthenticated(containerRequestContext)) {
                    if (!authenticator.getAuthenticationScheme().equals(Authenticator.AUTH_SCHEME.BASIC)) {
                        containerRequestContext.abortWith(new ErrorResponse(Response.Status.UNAUTHORIZED, "Authentication Failed").build());
                    } else {
                        Response response = Response.status(Response.Status.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"User Visible Realm\", charset=\"UTF-8\"").build();
                        containerRequestContext.abortWith(response);
                    }
                    return;
                }
            }
        }
    }


    private boolean getAuthenticatorResult (Class <? extends Authenticator> anAuthenticatorClass, ContainerRequestContext containerRequestContext) {
        if (anAuthenticatorClass != null) {
            Authenticator authenticator = ServiceDependencyResolver.getDependency(anAuthenticatorClass);
            return authenticator.isAuthenticated(containerRequestContext);
        } else {
            log.fatal("Authenticator Class {} Not Found By Authenticated Annotation Present in Method {} and Route {}", anAuthenticatorClass, containerRequestContext.getMethod(), resourceInfo.getResourceClass());
            return false;
        }
    }
}
