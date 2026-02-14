package com.hrs.hotel_service.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "hotels")
public class Hotel {
    @Id
    private String hotelId;
    private String name;
    private String city;
    private int availableRooms;
    private int totalRooms;
}
