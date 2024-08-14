package com.victoruk.StarHotel.service.imp;

import com.victoruk.StarHotel.dto.Response;
import com.victoruk.StarHotel.dto.RoomDTO;
import com.victoruk.StarHotel.entity.Room;
import com.victoruk.StarHotel.exception.OurException;
import com.victoruk.StarHotel.repo.BooKingRepository;
import com.victoruk.StarHotel.repo.RoomRepository;
import com.victoruk.StarHotel.service.interfac.AwsS3Service;
import com.victoruk.StarHotel.service.interfac.IRoomService;
import com.victoruk.StarHotel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BooKingRepository booKingRepository;
    @Autowired
    private AwsS3Service awsS3Service;  //i did not implement this because i dont have aws paid servece.



    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();

        try{
//            String imageUrl = awsS3Service.saveImageToS3(photo);
            Room room = new Room();
//            room.getRoomPhotoUrl(imageUrl)
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);
            Room saveRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(saveRoom);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

             }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error saving a room" + e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {

        return roomRepository.findDistinctRoomType();
    }
    @Override
    public Response getALLRoom() {

        Response response = new Response();

        try{
            List<Room>roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToroomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error saving a room" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();

        try{
            roomRepository.findById(roomId).orElseThrow(()-> new OurException("Room not found"));
            roomRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("successFul");

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error deleting room" + e.getMessage());
        }
        return response;
    }

//    @Override
//    public Response updateRoom(Long roomId, String roomType, BigDecimal roomPrice, MultipartFile photo) {
//        return null;
//    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo) {

        Response response = new Response();

        try{
             Room room =  roomRepository.findById(roomId).orElseThrow(()-> new OurException("Room not found"));
            if (roomType != null) room.setRoomType(roomType);
            if (roomPrice != null) room.setRoomPrice(roomPrice);
            if (roomType != null) room.setRoomDescription(description);

            Room updatedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);
            response.setStatusCode(200);
            response.setMessage("successFul");

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error deleting room" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();

        try{
           Room room  = roomRepository.findById(roomId).orElseThrow(()-> new OurException("Room not found"));
           RoomDTO  roomDTO = Utils.mapRoomEntityToRoomDTOPlusBooking(room);
//            roomRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("successFul");
            response.setRoom(roomDTO);

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting roomById" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomByDateAndType(LocalDate checkInDAte, LocalDate checkOutDate, String roomType) {
        Response response = new Response();

        try{
            List<Room> availableRooms = roomRepository.findAvailableRoombyDateAndTypes(checkInDAte, checkOutDate, roomType);
            List<RoomDTO>roomDTOList = Utils.mapRoomListEntityToroomListDTO(availableRooms);
            response.setStatusCode(200);
            response.setMessage("successFul");
            response.setRoomList(roomDTOList);

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error GettingAvailableRoomByDateAndType" + e.getMessage());
        }
        return response;

    }

    @Override
    public Response getAvailableRooms() {
        Response response = new Response();

        try{
            List<Room>roomList = roomRepository.getAllableRoom();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToroomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("successFul");
            response.setRoomList(roomDTOList);


        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting roomById" + e.getMessage());
        }
        return response;
    }
}
