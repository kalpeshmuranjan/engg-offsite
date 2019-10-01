package io.gupshup.workshop.chatgroup.inject;

import eu.infomas.annotation.AnnotationDetector;
import gov.va.oia.HK2Utilities.AnnotatedClasses;
import gov.va.oia.HK2Utilities.AnnotationReporter;
import gov.va.oia.HK2Utilities.HK2RuntimeInitializer;
import io.gupshup.workshop.chatgroup.constants.Constants;
import lombok.extern.log4j.Log4j2;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Log4j2
public class ServiceDependencyResolver implements ContainerLifecycleListener {

    private static ServiceLocator serviceLocator;


    public synchronized static <T> T getDependency (Class <T> clazz) {
        if (serviceLocator != null) {
            return serviceLocator.getService(clazz);
        }

        serviceLocator = createDefaultServiceLocator(Constants.SERVICE_NAME);
        return serviceLocator.getService(clazz);
    }


    public synchronized static <T> T getNamedDependency (Class <T> clazz, String name) {
        if (serviceLocator != null) {
            return serviceLocator.getService(clazz, name);
        }

        serviceLocator = createDefaultServiceLocator(Constants.SERVICE_NAME);
        return serviceLocator.getService(clazz, name);
    }


    public synchronized static ServiceLocator createDefaultServiceLocator (String name) {
        final String[] packageNames = {Constants.DEFAULT_APP_PACKAGE};
        try {
            return HK2RuntimeInitializer.init(name, true, packageNames);
        } catch (ClassNotFoundException | IOException e) {
            log.error("Error Occurred while creating Default Service Locator => ", e);
        }
        return ServiceLocatorUtilities.createAndPopulateServiceLocator(name);
    }


    public static ServiceLocator getServiceLocator () {
        return serviceLocator;
    }


    public static void setServiceLocator (ServiceLocator serviceLocator) {
        ServiceDependencyResolver.serviceLocator = serviceLocator;
    }


    public synchronized static Class <?>[] getClassesWithAnnotationFromAPackage (Class <? extends Annotation> annotation, String... packageNames) throws ClassNotFoundException {
        final AnnotatedClasses ac = new AnnotatedClasses();

        try {
            final AnnotationDetector cf = new AnnotationDetector(new AnnotationReporter(ac, new Class[]{annotation}));
            if (packageNames == null || packageNames.length == 0) {
                cf.detect();
            } else {
                cf.detect(packageNames);
            }
        } catch (final IOException e) {
            log.error("Error While Getting Classes with Annotation @Service => ", e);
        }
        return ac.getAnnotatedClasses();
    }


    @Override
    public void onStartup (Container container) {
        this.init(container.getApplicationHandler().getServiceLocator());
    }


    private void init (ServiceLocator sl) {
        serviceLocator = sl;
    }


    @Override
    public void onReload (Container container) {
        this.init(container.getApplicationHandler().getServiceLocator());
    }


    @Override
    public void onShutdown (Container container) {
    }


    private static Class <?>[] getAllClassesByPackage (String packageName) {
        final List <ClassLoader> classLoadersList = new LinkedList <>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        final Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName))));

        final Set <Class <?>> classes = reflections.getSubTypesOf(Object.class);
        return classes.toArray(new Class <?>[0]);
    }
}

