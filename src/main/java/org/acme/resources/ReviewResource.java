package org.acme.resources;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.List;
import java.util.UUID;

import org.acme.model.Review;
import org.acme.services.ReviewService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.spi.UnhandledException;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/city/{apiKey}/my-reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReviewResource {

    @Inject
    ReviewService reviewService;

    @GET
    @Operation(summary = "Show all the current reviews for a user", description = "Enter API-key to the URL to retrieve and show all the reviews currently in the database connected to a specific api key.")
    @APIResponse(responseCode = "204", description = "No reviews currently in the database connected to that api key.")
    public Response getReviews(@PathParam("apiKey") UUID apiKey) {

        List<Review> reviews = reviewService.getReviewByApiKey(apiKey);

        if (reviews.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(reviews).build();
    }

    @POST
    @Operation(summary = "Submits a review of a city", description = "Enter API-key to the URL to submit a review of a city")
    @Path("/submit-review")
    public Response submitReview(@PathParam("apiKey") UUID apiKey, @RequestBody Review review) {
        if (reviewService.createNewReview(apiKey, review) != null) {
            return Response.ok(review).build();
        } else {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                    .entity("Invalid APIKEY")
                    .build();
        }
    }

    @GET
    @Operation(summary = "Shows ratings of a city", description = "Enter API-key and a rating to the URL to show all reviews of that city with that rating")
    @Path("/{cityName}/{rating}")
    public Response getReviewsByRating(@PathParam("apiKey") UUID apiKey, @PathParam("cityName") String cityName,
            @PathParam("rating") int rating) {
        List<Review> reviews = reviewService.reviewsByRating(apiKey, cityName, rating);
        return Response.ok(reviews).build();
    }

    @DELETE
    @Operation(summary = "Deletes all reviews from the API holder", description = "Enter API-key to the URL to delete all reviews associated to that API key")
    public Response deleteAllReviews(@PathParam("apiKey") UUID apiKey) {

        try {
            reviewService.deleteAllReviewsByApiKey(apiKey);
            return Response.ok().build();
        } catch (UnhandledException e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Delete not executed").build();
        }

    }

    @DELETE
    @Operation(summary = "Delete a review", description = "Enter the review creators API-key and the reviewId to the URL to delete the review ")
    @Path("/{reviewId}")
    public Response deleteSingleReview(@PathParam("apiKey") UUID apiKey, @PathParam("reviewId") Long reviewId) {
        try {
            boolean success = reviewService.deleteSingleReviewByReviewId(apiKey, reviewId);
            if (success == true) {
                return Response.ok().entity("You have successfully deleted the review.").build();
            } else {
                return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Delete not executed").build();
            }

        } catch (UnhandledException e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Delete not executed").build();
        }
    }

    @PATCH
    @Operation(summary = "Edit a review", description = "Enter the review creators API-key to the URL. Enter reviewId, review and rating to the request body")
    public Response editReview(@PathParam("apiKey") UUID apiKey, @RequestBody Review review) {

        try {
            reviewService.editSingleReview(apiKey, review);

            Review updatedReview = reviewService.findReviewByReviewId(review.getReviewId());
            updatedReview.getUser().setApiKey(null);
            updatedReview.getCity().getUser().setApiKey(null);
            updatedReview.setApiKey(null);
            updatedReview.getCity().setApiKey(null);

            return Response.ok(updatedReview).build();

        } catch (UnhandledException e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Edit not executed").build();
        }
    }
}