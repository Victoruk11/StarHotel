package com.victoruk.StarHotel.repo;

import com.victoruk.StarHotel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BooKingRepository  extends JpaRepository <Booking, Long> {

    List<Booking> findByRoomId(Long roomId);
    Optional<Booking> findByConfirmationCode(String confirmationCode);
    List<Booking> findByUserId(Long userId);

}
