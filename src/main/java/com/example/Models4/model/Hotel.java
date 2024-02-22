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
public class Hotel {
    @Id
    private String hotelCode;

    private String name;
    private String place;
    private String checkIn;
    private String checkOut;
    private Boolean activeFlag;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @Override
    public String toString() {
        return "Hotel { " +
                "\n" + "   'hotelCode':    " + hotelCode +
                "\n" + "   'name':         " + name +
                "\n" + "   'place':        " + place +
                "\n" + "   'checkIn':      " + checkIn +
                "\n" + "   'checkOut':     " + checkOut +
                "\n" + "   'activeFlag':   " + activeFlag +
                "\n" + "   'description':  " + description + "\n" + "}";
    }
}
