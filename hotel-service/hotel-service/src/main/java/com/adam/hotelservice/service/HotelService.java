package com.adam.hotelservice.service;

import com.adam.hotelservice.model.Hotel;

import java.util.List;

public interface HotelService {

   public Hotel saveHotel(Hotel hotel);

   public Hotel getHotel(String id);

   public List<Hotel> getAllHotels();

}
