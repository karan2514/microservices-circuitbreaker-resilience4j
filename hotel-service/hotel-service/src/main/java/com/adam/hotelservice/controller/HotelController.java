package com.adam.hotelservice.controller;

import com.adam.hotelservice.model.Hotel;
import com.adam.hotelservice.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        Hotel savedHotel = hotelService.saveHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotel);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getSingleHotel(@PathVariable(name = "hotelId") String hotelId){
        Hotel hotel = hotelService.getHotel(hotelId);
        return ResponseEntity.ok().body(hotel);
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getSingleHotel(){
        List<Hotel> hotel = hotelService.getAllHotels();
        return ResponseEntity.ok().body(hotel);
    }
}
