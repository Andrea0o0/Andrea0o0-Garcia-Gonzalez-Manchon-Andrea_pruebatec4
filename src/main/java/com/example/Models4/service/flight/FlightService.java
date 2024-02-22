package com.example.Models4.service.flight;

import com.example.Models4.model.Flight;
import com.example.Models4.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService implements IFlightService{

    @Autowired
    private FlightRepository flightRepo;

    @Override
    public List<Flight> getFlights() {
        return flightRepo.findAll();
    }

    @Override
    public void saveFlight(Flight flight) {
        flightRepo.save(flight);
    }

    @Override
    public void deleteFlight(String id) {
        flightRepo.deleteById(id);
    }

    @Override
    public Flight findFlight(String id) {
        return flightRepo.findById(id).orElse(null);
    }

    @Override
    public List<Flight> findOneWayFlight(String origin, String destination, LocalDate date, Integer seats) {
        return flightRepo.findOneWayFlightByOriginDestinationDateSeats(origin,destination,date,seats);
    }

    @Override
    public List<Flight> findOneWayFlightRangeDates(String origin,
                                                   String destination,
                                                   LocalDate startDate, LocalDate endDate, Integer seats) {
        return flightRepo.findOneWayDateRangeFlight(origin,destination,
                                                    startDate,endDate,seats);
    }

    @Override
    public List<Flight> findOneWayFlightNoDestination(String origin,
                                          LocalDate date, Integer seats) {
        return flightRepo.findOneWayFlightByOriginDateSeats(origin,date,seats);
    }

    @Override
    public List<Flight> findOneWayDateRangeFlightNoDestination(String origin, LocalDate startDate, LocalDate endDate, Integer seats) {
        return flightRepo.findOneWayDateRangeFlightNoDestination(origin,
                                                    startDate,endDate,seats);
    }
}
