package com.victoruk.StarHotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.victoruk.StarHotel.entity.Booking;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {


    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private String roomDescription;
    private List<BooKingDTO> bookings = new ArrayList<>();
}
