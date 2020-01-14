package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * AppLifecycleBean
 */
@ApplicationScoped
@Slf4j
public class AppLifecycleBean {

    @Inject
    LaunchMode mode;
    
    void onStart(@Observes StartupEvent ev) {               
        log.info("The application is starting in {} mode...", mode);
    }

    void onStop(@Observes ShutdownEvent ev) {               
        log.info("The application is stopping...");
    }
    
}