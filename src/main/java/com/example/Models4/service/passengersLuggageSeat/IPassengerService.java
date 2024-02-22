package com.example.Models4.service.passengersLuggageSeat;

import com.example.Models4.model.Passenger;

import java.util.List;

public interface IPassengerService {
    public List<Passenger> getPassengers();

    public Passenger savePassenger(Passenger passenger);

    public void deletePassenger(Long id);

    public Passenger findPassenger(Long id);
}
