package org.acme;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.reactivestreams.Publisher;

import io.vertx.axle.core.Vertx;


@Path("/greeting")
public class GreetingResource {

    @Inject
    Vertx vertx;

    @ConfigProperty(name = "greeting.message") 
    String message;

    @ConfigProperty(name = "greeting.suffix", defaultValue="!") 
    String suffix;

    @ConfigProperty(name = "greeting.name")
    Optional<String> name;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{name}")
    public CompletionStage<String>  hello(@PathParam("name") String name) {
        CompletableFuture<String> future = new CompletableFuture<>();
        long start = System.nanoTime();
        vertx.setTimer(10, l -> {
            // Compute elapsed time in milliseconds
            long duration = TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
        
            // Format message
            String message = String.format("Hello %s! (%d ms)%n", name, duration);
        
            // Complete
            future.complete(message);
        });
        return future;
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @Path("{name}/streaming")
    public Publisher<String> greeting(@PathParam("name") String name) {
        return vertx.periodicStream(2000).toPublisherBuilder()
            .map(l -> String.format("Hello %s! (%s)%n", name, new Date()))
            .buildRs();
    }

}