package io.gupshup.workshop.chatgroup.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import io.gupshup.workshop.chatgroup.configuration.ConfigurationManager;
import io.gupshup.workshop.chatgroup.services.JWTService;
import lombok.extern.log4j.Log4j2;
import org.jvnet.hk2.annotations.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class JWTServiceImpl implements JWTService {

    private static final String KEY_PAYLOAD = "payload";

    @Inject
    private ConfigurationManager configurator;

    private Algorithm algorithm;


    @PostConstruct
    private void setAlgorithm () {
        algorithm = Algorithm.HMAC256(configurator.getJWTSecret());
    }


    @Override
    public String generateToken (String payload) {
        long expiry       = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(configurator.getJWTExpiry());
        Date expiryAsDate = new Date(expiry);
        return JWT.create().withClaim(KEY_PAYLOAD, payload).withIssuedAt(new Date()).withExpiresAt(expiryAsDate).withIssuer(configurator.getJWTIssuer()).sign(algorithm);
    }


    @Override
    public String verifyToken (String token) {
        try {
            JWT.require(algorithm).withIssuer(configurator.getJWTIssuer()).build().verify(token);
            Claim payload = JWT.decode(token).getClaim(KEY_PAYLOAD);
            return payload.isNull() ? null : payload.asString();
        } catch (JWTVerificationException e) {
            log.error("JWT TOKEN VERIFICATION FAILED", e);
        }
        return null;
    }
}
