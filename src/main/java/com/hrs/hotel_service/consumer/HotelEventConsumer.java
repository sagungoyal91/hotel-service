package com.hrs.hotel_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.hotel_service.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelEventConsumer {

    private final HotelService hotelService;

    @KafkaListener(topics = "payment-failed", groupId = "hotel-group")
    public void handleFailure(String message) throws JsonProcessingException {

        log.info("Received: {}", message);

        Map<String,Object> map =
                new ObjectMapper().readValue(message, Map.class);

        String hotelId = (String) map.get("hotelId");
        int rooms = (Integer) map.get("rooms");

        hotelService.releaseRoom(hotelId, rooms);

        log.info("HotelEventCOnsumer- rooms released");
    }

}

