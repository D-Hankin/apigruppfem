package org.acme.services;

import java.util.List;
import java.util.UUID;

import org.acme.model.Review;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
    public Review createNewReview (UUID apiKey, Review review) {
        try {
            if (userService.findUserByApiKey(apiKey) != null && userService.findUserByApiKey(review.getApiKey()) == userService.findUserByApiKey(apiKey)) {
                    em.persist(review);
                    return review;
                }
              }catch (NoResultException e) {
              return null;
          }    
          return null;
    }
}
