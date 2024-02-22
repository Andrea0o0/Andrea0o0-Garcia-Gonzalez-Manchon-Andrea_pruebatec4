package com.example.Models4.service.passengersLuggageSeat;

import com.example.Models4.model.Passenger;
import com.example.Models4.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService implements IPassengerService {
    @Autowired
    private PassengerRepository passengerRepo;

    @Override
    public List<Passenger> getPassengers() {
        return passengerRepo.findAll();
    }

    @Override
    public Passenger savePassenger(Passenger passenger) {
        return passengerRepo.saveAndFlush(passenger);
    }

    @Override
    public void deletePassenger(Long id) {
        passengerRepo.deleteById(id);
    }

    @Override
    public Passenger findPassenger(Long id) {
        return passengerRepo.findById(id).orElse(null);
    }
}
