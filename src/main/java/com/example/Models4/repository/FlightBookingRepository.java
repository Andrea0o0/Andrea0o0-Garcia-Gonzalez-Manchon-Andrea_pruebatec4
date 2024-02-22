package com.example.Models4.repository;

import com.example.Models4.model.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking,
        Long> {
}
