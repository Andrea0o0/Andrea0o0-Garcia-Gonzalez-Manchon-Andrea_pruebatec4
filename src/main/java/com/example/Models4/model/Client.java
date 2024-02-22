package com.example.Models4.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Client extends Person {
    private String name;
    private String lastName;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Booking> bookings;

}
