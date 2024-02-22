package com.example.Models4.service.passengersLuggageSeat;

import com.example.Models4.model.SeatFlight;

import java.util.List;

public interface ISeatService {
    public List<SeatFlight> getAllSeats();

    public SeatFlight saveSeat(SeatFlight seatFlight);

    public void deleteSeat(Long id);

    public SeatFlight findSeat(Long id);
}
