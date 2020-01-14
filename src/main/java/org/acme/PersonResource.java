package org.acme;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.model.Person;

/**
 * PersonResource
 */
@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @POST
    @Transactional
    public Response createNew(Person person) {
        person.persist();
        return Response.ok(person).build();
    }
    
    @GET
    @Path("{id}")
    public Person getById(@PathParam("id") Long personId) {
        Optional<Person> optional = Person.findByIdOptional(personId);
        Person person = optional.orElseThrow(() -> new NotFoundException());
        return person;
    }

    @GET
    @Path("/alive")
    public Response getAlive() {
        return Response.ok(Person.findAlive()).build();
    }

}