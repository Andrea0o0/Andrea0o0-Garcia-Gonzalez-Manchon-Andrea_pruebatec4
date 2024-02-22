package com.example.Models4.controller;

import com.example.Models4.model.Luggage;
import com.example.Models4.service.passengersLuggageSeat.ILuggageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/luggage")
public class LuggageController {

    @Autowired
    private ILuggageService luggageService;

    @Operation(summary = "Get all luggage",
            description = "Get all luggage items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Luggage retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No luggage found")
    })
    @GetMapping("/get")
//    PRIVATE: ADMIN
    public ResponseEntity<?> getLuggage() {
        return luggageService.getAllLuggage() == null ? new ResponseEntity<>(
                "No luggage found",HttpStatus.NOT_FOUND):
                new ResponseEntity<>(luggageService.getAllLuggage(),
                                     HttpStatus.OK);
    }


    @Operation(summary = "Get luggage by ID",
            description = "Get luggage item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Luggage retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Luggage not found")
    })
    @GetMapping("/get/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<?> getLuggage (@PathVariable long id){
        return luggageService.findLuggage(id) == null ? new ResponseEntity<>(
                "There's no luggage with " + id + " Id",
                HttpStatus.NOT_FOUND):
                new ResponseEntity<>(luggageService.findLuggage(id),HttpStatus.OK);
    }


    @Operation(summary = "Get luggage models",
            description = "Get all available luggage models")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Luggage models retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No luggage models found")
    })
    @GetMapping("/getModels")
//    PRIVATE: ADMIN
    public List<Luggage> getModelsLuggage() {
        return luggageService.findModelLuggage();
    }


    @Operation(summary = "Create luggage",
            description = "Create a new luggage item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Luggage created successfully")
    })
    @PostMapping("/create")
//    PRIVATE: ADMIN
    public ResponseEntity<String> createLuggage(@RequestBody Luggage luggage) {
        luggageService.saveLuggage(luggage);
        return ResponseEntity.status(
                HttpStatus.CREATED).body("Luggage created successfully");
    }

    @Operation(summary = "Delete luggage by ID",
            description = "Delete luggage item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Luggage deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Luggage not found")
    })
    @DeleteMapping("/delete/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<String> deleteLuggage(@PathVariable Long id) {
        if (luggageService.findLuggage(id) != null) {
            luggageService.deleteLuggage(id);
            return ResponseEntity.status(HttpStatus.OK).body("Luggage deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Luggage not found");
        }
    }

    @Operation(summary = "Update luggage by ID",
            description = "Update luggage item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Luggage updated successfully"),
            @ApiResponse(responseCode = "404", description = "Luggage not found")
    })
    @PutMapping("/update/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<String> updateLuggage(@PathVariable Long id, @RequestBody Luggage updatedLuggage) {
        Luggage luggage = luggageService.findLuggage(id);
        if (luggage != null) {
            luggage.setDescription(updatedLuggage.getDescription() == null ?
                                           luggage.getDescription():
                                           updatedLuggage.getDescription());
            luggage.setInvoiced(updatedLuggage.getInvoiced() == null ?
                                        luggage.getInvoiced():
                                        updatedLuggage.getInvoiced());
            luggage.setKg(updatedLuggage.getKg() == null ?
                                  luggage.getKg():
                                  updatedLuggage.getKg());
            luggage.setPrice(updatedLuggage.getPrice() == null ?
                                     luggage.getPrice():
                                     updatedLuggage.getPrice());
            luggage.setPassenger(updatedLuggage.getPassenger() == null ?
                                         luggage.getPassenger():
                                         updatedLuggage.getPassenger());
            luggage.setLuggageType(updatedLuggage.getLuggageType() == null ?
                                           luggage.getDescription():
                                           updatedLuggage.getDescription());
            luggage.setModel(updatedLuggage.getModel() == null ?
                                     luggage.getModel():
                                     updatedLuggage.getModel());
            luggageService.saveLuggage(luggage);
            return ResponseEntity.status(HttpStatus.OK).body("Luggage updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Luggage not found");
        }
    }
}

