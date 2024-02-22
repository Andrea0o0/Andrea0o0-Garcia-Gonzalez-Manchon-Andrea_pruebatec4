package com.example.Models4.service.person;

import com.example.Models4.model.Employee;

import java.util.List;

public interface IEmployeeService {
    public List<Employee> getEmployees();

    public void saveEmployee(Employee employee);

    public void deleteEmployee(Long id);

    public Employee findEmployee(Long id);

    public List<Employee> findEmployeeByUsername(String username);
}
