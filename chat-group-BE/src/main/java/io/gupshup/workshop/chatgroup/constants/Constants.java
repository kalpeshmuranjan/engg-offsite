package io.gupshup.workshop.chatgroup.constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.gupshup.workshop.chatgroup.configuration.ConfigurationManager;
import io.gupshup.workshop.chatgroup.inject.ServiceDependencyResolver;

public interface Constants {
    String SERVICE_NAME        = "chatgroup";
    String DEFAULT_APP_PACKAGE = "io.gupshup.workshop";
    String APP_NAME            = "Chat-Group";
    Gson   GSON                = new GsonBuilder().serializeNulls().create();

    interface PropertyKeys {
        String APPLICATION_ENVIRONMENT = "application.environment";
        String JWT_SECRET              = "jwt.secret";
        String JWT_EXPIRY_DAYS         = "jwt.expiry.days";
        String JWT_ISSUER              = "jwt.issuer";
    }

    interface ENV {
        String DEV  = "dev";
        String QA   = "qa";
        String PROD = "prod";
    }

    static boolean isDevelopmentEnvironment () {
        return isEnvironment(ENV.DEV);
    }

    static boolean isQAEnvironment () {
        return isEnvironment(ENV.QA);
    }

    static boolean isProdEnvironment () {
        return isEnvironment(ENV.PROD);
    }

    static boolean isEnvironment (String environment) {
        ConfigurationManager conf = ServiceDependencyResolver.getDependency(ConfigurationManager.class);
        return conf.getServerEnvironment().equalsIgnoreCase(environment);
    }

    public interface ResponseValues {
        String SUCCESS         = "success";
        String ERROR           = "error";
        String SUCCESS_MESSAGE = "Request Completed";
        String ERROR_MESSAGE   = "Request Failed";
    }

    public interface JSONPropertyKeys {
        String STATUS  = "status";
        String MESSAGE = "message";
    }

    public interface HeaderPropertyKeys {
        String AUTHORIZATION_HEADER_KEY = "Authorization";
        String CONTENT_TYPE_HEADER      = "Content-Type";
    }
}
