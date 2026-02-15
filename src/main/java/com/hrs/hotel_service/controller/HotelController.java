package com.hrs.hotel_service.controller;

import com.hrs.hotel_service.entity.Hotel;
import com.hrs.hotel_service.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/hotel")
public class HotelController {

    private static final Logger logger = LoggerFactory.getLogger(HotelController.class);
    @Autowired
    HotelService hotelService;

    @PostMapping
    ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotelRequest) {
        logger.info("inside save Hotel: " + hotelRequest.getName());
        Hotel Hotel = hotelService.create(hotelRequest);
        logger.info("Hotel saved: " + Hotel.getHotelId());
        return ResponseEntity.ok(Hotel);
    }

    @GetMapping("all")
    public ResponseEntity<?> fetchAllHotels() {
        logger.info("inside fetch all Hotel");
        return ResponseEntity.ok(hotelService.fetchAllHotels());
    }

    @GetMapping("/{id}")
    ResponseEntity<Hotel> getHotel(@PathVariable String id) {
        logger.info("inside fetch Hotel: " + id);
        Optional<Hotel> hotel = hotelService.getHotel(id);
       /* logger.info("Hotel fetched: " +
                (Hotel.isPresent() ? Hotel.get().getHotelId() : "Hotel doesnot exist"));*/
        if (hotel.isPresent()) {
            logger.info("Hotel fetched");
            return ResponseEntity.ok(hotel.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Hotel> available(
            @PathVariable String id) {
        Optional<Hotel> hotel = hotelService.getHotel(id);
        if (hotel.isPresent() && hotel.get().getAvailableRooms() > 0) {
            logger.info("Rooms available: " + hotel.get().getAvailableRooms());
            return ResponseEntity.ok(hotel.get());
        } else {
            logger.info("Rooms not available");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/internal/{id}/reserve")
    public ResponseEntity<?> reserve(
            @PathVariable String id,
            @RequestParam int rooms) {
        logger.info("reserve room :" + rooms + id);
        boolean isReserved = hotelService.reserveRoom(id, rooms);

        if (!isReserved) {
            logger.info("Not Reserved");
            return ResponseEntity.badRequest()
                    .body(false);
        }
        logger.info("Room Reserved");
        return ResponseEntity.ok(true);
    }

    @PostMapping("/{id}/release")
    public ResponseEntity<?> release(
            @PathVariable String id,
            @RequestParam int rooms) {
        logger.info("release room :" + rooms + id);
        boolean isReleased= hotelService.releaseRoom(id, rooms);
       if(isReleased){
           return ResponseEntity.ok("Released");
       }
        logger.info("Rooms not Released");
        return ResponseEntity.badRequest().body("Not Released..");
    }
}
