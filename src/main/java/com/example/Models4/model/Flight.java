package com.example.Models4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Flight {
    @Id
    private String flightNumber;

    private String origin;
    private String destination;
    private String seatType;
    private Double price;
    private LocalDateTime takeoffDate;
    private LocalDateTime arrivalDate;


    private Integer rowsSeat;
    private Integer columnsSeat;
    private Double seatPrice;


    private Integer seatsNumber;
    private Integer reservedSeats;
    private Boolean activeFlag;

    @OneToMany(mappedBy = "flightOneWay")
    @JsonIgnore
    private List<FlightBooking> oneWayBookings;

    @OneToMany(mappedBy = "flightReturn")
    @JsonIgnore
    private List<FlightBooking> returnBookings;

    @OneToMany(mappedBy = "flightId")
    @JsonIgnore
    private List<SeatFlight> seatsBooked;

}


