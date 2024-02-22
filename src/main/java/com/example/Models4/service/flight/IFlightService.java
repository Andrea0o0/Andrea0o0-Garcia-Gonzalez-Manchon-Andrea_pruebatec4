package com.example.Models4.service.flight;

import com.example.Models4.model.Flight;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IFlightService {
    public List<Flight> getFlights();

    public void saveFlight(Flight flight);

    public void deleteFlight(String id);

    public Flight findFlight(String id);

    public List<Flight> findOneWayFlight(
            String origin,
            String destination,
            LocalDate date,
            Integer seats);

    public List<Flight> findOneWayFlightRangeDates(
            String origin,
            String destination,
            LocalDate startDate,
            LocalDate endDate,
            Integer minAvailableSeats);

    public List<Flight> findOneWayFlightNoDestination(
            String origin,
            LocalDate takeoffDate,
            Integer seats);

    public List<Flight> findOneWayDateRangeFlightNoDestination(
            String origin,
            LocalDate startDate,
            LocalDate endDate,
            Integer seats);
}
