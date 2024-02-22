package com.example.Models4.service.booking;

import com.example.Models4.model.HotelBooking;

import java.util.List;

public interface IHotelBookingService {
    public List<HotelBooking> getHotelBookings();

    public HotelBooking saveHotelBooking(HotelBooking hotelBooking);

    public void deleteHotelBooking(Long id);

    public HotelBooking findHotelBooking(Long id);
}