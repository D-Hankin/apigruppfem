package org.acme.services;

import java.util.List;
import java.util.UUID;

import org.acme.model.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Transactional(Transactional.TxType.SUPPORTS)
@ApplicationScoped
@Named
public class UserService {
    
    @Inject
    EntityManager em;

    public List<User> findAll() {
        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        return users;
    }

    public User findUserByApiKey(UUID apiKey) {
        TypedQuery <User> query = em.createQuery("SELECT u FROM User u WHERE u.apiKey = ?1", User.class);
        query.setParameter(1, apiKey);
        return query.getSingleResult();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public User createNewUser(User user) {
        user.setApiKey(UUID.randomUUID());
        em.persist(user);
        return user;
    }
}
