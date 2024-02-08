package org.acme.resources;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.acme.services.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;

@Path("/api/city/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    
    @Inject
    UserService userService;

    
    
}
