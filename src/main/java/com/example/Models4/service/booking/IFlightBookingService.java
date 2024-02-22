package com.example.Models4.service.booking;

import com.example.Models4.model.FlightBooking;

import java.util.List;

public interface IFlightBookingService {
    public List<FlightBooking> getFlightBookings();

    public FlightBooking saveFlightBooking(FlightBooking flightBooking);

    public void deleteFlightBooking(Long id);

    public FlightBooking findFlightBooking(Long id);
}
