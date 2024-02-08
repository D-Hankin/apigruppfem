package org.acme.resources;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.util.List;

import org.acme.model.City;
import org.acme.services.CityService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/city/{apiKey}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CityResource {
    
    @Inject
    CityService cityService;

    @GET
    @Operation(summary = "Show all the current cities", description = "Retrieve and show all the cities currently in the database.")
    @APIResponse(
        responseCode = "204",
        description = "No cities currently in the database"
    )
    public Response getUsers() {

        List<City> cities = cityService.findAll();

        if (cities.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(cities).build();
    }
}
