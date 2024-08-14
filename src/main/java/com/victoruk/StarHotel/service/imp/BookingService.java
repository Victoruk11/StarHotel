package com.victoruk.StarHotel.service.imp;

import com.victoruk.StarHotel.dto.BooKingDTO;
import com.victoruk.StarHotel.dto.Response;
import com.victoruk.StarHotel.entity.Booking;
import com.victoruk.StarHotel.entity.Room;
import com.victoruk.StarHotel.entity.User;
import com.victoruk.StarHotel.exception.OurException;
import com.victoruk.StarHotel.repo.BooKingRepository;
import com.victoruk.StarHotel.repo.RoomRepository;
import com.victoruk.StarHotel.repo.UserRepository;
import com.victoruk.StarHotel.service.interfac.IBookingService;
import com.victoruk.StarHotel.service.interfac.IRoomService;
import com.victoruk.StarHotel.util.Utils;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService {

    @Autowired
    BooKingRepository booKingRepository;

    @Autowired
    private IRoomService roomService;

    @Autowired

    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Response saveBooking(Long roomId, Long userId, Booking bookingResquest) {
        Response response = new Response();

        try {
            if (bookingResquest.getCheckOutDate().isBefore(bookingResquest.getCheckInDate())){

                throw new IllegalArgumentException("check in date must come after check out date");
            }
            Room room = roomRepository.findById(roomId).orElseThrow(()-> new OurException("Room Not Found"));

            User user = userRepository.findById(userId).orElseThrow(()-> new OurException("User Not Found"));

            List<Booking> existingBookings = room.getBookings();
            if (!roomIsAvailable(bookingResquest, existingBookings)){
                throw new OurException("Room not available for selected date range");
            }

            bookingResquest.setRoom(room);
            bookingResquest.setUser(user);
            String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
            bookingResquest.setBookingConformationCode(bookingConfirmationCode);
            booKingRepository.save(bookingResquest);
            response.setStatusCode(200);
            response.setMessage("successfull");
            response.setBookingConformationCode(bookingConfirmationCode);

        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error saving the booking: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response findBookingByConformationCode(String confirmationCode) {
        Response response = new Response();

        try {
            Booking booking = booKingRepository.findByConfirmationCode(confirmationCode)
                    .orElseThrow(() -> new OurException("Booking not found"));
            BooKingDTO booKingDTO = Utils.mapBookingEntityToBookingsDTO(booking);
            response.setStatusCode(200);
            response.setMessage("successfull");
            response.setBooking(booKingDTO);

        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error saving the booking: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();

        try {
            List <Booking> bookingList = booKingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BooKingDTO> booKingDTOList = Utils.mapBookionListEntityToBooKingListDTO(bookingList);
            response.setStatusCode(200);
            response.setMessage("successfull");
            response.setBooKingList(booKingDTOList);

        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting all bookings: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response = new Response();

        try {
             booKingRepository.findById(bookingId)
                    .orElseThrow(()-> new OurException("Booking does not exist"));
             booKingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("successfull");

        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error cancelling the bookings: " + e.getMessage());
        }
        return response;
    }



    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        // Check if the new booking's check-in date is before the existing booking's check-out date
                        // and the new booking's check-out date is after the existing booking's check-in date
                        (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()) &&
                                bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckInDate())) ||
                                // Check if the new booking's check-in date is equal to the existing booking's check-in date
                                bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate()) ||
                                // Check if the new booking's check-out date is equal to the existing booking's check-out date
                                bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()) ||
                                // Check if the new booking's check-in date is within an existing booking's period
                                (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate()) &&
                                        bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate())) ||
                                // Check if the new booking's check-out date is within an existing booking's period
                                (bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckInDate()) &&
                                        bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())) ||
                                // Check if the new booking completely overlaps an existing booking
                                (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate()) &&
                                        bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate())) ||
                                // Check if the existing booking's check-in date is within the new booking's period
                                (existingBooking.getCheckInDate().isAfter(bookingRequest.getCheckInDate()) &&
                                        existingBooking.getCheckInDate().isBefore(bookingRequest.getCheckOutDate())) ||
                                // Check if the existing booking's check-out date is within the new booking's period
                                (existingBooking.getCheckOutDate().isAfter(bookingRequest.getCheckInDate()) &&
                                        existingBooking.getCheckOutDate().isBefore(bookingRequest.getCheckOutDate()))
                );
    }



//    private boolean roomIsAvailable(Booking bookingResquest, List<Booking> existingBookings) {
//
//        return existingBookings.stream()
//                .noneMatch(existingBooking ->
//                        bookingResquest.getCheckInDate().equals(existingBooking.getCheckInDate())
//                                || bookingResquest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
//                                || (bookingResquest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
//                                && bookingResquest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
//                                || (bookingResquest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
//
//                                && bookingResquest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
//                                || (bookingResquest.getCheckInDate().isBefore(existingBooking.getCheckInDate()))
//
//                                && bookingResquest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))
//
//                                 || (bookingResquest.getCheckInDate().equals(existingBooking.getCheckOutDate())
//                                 && bookingResquest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
//
//                                || (bookingResquest.getCheckInDate().equals(existingBooking.getCheckOutDate())
//
//                                && bookingResquest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
//                };
//
//    }

}


