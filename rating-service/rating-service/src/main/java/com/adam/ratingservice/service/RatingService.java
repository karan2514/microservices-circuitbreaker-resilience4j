package com.adam.ratingservice.service;

import com.adam.ratingservice.model.Rating;

import java.util.List;

public interface RatingService {

    public Rating createRating(Rating rating);

    public List<Rating> getAllRating();

    public List<Rating> getRatingByUserId(String userId);

    public List<Rating> getRatingByHotelId(String hotelId);


}
