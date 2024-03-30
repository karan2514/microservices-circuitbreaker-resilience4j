package com.adam.userservice;

import com.adam.userservice.external.services.RatingService;
import com.adam.userservice.model.Rating;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	private RatingService ratingService;
	@Test
	void contextLoads() {
	}

	@Test
	void createRating(){
		Rating rating = Rating.builder().rating(8).userId("").hotelId("").feedback("good hotel to stay").build();
		Rating SavedRating = ratingService.createRating(rating);
		System.out.println("new Rating Created");

	}

}
