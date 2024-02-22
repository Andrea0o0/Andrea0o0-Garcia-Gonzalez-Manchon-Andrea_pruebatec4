package com.example.Models4.service.person;

import com.example.Models4.model.Employee;
import com.example.Models4.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public List<Employee> getEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepo.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }

    @Override
    public Employee findEmployee(Long id) {
        return employeeRepo.findById(id).orElse(null);
    }

    @Override
    public List<Employee> findEmployeeByUsername(String username) {
        return employeeRepo.findByUsername(username);
    }
}
