package org.acme.services;

import java.util.List;

import org.acme.model.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
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
}
