package com.example.Models4.service.passengersLuggageSeat;

import com.example.Models4.model.SeatFlight;
import com.example.Models4.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService implements ISeatService {
    @Autowired
    private SeatRepository seatRepo;

    @Override
    public List<SeatFlight> getAllSeats() {
        return seatRepo.findAll();
    }

    @Override
    public SeatFlight saveSeat(SeatFlight seatFlight) {
        return seatRepo.saveAndFlush(seatFlight);
    }

    @Override
    public void deleteSeat(Long id) {
        seatRepo.deleteById(id);
    }

    @Override
    public SeatFlight findSeat(Long id) {
        return seatRepo.findById(id).orElse(null);
    }
}
