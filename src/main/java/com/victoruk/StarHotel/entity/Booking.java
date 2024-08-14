package com.victoruk.StarHotel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "check in date is required")
    private LocalDate  checkInDate;

    @Future(message = "check out date must be in the future")
    private LocalDate  checkOutDate;

    @Min(value = 1, message = "number of adult must not be less than one")
    private int NumberOfAdult;

    @Min(value = 0, message = "number of children must not be less than zero")
    private int numberOfChildren;

    private int totalNumberOfGuest;

    private String bookingConformationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public void calculateTotalNumberOfGuest(){

        this.totalNumberOfGuest = this.numberOfChildren + this.NumberOfAdult;
    }

    public void setNumberOfAdult(int numberOfAdult) {
        NumberOfAdult = numberOfAdult;
        calculateTotalNumberOfGuest();
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
        calculateTotalNumberOfGuest();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", NumberOfAdult=" + NumberOfAdult +
                ", numberOfChildren=" + numberOfChildren +
                ", totalNumberOfGuest=" + totalNumberOfGuest +
                ", bookingConformationCode='" + bookingConformationCode + '\'' +
                '}';
    }
}
