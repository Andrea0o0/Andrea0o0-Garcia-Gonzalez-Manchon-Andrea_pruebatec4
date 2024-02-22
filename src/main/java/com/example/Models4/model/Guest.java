package com.example.Models4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String email;

    @ManyToOne
    @JoinColumn(name = "hotel_booking_id")
    @JsonIgnore
    private HotelBooking hotelBooking;
}
