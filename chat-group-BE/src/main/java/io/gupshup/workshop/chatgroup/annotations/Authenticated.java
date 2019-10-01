package io.gupshup.workshop.chatgroup.annotations;

import io.gupshup.workshop.chatgroup.authentication.Authenticator;
import io.gupshup.workshop.chatgroup.authentication.impl.PassThroughAuthenticator;

import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Authenticated {
    Class <? extends Authenticator> by () default PassThroughAuthenticator.class;

}