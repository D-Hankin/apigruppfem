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
@Table(name = "t_review")
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewID;

    @ManyToOne
    @JoinColumn(name = "apiKey", referencedColumnName = "apiKey", insertable = false, updatable = false)
    private User user;
    private UUID apiKey;

    @ManyToOne
    @JoinColumn(name = "cityId", referencedColumnName = "cityId", insertable = false, updatable = false)
    private City city;

    private Long cityId;

    @NotEmpty
    @Min(value = 1, message = "The review can not be empty.")
    @Max(value = 200, message = "The review can't be more than 200 characters.")
    private String review;

    @NotEmpty
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be less than 5")
    private int rating;

    public Long getReviewID() {
        return reviewID;
    }

    public void setReviewID(Long reviewID) {
        this.reviewID = reviewID;
    }

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    

}
