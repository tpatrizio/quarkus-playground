package org.acme.config;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Size;

import io.quarkus.arc.config.ConfigProperties;

/**
 * GreetingConfiguration
 */
@ConfigProperties(prefix = "greeting")
public class GreetingConfiguration {

    @Size(min = 4)
    public String message;
    public String suffix = "!"; 

    public Optional<String> name;
    public HiddenConfig hidden; 

    public static class HiddenConfig {
        public Integer prizeAmount;
        public List<String> recipients;
    }
    
}