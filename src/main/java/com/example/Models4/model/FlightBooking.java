package com.example.Models4.model;

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
public class FlightBooking extends Booking {

    private String origin;
    private String destination;
    private Boolean bookingType;

    @ManyToOne
    @JoinColumn(name = "flight_one_way_id")
    private Flight flightOneWay;

    @ManyToOne
    @JoinColumn(name = "flight_return_id")
    private Flight flightReturn;

    private Integer peopleNumber;
    private String seatType;

    @OneToMany(mappedBy = "flightBooking")
    private List<Passenger> infoPassengers;

    private Double totalPrice;
}
