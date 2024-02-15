package org.acme.services;

import java.util.List;
import java.util.UUID;

import org.acme.model.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.UnexpectedTypeException;

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
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.apiKey = ?1", User.class);
        query.setParameter(1, apiKey);
        return query.getSingleResult();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public User createNewUser(User user) throws UnexpectedTypeException {
        try{
            user.setApiKey(UUID.randomUUID());
            user.setAccountActive(1);
            em.persist(user);
            return user;
        } catch (UnexpectedTypeException e) {
            return null;
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteUserByApiKey(UUID apiKey) {

        Query query = em.createQuery("UPDATE User u SET accountActive = :accountActive  WHERE u.apiKey = ?1");
        query.setParameter(1, apiKey);
        query.setParameter("accountActive", 0);
        query.executeUpdate();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void updateUserByApiKey(UUID apiKey, User user) {
        Query query = em.createQuery(
                "UPDATE User u SET firstName = :firstName, lastName = :lastName, email = :email WHERE apiKey = ?1");
        query.setParameter("firstName", user.getFirstName());
        query.setParameter("lastName", user.getLastName());
        query.setParameter("email", user.getEmail());
        query.setParameter(1, apiKey);
        query.executeUpdate();
    }

}
