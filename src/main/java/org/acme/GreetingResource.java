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
import io.vertx.axle.core.eventbus.EventBus;
import io.vertx.axle.core.eventbus.Message;


@Path("/greeting")
public class GreetingResource {

    @Inject
    EventBus bus;

    @Inject
    Vertx vertx;

    @ConfigProperty(name = "greeting.message") 
    String message;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{name}")
    public CompletionStage<String> hello(@PathParam("name") String name) {
        CompletableFuture<String> future = new CompletableFuture<>();
        long start = System.nanoTime();
        vertx.setTimer(10, l -> {
            // Compute elapsed time in milliseconds
            long duration = TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
        
            // Format message
            String greeting = String.format("%s %s! (%d ms)%n", message, name, duration);
        
            // Complete
            future.complete(greeting);
        });
        return future;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{name}/async")
    public CompletionStage<String> helloAsync(@PathParam("name") String name) {
        return bus.<String>request("greeting", name).thenApply(Message::body);
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @Path("{name}/streaming")
    public Publisher<String> greeting(@PathParam("name") String name) {
        return vertx.periodicStream(2000).toPublisherBuilder()
            .map(l -> String.format("%s %s! (%s)%n", message, name, new Date()))
            .buildRs();
    }

}