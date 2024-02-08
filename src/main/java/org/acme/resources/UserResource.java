package org.acme.resources;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.List;

import org.acme.model.User;
import org.acme.services.UserService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/city/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    
    @Inject
    UserService userService;

    @GET
    @Operation(summary = "Show all the current users", description = "Retrieve and show all the users currently in the database.")
    @APIResponse(
        responseCode = "204",
        description = "No user currently in the database"
    )
    public Response getUsers() {

        List<User> users = userService.findAll();

        if (users.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(users).build();
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{userId}")
    public Response getUserById(@PathParam("id") @Min(1) Long id) {

        User user = userService.find(id);

        return Response.ok(user).build();
    }
}
