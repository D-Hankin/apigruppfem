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
import jakarta.ws.rs.core.Response;

@Transactional(Transactional.TxType.SUPPORTS)
@ApplicationScoped
@Named
public class CityService {

    @Inject
    EntityManager em;

    @Inject
    UserService userService;

    public List<City> findAll(UUID apiKey) {
        if (userService.findUserByApiKey(apiKey) != null) {
            List<City> cities = em.createQuery("SELECT c FROM City c", City.class).getResultList();
            for (City city : cities) {
                city.getUser().setApiKey(null);
                city.setApiKey(null);
            }
            return cities;
        } else {
            return null;
        }

    }

    public Long countAllCities(UUID apiKey) {

        if (userService.findUserByApiKey(apiKey) != null) {
            return em.createQuery("SELECT COUNT(c) FROM City c", Long.class).getSingleResult();

        } else {
            return null;
        }
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

        Query query = em.createQuery("SELECT c FROM City c WHERE c.apiKey = :apiKey", City.class);
        query.setParameter("apiKey", apiKey);

        return query.getResultList();
    }

    public City findCityByApiKeyAndCityName(String cityName, UUID apiKey) {

        TypedQuery<City> query = em.createQuery("SELECT c FROM City c WHERE c.apiKey = ?1 AND c.cityName = ?2",
                City.class);
        query.setParameter(1, apiKey);
        query.setParameter(2, cityName);

        City city = query.getSingleResult();
        city.setApiKey(null);
        city.getUser().setApiKey(null);

        return city;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Response deleteByApiKey(UUID apiKey, String cityName) {

        Query selectQuery = em.createQuery("SELECT c FROM City c WHERE c.cityName = :cityName");
        selectQuery.setParameter("cityName", cityName);
        City city = (City) selectQuery.getSingleResult();

        Query deleteQuery = em.createQuery("DELETE FROM Review r WHERE r.cityId = :cityId");
        deleteQuery.setParameter("cityId", city.getCityId());
        deleteQuery.executeUpdate();

        Query query = em.createQuery("DELETE FROM City c WHERE c.apiKey = ?1 AND c.cityName = ?2");
        query.setParameter(1, apiKey);
        query.setParameter(2, cityName);
        int result = query.executeUpdate();

        if (result == 1) {
            return Response.ok().entity(cityName + " has been deleted.").build();
        } else {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED)
            .entity("The city must be connected to your API key for you to delete it.")
            .build();
        }  
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

    public Long countAllCountries(UUID apiKey) {

        if (userService.findUserByApiKey(apiKey) != null
                && userService.findUserByApiKey(apiKey).getAccountActive() == 1) {

            return em.createQuery("SELECT COUNT(DISTINCT country) FROM City c", Long.class).getSingleResult();

        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> seeAllCountries(UUID apiKey) {

        if (userService.findUserByApiKey(apiKey) != null
                && userService.findUserByApiKey(apiKey).getAccountActive() == 1) {

            return em.createQuery("SELECT DISTINCT country FROM City c").getResultList();

        } else {
            return null;
        }

    }

    public List<City> tenMostPopulaceCities(UUID apiKey) {
        if (userService.findUserByApiKey(apiKey) != null
        && userService.findUserByApiKey(apiKey).getAccountActive() == 1) {
            
            return em.createQuery("SELECT c FROM City c ORDER BY c.population DESC", City.class)
                     .setMaxResults(10)
                     .getResultList();

        } else {
            return null;
        }
    }

    public List<City> tenLeastPopulaceCities(UUID apiKey) {
        if (userService.findUserByApiKey(apiKey) != null
                && userService.findUserByApiKey(apiKey).getAccountActive() == 1) {

                    return em.createQuery("SELECT c FROM City c ORDER BY c.population", City.class)
                    .setMaxResults(10)
                    .getResultList();

        } else {
            return null;
        }
    }

    public List<City> randomCity(UUID apiKey) {
        if (userService.findUserByApiKey(apiKey) != null
                && userService.findUserByApiKey(apiKey).getAccountActive() == 1) {

            return em.createQuery("SELECT c FROM City c ORDER BY RANDOM() LIMIT 1", City.class).getResultList();

        } else {
            return null;
        }
    }
}
