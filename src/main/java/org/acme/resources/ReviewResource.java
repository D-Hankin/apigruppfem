package org.acme.resources;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.List;
import java.util.UUID;

import org.acme.model.Review;
import org.acme.model.User;
import org.acme.services.ReviewService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/city/{apiKey}/my-reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReviewResource {

    @Inject
    ReviewService reviewService;

    @GET
    @Operation(summary = "Show all the current reviews", description = "Retrieve and show all the reviews currently in the database.")
    @APIResponse(
        responseCode = "204",
        description = "No reviews currently in the database"
    )
    public Response getReviews() {

        List<Review> reviews = reviewService.getReviewByUserId();

        if (reviews.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(reviews).build();
    }
}