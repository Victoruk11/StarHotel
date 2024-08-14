package com.victoruk.StarHotel.repo;

import com.victoruk.StarHotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // to find a particular room type
    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomType();

    //to get available room that has not been booked
    @Query("SELECT r FROM r WHERE r.id NOT IN (SELECT b.room.id FROM Boooking b)")
    List<Room>getAllableRoom(); //this is getAllAvailableRoom

    @Query("SELECT r FROM Room WHERE r.roomType  LIKE %:roomType% AND r.id NOT IN (SELECT bk.room.id FROM Booking bk WHERE" +
            "bk.checkInDate <= :checkOutDate) AND (BK.checkOutDate >= :checkInDate)")
    List<Room>findAvailableRoombyDateAndTypes(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
}
