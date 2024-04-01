package com.adam.userservice.controller;

import com.adam.userservice.external.services.RatingService;
import com.adam.userservice.model.Rating;
import com.adam.userservice.model.User;
import com.adam.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    int retryCount = 1;
    @GetMapping("/{userId}")
   // @CircuitBreaker(name = "ratingHotelBreaker" , fallbackMethod = "ratingHotelFallback")
    //@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUserById(@PathVariable(name = "userId") String userId){
        LOGGER.info("Retry Count:{}", retryCount);
        retryCount++;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
        LOGGER.info("Fallback method executed because Rating service is down",ex.getMessage());
        User user = User.builder()
                .userId("1234")
                .email("dummyuser@yopmail.com")
                .name("dummy")
                .about("this dummy user is created because some service is down")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.getAllUser();
        return ResponseEntity.ok(allUsers);
    }

    /*@PostMapping("/user/rating")
    public Rating createRatingFromUser(@RequestBody Rating rating){
        return ratingService.createRating(rating);
    }*/
}
