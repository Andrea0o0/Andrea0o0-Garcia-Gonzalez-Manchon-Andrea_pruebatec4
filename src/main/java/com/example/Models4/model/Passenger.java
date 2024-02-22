package com.example.Models4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String identification;
    private String email;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL)
    private List<Luggage> luggageList;

    private Double luggagePrice;

    @OneToOne
    @JoinColumn(name = "seat_oneway")
    private SeatFlight seatFlightRowColumnOneWay;

    @OneToOne
    @JoinColumn(name = "seat_return")
    private SeatFlight seatFlightRowColumnReturn;

    private Double seatsPrice;

    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "flight_booking_id")
    @JsonIgnore
    private FlightBooking flightBooking;
}
