package com.example.Models4.repository;

import com.example.Models4.model.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelBookingRepository extends JpaRepository<HotelBooking,Long> {
}
