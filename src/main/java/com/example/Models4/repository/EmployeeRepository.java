package com.example.Models4.repository;

import com.example.Models4.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findByUsername(String username);

}
