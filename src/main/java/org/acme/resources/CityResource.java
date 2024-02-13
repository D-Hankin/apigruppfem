package org.acme.resources;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import org.acme.model.City;
import org.acme.services.CityService;
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

@Path("/api/city/{apiKey}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CityResource {

    @Inject
    CityService cityService;

    @Inject
    UserService userService;

    @GET
    @Operation
        (summary = "Show all the current cities",
        description = "Retrieve and show all the cities currently in the database.")
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
    @Operation(
        summary = "Create a city",
        description = "Enter cityName, country, description, population and imageUrl(String).\nPopulation values: Min value = 1,000, Max value = 100,000,000"
    )
    public Response createCity(@Valid City city, @PathParam("apiKey") UUID apiKey) throws URISyntaxException {
        
        if (userService.findUserByApiKey(apiKey) != null && cityService.findCityByName(city.getCityName()) == null) {
            city.setApiKey(apiKey);
            city = cityService.createCity(city);
            URI createdUri = new URI(city.getCityId().toString());
            return Response.created(createdUri).entity(city).build();
        } else {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED)
        .entity("This city already exists in the database")
        .build();
        }       
    }

    @DELETE
    @Operation(
        summary = "Delete a city",
        description = "Enter cityId & apiKey to delete a city"
    )
    @Path("/{cityName}")
    public Response deleteCity(@PathParam("apiKey") UUID apiKey, @PathParam("cityName") String cityName) {
        cityService.deleteByApiKey(apiKey, cityName);
        return Response.noContent().build();
    }

    @PATCH
    public City updateCity(@RequestBody City city, @PathParam("apiKey") UUID apiKey) {
        cityService.updateCityByApiKey(city, apiKey);

        return cityService.findCityByApiKeyAndCityName(city.getCityName(), apiKey);
    }

    @GET 
    @Path("/{cityName}")
    public City findSingleCity(@PathParam("apiKey") UUID apiKey, @PathParam("cityName") String cityName) {

        return cityService.findCityByApiKeyAndCityName(cityName, apiKey);
    }

    @GET
    @Path("/my-cities")
    public List<City> findMyCities(@PathParam("apiKey") UUID apiKey) {

        return cityService.findCityByApiKey(apiKey);
    }

    @GET
    @Path("/number-of-cities")
    public String cityCount(@PathParam("apiKey") UUID apiKey) {
        
        try {
            return Long.toString(cityService.countAllCities(apiKey));
        } catch (Exception e) {
            System.out.println("");
            return "You need a valid API key to see this information.";
        }
    }

    @GET
    @Path("/number-of-countries")
    public String countryCount(@PathParam("apiKey") UUID apiKey) {
        
        try {
            return Long.toString(cityService.countAllCountries(apiKey));
        } catch (Exception e) {
            return "You need a valid API key to see this information.";
        }
    }

    @GET
    @Path("/list-all-countries")
    public Response listOfCountries(@PathParam("apiKey") UUID apiKey) {
        
        try {
            return Response.ok(cityService.seeAllCountries(apiKey)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED)
        .entity("You need an API key to see this information.")
        .build();
        }
    }

    @GET
    @Path("/most-populace-cities")
    public Response mostPopulaceCities(@PathParam("apiKey") UUID apiKey) {
        try {
            return Response.ok(cityService.mostPopulaceCities(apiKey)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED)
        .entity("You need an API key to see this information.")
        .build();
        }
    }
}
                                

