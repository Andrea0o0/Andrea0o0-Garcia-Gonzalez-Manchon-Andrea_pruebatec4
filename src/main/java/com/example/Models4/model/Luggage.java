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
public class Luggage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String luggageType;
    private String description;
    private Boolean invoiced;
    private Boolean model;
    private Double kg;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    @JsonIgnore
    private Passenger passenger;
}
