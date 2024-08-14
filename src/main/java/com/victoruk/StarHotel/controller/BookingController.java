package com.victoruk.StarHotel.controller;


import com.victoruk.StarHotel.dto.Response;
import com.victoruk.StarHotel.entity.Booking;
import com.victoruk.StarHotel.service.interfac.IBookingService;
import com.victoruk.StarHotel.service.interfac.IRoomService;
import com.victoruk.StarHotel.service.interfac.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    IBookingService bookingService;


    @PostMapping("/booking/{roomId}/{userId")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> saveBookings(@PathVariable Long roomId,
                                                 @PathVariable Long userId,
                                                 @RequestBody Booking bookingRequest){

        Response responsen = bookingService.saveBooking(roomId,userId, bookingRequest);
        return ResponseEntity.status(responsen.getStatusCode()).body(responsen);

    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllBookings(){

        Response response = bookingService.getAllBookings();

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-by-confirmation-code/{confirmationCode}")
    public ResponseEntity<Response> getBookingByConfrmationCode(@PathVariable String confirmationCode){

        Response response = bookingService.findBookingByConformationCode(confirmationCode);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/cancel/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> getBookingByConfrmationCode(@PathVariable Long bookId){

        Response response = bookingService.cancelBooking(bookId);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }









//    Response saveBooking(Long roomId, Long User, Booking bookingResquest);
//
//    Response findBookingByConformationCode(String confirmationCode);
//
//    Response getAllBookings();
//
//    Response cancelBooking(Long bookingId);

}
