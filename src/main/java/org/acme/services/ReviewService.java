package org.acme.services;

import java.util.List;

import org.acme.model.Review;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Transactional(Transactional.TxType.SUPPORTS)
@ApplicationScoped
@Named
public class ReviewService {
    
    @Inject
    EntityManager em;
    public List<Review> findAll() {    
        List<Review> reviews = em.createQuery("SELECT u FROM User u", Review.class).getResultList();
        return reviews;
    }
}
