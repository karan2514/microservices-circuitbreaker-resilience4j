package com.adam.userservice.service.Impl;

import com.adam.userservice.UserRepository;
import com.adam.userservice.exceptions.ResourceNotFoundException;
import com.adam.userservice.model.Hotel;
import com.adam.userservice.model.Rating;
import com.adam.userservice.model.User;
import com.adam.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
     RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;
    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found on the server !! " + userId));
        // rating service call
        Rating[] ratingOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + userId, Rating[].class);
        LOGGER.info("{}", ratingOfUser);

        List<Rating> ratings = Arrays.stream(ratingOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();
            LOGGER.info("response status code: {} ", forEntity.getStatusCode());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);


        return user;
    }


    @Override
    public List<User> getAllUser() {
        List<User> userList = userRepository.findAll();

        //LOGGER.info("{}",ratingOfUser);
      for (User users: userList){
          ArrayList<Rating> ratingOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/"+ users.getUserId(), ArrayList.class);

          users.setRatings(ratingOfUser);
      }

        return userList;
    }

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        return userRepository.save(user);
    }
}
