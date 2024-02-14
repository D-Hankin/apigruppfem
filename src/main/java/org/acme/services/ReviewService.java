package org.acme.services;

import java.util.List;
import java.util.UUID;

import org.acme.model.City;
import org.acme.model.Review;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Transactional(Transactional.TxType.SUPPORTS)
@ApplicationScoped
@Named
public class ReviewService {

    @Inject
    EntityManager em;
    @Inject
    UserService userService;

    public List<Review> getReviewByApiKey(UUID apiKey) {
        List<Review> reviews = em.createQuery("SELECT r FROM Review r WHERE r.apiKey = ?1", Review.class)
                .setParameter(1, apiKey)
                .getResultList();

        for (Review review : reviews) {
            review.getCity().getUser().setApiKey(null);
            review.getCity().setApiKey(null);
        }

        return reviews;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Review createNewReview(UUID apiKey, Review review) {
        try {
            if (userService.findUserByApiKey(apiKey) != null
                    && userService.findUserByApiKey(review.getApiKey()) == userService.findUserByApiKey(apiKey)) {
                em.persist(review);
                return review;
            }
        } catch (NoResultException e) {
            return null;
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteAllReviewsByApiKey(UUID apiKey) {
        System.out.println("Jag är i Service");
        Query deleteQuery = em.createQuery("DELETE FROM Review r WHERE r.apiKey = :apiKey");
        deleteQuery.setParameter("apiKey", apiKey);
        deleteQuery.executeUpdate();
    }

    public List<Review> reviewsByRating(UUID apiKey, String cityName, int rating) {
        try {
            Query selectQuery = em.createQuery("SELECT c FROM City c WHERE c.cityName = :cityName");
            selectQuery.setParameter("cityName", cityName);
            City city = (City) selectQuery.getSingleResult();

            if (userService.findUserByApiKey(apiKey) != null) {
                List<Review> reviews = em
                        .createQuery("SELECT r FROM Review r WHERE r.rating = :rating AND r.cityId = :cityId")
                        .setParameter("cityId", city.getCityId())
                        .setParameter("rating", rating).getResultList();
                for (Review review : reviews) {
                    review.getUser().setApiKey(null);
                    review.getCity().getUser().setApiKey(null);
                    review.setApiKey(null);
                    review.getCity().setApiKey(null);

                }
                return reviews;
            } else {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        }
    }
}
