package io.gupshup.workshop.chatgroup.configuration;

import io.gupshup.workshop.chatgroup.constants.Constants;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ConfigurationManager {
    <T> T getProperty (String key, Class <T> type);

    <T> T getProperty (String key, String defaultValue, Class <T> type);

    default String getServerEnvironment () {
        return getProperty(Constants.PropertyKeys.APPLICATION_ENVIRONMENT, String.class);
    }

    default String getJWTSecret () {
        return getProperty(Constants.PropertyKeys.JWT_SECRET, String.class);
    }

    default long getJWTExpiry () {
        return getProperty(Constants.PropertyKeys.JWT_EXPIRY_DAYS, Long.class);
    }

    default String getJWTIssuer () {
        return getProperty(Constants.PropertyKeys.JWT_ISSUER, String.class);
    }
}
