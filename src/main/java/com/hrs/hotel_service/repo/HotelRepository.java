package com.hrs.hotel_service.repo;

import com.hrs.hotel_service.entity.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HotelRepository extends MongoRepository<Hotel, String> {
    Hotel findByName(String email);
    List<Hotel> findByCity(String email);
}
