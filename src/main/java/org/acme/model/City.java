package org.acme.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "t_city")
public class City {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;
    
    @ManyToOne
    @JoinColumn(name = "apiKey", referencedColumnName = "apiKey", insertable = false, updatable = false)
    private User user;
    
    private UUID apiKey;

    @NotEmpty
    private String cityName;

    
    @NotEmpty
    private String country;
    
    @NotEmpty
    private String description;
    
    @Min(value = 1000, message = "Value must be at least 1000")
    @Max(value = 100000000, message = "value must be less than 100,000,000")
    private int population;
    
    @NotEmpty
    private String imageUrl;
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public UUID getApiKey() {
        return apiKey;
    }
    
    public void setApiKey(UUID apiKey) {
        this.apiKey = apiKey;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
