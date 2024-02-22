package com.example.Models4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HotelBooking extends Booking {
    private LocalDate fromDate;
    private LocalDate untilDate;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private Integer peopleNumber;

    @OneToMany(mappedBy = "hotelBooking")
    private List<Guest> guests;

    private Double totalPrice;



}
