package com.victoruk.StarHotel.service.interfac;

import com.victoruk.StarHotel.dto.Response;
import com.victoruk.StarHotel.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRoomService {

    Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description);

    List<String> getAllRoomTypes();

    Response getALLRoom();

    Response deleteRoom(Long roomId);

//    Response updateRoom(Long roomId, String roomType, BigDecimal roomPrice, MultipartFile photo);

    Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo);

    Response getRoomById(Long roomId);

    Response getAvailableRoomByDateAndType(LocalDate checkInDAte, LocalDate checkOutDate, String roomType);

    Response getAvailableRooms();


}
