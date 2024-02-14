package org.acme.resources;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import org.acme.model.User;
import org.acme.services.UserService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @GET
    @Operation(summary = "Show all the current users", description = "Retrieve and show all the users currently in the database.")
    @APIResponse(responseCode = "204", description = "No user currently in the database")
    @APIResponse(responseCode = "200", description = "Funkar detta?!?!"

    )
    public Response getUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(users).build();
    }

    @GET
    @Operation(summary = "Show all current users", description = "Retrieve and show all the users currently in the database.\"")
    @Path("/{apiKey}")
    public Response getUserById(@PathParam("apiKey") UUID apiKey) {
        User user = userService.findUserByApiKey(apiKey);
        return Response.ok(user).build();
    }

    @POST
    @Operation(summary = "Create a user", description = "Enter firstName, lastName and email")
    @Path("/create-user")
    public Response createUser(@Valid User user) throws URISyntaxException {
        user = userService.createNewUser(user);
        URI createdUri = new URI(user.getUserId().toString());
        return Response.created(createdUri).entity(user).build();
    }

    @PATCH
    @Operation(summary = "Delete a user (soft).", description = "Enter the API key to deactivate it.")
    @Path("/{apiKey}/deactivate-apikey")
    public Response deleteUser(@PathParam("apiKey") UUID apiKey) {
        userService.deleteUserByApiKey(apiKey);
        return Response.ok().entity("The API key has been deactivated!").build();
    }

    @PATCH
    @Operation(summary = "Update a user", description = "Enter apiKey and the values you want to change to update the user.")
    @Path("/{apiKey}")
    public User updateUser(@RequestBody User user, @PathParam("apiKey") UUID apiKey) {
        userService.updateUserByApiKey(apiKey, user);
        return userService.findUserByApiKey(apiKey);
    }

}
