package com.example.Models4.repository;

import com.example.Models4.model.SeatFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<SeatFlight,Long> {

}
