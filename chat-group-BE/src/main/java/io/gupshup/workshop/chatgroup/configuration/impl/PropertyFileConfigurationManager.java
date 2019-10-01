package io.gupshup.workshop.chatgroup.configuration.impl;

import com.github.drapostolos.typeparser.TypeParser;
import io.gupshup.workshop.chatgroup.configuration.ConfigurationManager;
import lombok.extern.log4j.Log4j2;
import org.jvnet.hk2.annotations.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Properties;


@Log4j2
@Service
public class PropertyFileConfigurationManager implements ConfigurationManager {

    private static final TypeParser TYPE_PARSER = TypeParser.newBuilder().build();

    private Properties properties;


    @PostConstruct
    private void onInit () throws InstantiationException {
        this.properties = new Properties();
        try {

            this.properties
                    .load(PropertyFileConfigurationManager.class.getClassLoader().getResourceAsStream(System.getProperty("config.file.forStage", "config.properties")));
        } catch (final IOException e) {
            log.error(
                    "Unable To Find config.properties in project classpath. Please Add the same. Exiting");
            throw new InstantiationException(e.getMessage());
        }
    }


    @PreDestroy
    private void onDestroy () {
        log.trace("Cleaning Properties... Closing Down Configurator");
        properties.clear();
    }


    @Override
    public <T> T getProperty (String key, Class <T> type) {
        return TYPE_PARSER.parse(properties.getProperty(key), type);
    }


    @Override
    public <T> T getProperty (String key, String defaultValue, Class <T> type) {
        return TYPE_PARSER.parse(properties.getProperty(key, defaultValue), type);
    }
}
