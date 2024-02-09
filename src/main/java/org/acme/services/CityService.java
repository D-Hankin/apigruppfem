package org.acme.services;

import java.util.List;
import java.util.UUID;

import org.acme.model.City;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Transactional(Transactional.TxType.SUPPORTS)
@ApplicationScoped
@Named
public class CityService {
    
    @Inject
    EntityManager em;
    public List<City> findAll() {
        List<City> cities = em.createQuery("SELECT c FROM City c", City.class).getResultList();
        return cities;
    }

    public Long countAll() {
        return em.createQuery("SELECT COUNT(c) FROM City c", Long.class).getSingleResult(); 
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public City create(City city) {
        em.persist(city);
        return city;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void delete(Long id) {
        em.remove(em.getReference(City.class, id));
    }

    // @Transactional(Transactional.TxType.REQUIRED)
    // public void update(City city) {
    //     em.refresh(city);
    // }
}
