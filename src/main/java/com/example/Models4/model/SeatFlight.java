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
public class SeatFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rowSeat;
    private Integer columnSeat;
    private String seatString;
    private Double price;

    @OneToOne(mappedBy = "seatFlightRowColumnOneWay", cascade = CascadeType.ALL)
    @JsonIgnore
    private Passenger passengerOneWay;

    @OneToOne(mappedBy = "seatFlightRowColumnReturn", cascade = CascadeType.ALL)
    @JsonIgnore
    private Passenger passengerReturn;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    @JsonIgnore
    private Flight flightId;

}


