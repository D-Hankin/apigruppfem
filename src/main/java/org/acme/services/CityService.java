package org.acme.services;

import java.util.List;
import java.util.UUID;

import org.acme.model.City;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
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
    public City createCity(City city) {
        em.persist(city);
        return city;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void delete(Long id) {
        em.remove(em.getReference(City.class, id));
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void updateCityByApiKey(City city, UUID apiKey) {
        Query query = em.createQuery(
                "UPDATE City c SET cityName = :cityName, country = :country, description = :description, imageUrl = :imageUrl, population = :population WHERE apiKey = ?1 AND cityName = ?2");
        query.setParameter("cityName", city.getCityName());
        query.setParameter("country", city.getCountry());
        query.setParameter("description", city.getDescription());
        query.setParameter("imageUrl", city.getImageUrl());
        query.setParameter("population", city.getPopulation());
        query.setParameter(1, apiKey);
        query.setParameter(2, city.getCityName());
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<City> findCityByApiKey(UUID apiKey) {
        System.out.println("here: " + apiKey);
        Query query = em.createQuery("SELECT c FROM City c WHERE c.apiKey = :apiKey");
        query.setParameter("apiKey", apiKey);
        
        return query.getResultList();
    }

    public City findCityByApiKeyAndCityName(String cityName, UUID apiKey) {

        TypedQuery<City> query = em.createQuery("SELECT c FROM City c WHERE c.apiKey = ?1 AND c.cityName = ?2", City.class);
        query.setParameter(1, apiKey);
        query.setParameter(2, cityName);

        return query.getSingleResult();
    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteByApiKey(UUID apiKey, String cityName) {
    
            Query query = em.createQuery("DELETE FROM City c WHERE c.apiKey = ?1 AND c.cityName = ?2");
            query.setParameter(1, apiKey);
            query.setParameter(2, cityName);
            query.executeUpdate();
        }

    public City findCityByName(String cityName) {

        try {
            TypedQuery<City> query = em.createQuery("SELECT c FROM City c WHERE c.cityName = ?1", City.class);
            query.setParameter(1, cityName);

            return query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
}
