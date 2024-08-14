package com.victoruk.StarHotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.victoruk.StarHotel.entity.Room;
import com.victoruk.StarHotel.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BooKingDTO {



    private Long id;

    private LocalDate checkInDate;

    private LocalDate  checkOutDate;

    private int NumberOfAdult;

    private int numberOfChildren;

    private int totalNumberOfGuest;

    private String bookingConformationCode;

    private UserDTO user;

    private RoomDTO room;

}
