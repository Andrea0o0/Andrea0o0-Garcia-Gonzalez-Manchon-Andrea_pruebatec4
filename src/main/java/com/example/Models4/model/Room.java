package com.example.Models4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hotelName;
    private String place;
    private String roomNumber;
    private Integer roomType;
    private String roomTypeString;
    private Double priceNight;
    private LocalDate fromDate;
    private LocalDate untilDate;
    private Boolean activeFlag;

    @OneToMany(mappedBy = "room")
    private List<ReservedDate> reservedDates;
//    private List<String> urlsImages;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<HotelBooking> bookings;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonIgnore
    private Hotel hotel;


}
