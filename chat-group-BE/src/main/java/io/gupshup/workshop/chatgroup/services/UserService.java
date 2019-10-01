package io.gupshup.workshop.chatgroup.services;

import io.gupshup.workshop.chatgroup.models.User;
import org.jvnet.hk2.annotations.Contract;

import javax.ws.rs.core.SecurityContext;

@Contract
public interface UserService {
    String generateToken (String userName);

    User getUserFromSecurityContext (SecurityContext securityContext);
}
