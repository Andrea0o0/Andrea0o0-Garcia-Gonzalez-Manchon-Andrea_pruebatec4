package com.example.Models4.service.booking;

import com.example.Models4.model.FlightBooking;
import com.example.Models4.repository.FlightBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightBookingService implements IFlightBookingService {

    @Autowired
    private FlightBookingRepository flightBookingRepo;

    @Override
    public List<FlightBooking> getFlightBookings() {
        return flightBookingRepo.findAll();
    }

    @Override
    public FlightBooking saveFlightBooking(FlightBooking flightBooking) {
        return flightBookingRepo.saveAndFlush(flightBooking);
    }

    @Override
    public void deleteFlightBooking(Long id) {
        flightBookingRepo.deleteById(id);
    }

    @Override
    public FlightBooking findFlightBooking(Long id) {
        return flightBookingRepo.findById(id).orElse(null);
    }
}
