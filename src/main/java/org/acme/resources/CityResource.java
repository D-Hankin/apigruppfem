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
    @Operation(summary = "Show all the current cities", description = "Enter API-key to the URL to retrieve and show all the cities currently in the database.")
    @APIResponse(responseCode = "204", description = "No cities currently in the database")
    public Response getCities() {

        List<City> cities = cityService.findAll();

        if (cities.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(cities).build();
    }

    @POST
    @Operation(summary = "Create a city", description = "Enter API-key to the URL. Enter cityName, country, description, population and imageUrl(String) to the request body.\nPopulation values: Min value = 1,000, Max value = 100,000,000")
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
    @Operation(summary = "Delete a city", description = "Enter cityId & the city creator API-key to the URL to delete a city")
    @Path("/{cityName}")
    public Response deleteCity(@PathParam("apiKey") UUID apiKey, @PathParam("cityName") String cityName) {
        cityService.deleteByApiKey(apiKey, cityName);
        return Response.noContent().build();
    }

    @Operation(summary = "Update a City", description = "Enter API-key to the URL. Enter cityName, country, description, imageUrl and population to the request body")
    @PATCH
    public City updateCity(@RequestBody City city, @PathParam("apiKey") UUID apiKey) {
        cityService.updateCityByApiKey(city, apiKey);

        return cityService.findCityByApiKeyAndCityName(city.getCityName(), apiKey);
    }

    @Operation(summary = "Finds a single city", description = "Enter API-key and cityName to the URL to show the city ")
    @GET
    @Path("/{cityName}")
    public City findSingleCity(@PathParam("apiKey") UUID apiKey, @PathParam("cityName") String cityName) {

        return cityService.findCityByApiKeyAndCityName(cityName, apiKey);
    }

    @Operation(summary = "Shows your created cities", description = "Enter API-key to the URL to show your created cities")
    @GET
    @Path("/my-cities")
    public List<City> findMyCities(@PathParam("apiKey") UUID apiKey) {

        return cityService.findCityByApiKey(apiKey);
    }

    @Operation(summary = "Show how many cities that are in the database", description = "Enter API-key to the URL to show how many cities that are in the database")
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
    @Operation(summary = "Show how many countries that are in the database", description = "Enter API-key to the URL to show how many countries that are in the database")
    @Path("/number-of-countries")
    public String countryCount(@PathParam("apiKey") UUID apiKey) {

        try {
            return Long.toString(cityService.countAllCountries(apiKey));
        } catch (Exception e) {
            return "You need a valid API key to see this information.";
        }
    }

    @GET
    @Operation(summary = "Shows a list of all countries in the database", description = "Enter API-key to the URL to show a list off all countries")
    @Path("/list-all-countries")
    public Response listOfCountries(@PathParam("apiKey") UUID apiKey) {

        try {
            List<String> countries = cityService.seeAllCountries(apiKey);
            if (countries != null) {
                return Response.ok(countries).build();
            } else {
                return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                        .entity("You need an API key to see this information.")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                    .entity("You need an API key to see this information.")
                    .build();
        }
    }

    @GET
    @Operation(summary = "Show the ten most populated cities ", description = "Enter API-key to the URL to show the ten most populated cities")
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

    @GET
    @Operation(summary = "Show the ten least populated cities", description = "Enter API-key to the URL to show the ten least populated cities")
    @Path("/least-populace-cities")
    public Response leastPopulaceCities(@PathParam("apiKey") UUID apiKey) {
        try {
            return Response.ok(cityService.leastPopulaceCities(apiKey)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                    .entity("You need an API key to see this information.")
                    .build();
        }
    }

    @GET
    @Operation(summary = "Shows a random city", description = "Enter API-key to the URL to show a random city")
    @Path("/random-city")
    public Response randomCity(@PathParam("apiKey") UUID apiKey) {
        try {
            return Response.ok(cityService.randomCity(apiKey)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                    .entity("You need an API key to see this information.")
                    .build();
        }
    }
}
