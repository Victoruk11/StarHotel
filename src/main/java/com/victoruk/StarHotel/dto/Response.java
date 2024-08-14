package com.victoruk.StarHotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int statusCode;
    private String message;

    private String token;
    private String role;
    private String expirationTime;
    private String bookingConformationCode;

    private UserDTO user;
    private RoomDTO room;
    private BooKingDTO booking;
    private List<UserDTO> userList;
    private List<RoomDTO> roomList;
    private List<BooKingDTO> booKingList;


}
