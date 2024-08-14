package com.victoruk.StarHotel.service.interfac;

import com.victoruk.StarHotel.dto.Response;
import com.victoruk.StarHotel.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Long UserId, Booking bookingResquest);

    Response findBookingByConformationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);


}
