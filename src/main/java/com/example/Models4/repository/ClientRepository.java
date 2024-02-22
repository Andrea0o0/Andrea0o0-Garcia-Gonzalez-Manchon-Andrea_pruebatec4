package com.example.Models4.repository;

import com.example.Models4.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    @Query("SELECT c FROM Client c WHERE c.email = ?1")
    List<Client> findByEmail(String email);
}
