package com.example.Models4;

import com.example.Models4.controller.FlightController;
import com.example.Models4.model.Flight;
import com.example.Models4.service.flight.FlightService;
import com.example.Models4.service.flight.IFlightService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FlightTest {
    private FlightService flightService = mock(FlightService.class);
    private FlightController flightController = new FlightController(flightService);


    @Test
    public void testOneWayFlight_NoOrigin_ReturnsBadRequest() {
        // Arrange
        String origin = null;
        // Act
        ResponseEntity<?> response = flightController.oneWayFlight(origin, "Destination", 2, LocalDate.now(), null, "Economy", 500.0);
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testOneWayFlight_SeatsNumberGreaterThan8_ReturnsBadRequest() {
        // Arrange
        // Seats number greater than 8
        int seatsNumber = 10;
        // Act
        ResponseEntity<?> response = flightController.oneWayFlight("Origin", "Destination", seatsNumber, LocalDate.now(), null, "Economy", 500.0);
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // More test cases can be added to cover different scenarios

    @Test
    public void testOneWayFlight_ValidInput_ReturnsListOfFlights() {
        // Arrange
        String origin = "BCN";
        String destination = "Miami";
        int seatsNumber = 2;
        LocalDate fromDate = LocalDate.parse("2024-09-12");
        // Mocking flightService
        List<Flight> mockFlights = List.of(new Flight(), new Flight());
        when(flightService.findOneWayFlight(origin, destination, fromDate, seatsNumber)).thenReturn(mockFlights);
        // Act
        ResponseEntity<?> response = flightController.oneWayFlight(origin,
                                                                   destination, seatsNumber, fromDate, null, null, null);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockFlights, response.getBody());
    }
}


