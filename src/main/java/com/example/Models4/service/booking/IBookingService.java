package com.example.Models4.service.booking;

import com.example.Models4.model.Booking;

import java.util.List;

public interface IBookingService {
    public List<Booking> getBookings();

    public void saveBooking(Booking booking);

    public void deleteBooking(Long id);

    public Booking findBooking(Long id);
}
