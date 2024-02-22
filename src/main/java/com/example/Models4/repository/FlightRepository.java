package com.example.Models4.repository;

import com.example.Models4.model.Client;
import com.example.Models4.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface FlightRepository extends JpaRepository<Flight,String> {
    @Query("SELECT f FROM Flight f WHERE f.origin = :origin AND f.destination = :destination AND DATE(f.takeoffDate) = :takeoffDate AND (f.seatsNumber - f.reservedSeats) >= :seats AND f.activeFlag = true")
    List<Flight> findOneWayFlightByOriginDestinationDateSeats(
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("takeoffDate") LocalDate takeoffDate,
            @Param("seats") Integer seats);

    @Query("SELECT f FROM Flight f WHERE f.origin = :origin AND f.destination = :destination AND DATE(f.takeoffDate) BETWEEN :startDate AND :endDate AND (f.seatsNumber - f.reservedSeats) >= :seats AND f.activeFlag = true")
    List<Flight> findOneWayDateRangeFlight(
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("seats") Integer seats);

    @Query("SELECT f FROM Flight f WHERE f.origin = :origin AND DATE(f.takeoffDate) = :takeoffDate AND (f.seatsNumber - f.reservedSeats) >= :seats AND f.activeFlag = true")
    List<Flight> findOneWayFlightByOriginDateSeats(
            @Param("origin") String origin,
            @Param("takeoffDate") LocalDate takeoffDate,
            @Param("seats") Integer seats);

    @Query("SELECT f FROM Flight f WHERE f.origin = :origin AND DATE(f.takeoffDate) BETWEEN :startDate AND :endDate AND (f.seatsNumber - f.reservedSeats) >= :seats AND f.activeFlag = true")
    List<Flight> findOneWayDateRangeFlightNoDestination(
            @Param("origin") String origin,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("seats") Integer seats);
}
