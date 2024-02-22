package com.example.Models4.repository;

import com.example.Models4.model.Client;
import com.example.Models4.model.ReservedDate;
import com.example.Models4.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservedDateRepository extends JpaRepository<ReservedDate,Long> {
    @Query("SELECT r FROM ReservedDate r WHERE r.room = ?1")
    List<ReservedDate> findByRoom(Room room);
}

