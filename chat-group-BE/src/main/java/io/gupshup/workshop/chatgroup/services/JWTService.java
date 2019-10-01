package io.gupshup.workshop.chatgroup.services;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface JWTService {
    String generateToken (String payLoad);

    String verifyToken (String token);
}