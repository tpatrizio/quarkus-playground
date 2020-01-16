package org.acme;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.vertx.ConsumeEvent;

/**
 * GreetingService
 */
@ApplicationScoped
public class GreetingService {

    @ConfigProperty(name = "greeting.message") 
    String message;
    
    @ConsumeEvent("greeting")
    public String greeting(String name) {
        return String.format("%s %s!", message, name);
    }

}