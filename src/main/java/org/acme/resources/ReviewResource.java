package org.acme.resources;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.acme.services.ReviewService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;

@Path("/api/city/{apiKey}/my-reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReviewResource {

    @Inject
    ReviewService reviewService;

    
    
}
