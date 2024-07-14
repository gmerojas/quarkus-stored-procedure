package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/saludo")
public class GreetingResource {

    @GET
    @Path("hola")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hola";
    }

    @GET
    @Path("bye")
    @Produces(MediaType.TEXT_PLAIN)
    public String bye() {
        return "Chiaooo";
    }
}
