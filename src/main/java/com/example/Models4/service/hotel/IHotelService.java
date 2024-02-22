package com.example.Models4.service.hotel;

import com.example.Models4.model.Hotel;

import java.util.List;

public interface IHotelService {
    public List<Hotel> getHotels();

    public Hotel saveHotel(Hotel hotel);

    public void deleteHotel(String hotelCode);

    public Hotel findHotel(String hotelCode);
}