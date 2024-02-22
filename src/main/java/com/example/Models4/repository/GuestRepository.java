package com.example.Models4.repository;

import com.example.Models4.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest,Long> {
}
