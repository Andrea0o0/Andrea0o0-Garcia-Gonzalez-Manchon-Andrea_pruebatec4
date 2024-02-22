package com.example.Models4.repository;

import com.example.Models4.model.Flight;
import com.example.Models4.model.Luggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LuggageRepository extends JpaRepository<Luggage, Long> {
    @Query("SELECT l FROM Luggage l WHERE l.model = true")
    List<Luggage> findModelLuggage();
}
