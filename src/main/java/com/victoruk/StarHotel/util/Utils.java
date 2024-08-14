package com.victoruk.StarHotel.util;

import com.victoruk.StarHotel.dto.BooKingDTO;
import com.victoruk.StarHotel.dto.RoomDTO;
import com.victoruk.StarHotel.dto.UserDTO;
import com.victoruk.StarHotel.entity.Booking;
import com.victoruk.StarHotel.entity.Room;
import com.victoruk.StarHotel.entity.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final SecureRandom secureRandom = new SecureRandom();


    public static String generateRandomConfirmationCode(int length){

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i <length; i++){
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public static UserDTO mapUserEntityToUserDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(userDTO.getName());
        userDTO.setEmail(userDTO.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(userDTO.getRole());
        return userDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTO(Room room){

        RoomDTO roomDTO = new RoomDTO();

       roomDTO.setId(room.getId());
       roomDTO.setRoomType(room.getRoomType());
       roomDTO.setRoomPrice(room.getRoomPrice());
       roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
       roomDTO.setRoomDescription(room.getRoomDescription());
        return roomDTO;
    }

    public static BooKingDTO mapBookingEntityToBookingsDTO(Booking booking){

        BooKingDTO booKingDTO = new BooKingDTO();

        booKingDTO.setId(booking.getId());
        booKingDTO.setCheckInDate(booking.getCheckInDate());
        booKingDTO.setCheckOutDate(booking.getCheckOutDate());
        booKingDTO.setNumberOfAdult(booking.getNumberOfAdult());
        booKingDTO.setNumberOfChildren(booking.getNumberOfChildren());
        booKingDTO.setTotalNumberOfGuest(booking.getTotalNumberOfGuest());
        booKingDTO.setBookingConformationCode(booking.getBookingConformationCode());
        return booKingDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTOPlusBooking(Room room){

        RoomDTO roomDTO = new RoomDTO();

       roomDTO.setId(room.getId());
       roomDTO.setRoomType(room.getRoomType());
       roomDTO.setRoomPrice(room.getRoomPrice());
       roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
       roomDTO.setRoomDescription(room.getRoomDescription());

       if (room.getBookings() != null){
           roomDTO.setBookings(room.getBookings().stream().map(Utils::mapBookingEntityToBookingsDTO).collect(Collectors.toList()));

       }
       return roomDTO;

    }

    public static UserDTO mapUserEntityToUserDTOPlusUserBookingAndRoom(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(userDTO.getName());
        userDTO.setEmail(userDTO.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(userDTO.getRole());

        if (!user.getBookings().isEmpty()){
            userDTO.setBookings(user.getBookings().stream()
                    .map(booking -> mapBookingEntityToBookingDTOPlusBookedRoom
                            (booking,false)).collect(Collectors.toList()));
        }
        return userDTO;
    }


    public static BooKingDTO mapBookingEntityToBookingDTOPlusBookedRoom(Booking booking, boolean mapUser){

        BooKingDTO booKingDTO = new BooKingDTO();

        booKingDTO.setId(booking.getId());
        booKingDTO.setCheckInDate(booking.getCheckInDate());
        booKingDTO.setCheckOutDate(booking.getCheckOutDate());
        booKingDTO.setNumberOfAdult(booking.getNumberOfAdult());
        booKingDTO.setNumberOfChildren(booking.getNumberOfChildren());
        booKingDTO.setTotalNumberOfGuest(booking.getTotalNumberOfGuest());
        booKingDTO.setBookingConformationCode(booking.getBookingConformationCode());

        if (mapUser){
            booKingDTO.setUser(Utils.mapUserEntityToUserDTO(booking.getUser()));
        }

        if (booking.getRoom() != null){

            RoomDTO roomDTO = new RoomDTO();

            roomDTO.setId(booking.getRoom().getId());
            roomDTO.setRoomType(booking.getRoom().getRoomType());
            roomDTO.setRoomPrice(booking.getRoom().getRoomPrice());
            roomDTO.setRoomPhotoUrl(booking.getRoom().getRoomPhotoUrl());
            roomDTO.setRoomDescription(booking.getRoom().getRoomDescription());
            booKingDTO.setRoom(roomDTO);

        }
        return booKingDTO;

    }

    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList){

        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    public static List<RoomDTO> mapRoomListEntityToroomListDTO(List<Room> roomList){

        return roomList.stream().map(Utils::mapRoomEntityToRoomDTO).collect(Collectors.toList());
    }

    public static List<BooKingDTO> mapBookionListEntityToBooKingListDTO(List<Booking> bookingList){

        return bookingList.stream().map(Utils::mapBookingEntityToBookingsDTO).collect(Collectors.toList());
    }




}



































