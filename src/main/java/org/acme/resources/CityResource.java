package org.acme.resources;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.acme.model.City;
import org.acme.services.CityService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/city")
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
    public Response getCities() {

        List<City> cities = cityService.findAll();

        if (cities.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(cities).build();
    }
    
    @POST
    public Response createCity(@Valid City city) throws URISyntaxException {
        city = cityService.create(city);
        URI createdUri = new URI(city.getCityId().toString());
        return Response.created(createdUri).entity(city).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCity(@PathParam("id")@Min(1)Long id) {
        cityService.delete(id);
        return Response.noContent().build();
    }

    // @PATCH
    // @Path("/{id}")
    // public Response updateCity(@PathParam("cityName") City city) {
    //     cityService.update(city.setCityName(City));
    //     return Response.accepted(city).entity(cityService).build();
    // }
}
                                

