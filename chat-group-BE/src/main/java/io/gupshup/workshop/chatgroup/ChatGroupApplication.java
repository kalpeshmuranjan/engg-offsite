package io.gupshup.workshop.chatgroup;

import io.gupshup.workshop.chatgroup.constants.Constants;
import io.gupshup.workshop.chatgroup.inject.ServiceDependencyResolver;
import lombok.extern.log4j.Log4j2;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.message.internal.TracingLogger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.jvnet.hk2.annotations.Service;

import javax.ws.rs.ApplicationPath;

@Log4j2
@ApplicationPath("rest")
public class ChatGroupApplication extends ResourceConfig {
    public ChatGroupApplication (ServiceLocator serviceLocator) {
        registerServices(serviceLocator);
        registerRoutes();
        registerServerProperties();
    }


    private void registerServerProperties () {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        property(ServerProperties.PROVIDER_SCANNING_RECURSIVE, true);
        property(ServerProperties.MOXY_JSON_FEATURE_DISABLE, true);
        property(ServerProperties.APPLICATION_NAME, Constants.APP_NAME);

        if (!Constants.isProdEnvironment()) {
            property(ServerProperties.TRACING, TracingConfig.ALL.name());
            property(ServerProperties.TRACING_THRESHOLD, TracingLogger.Level.TRACE.name());
            property(ServerProperties.MONITORING_STATISTICS_ENABLED, true);
            register(LoggingFeature.class);
        } else {
            property(ServerProperties.WADL_FEATURE_DISABLE, true);
            property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name());
            property(ServerProperties.TRACING_THRESHOLD, TracingLogger.Level.TRACE.name());
        }
    }


    private void registerRoutes () {
        this.packages(Constants.DEFAULT_APP_PACKAGE);
    }


    private void registerServices (ServiceLocator serviceLocator) {
        try {
            final Class <?>[] ac =
                    ServiceDependencyResolver.getClassesWithAnnotationFromAPackage(Service.class, Constants.DEFAULT_APP_PACKAGE);
            for (final ActiveDescriptor <?> ad : ServiceLocatorUtilities.addClasses(serviceLocator, ac)) {
                log.trace("Added {}", ad.toString());
            }
        } catch (final ClassNotFoundException e) {
            log.error(e);
        }
        ServiceDependencyResolver.setServiceLocator(serviceLocator);
        this.register(serviceLocator);
        register(MultiPartFeature.class);
    }
}
