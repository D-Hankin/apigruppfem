package org.acme.services;

import java.util.List;

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
}
