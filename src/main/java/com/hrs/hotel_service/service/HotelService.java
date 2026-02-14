package com.hrs.hotel_service.service;

import com.hrs.hotel_service.entity.Hotel;
import com.hrs.hotel_service.repo.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    HotelRepository repo;

    public Hotel create(Hotel h) {
        h.setAvailableRooms(h.getTotalRooms());
        return repo.save(h);
    }

    public List<Hotel> fetchAllHotels() {
        return repo.findAll();
    }

    public Optional<Hotel> getHotel(String id) {
        return repo.findById(id);
    }

    public boolean reserveRoom(String id, int rooms) {
        Optional<Hotel> opt = repo.findById(id);
        if (opt.isEmpty()) return false;

        Hotel h = opt.get();

        if (h.getAvailableRooms() < rooms)
            return false;

        h.setAvailableRooms(h.getAvailableRooms() - rooms);
        repo.save(h);
        return true;
    }

    public boolean releaseRoom(String id, int rooms) {
        Optional<Hotel> opt = repo.findById(id);
        if (opt.isEmpty())
            return false;

        Hotel h = opt.get();

        if (h.getTotalRooms() < (rooms+ h.getAvailableRooms()))
            return false;

        h.setAvailableRooms(h.getAvailableRooms() + rooms);
        repo.save(h);
        return true;
    }
}