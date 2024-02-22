package com.example.Models4.dto;

import com.example.Models4.model.Room;
import jakarta.persistence.CascadeType;
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
public class HotelRoomDto {
    private String hotelCode;

    private String name;
    private String place;
    private String checkIn;
    private String checkOut;
    private Boolean activeFlag;
    private String description;

    private List<Room> availableRooms;
}
