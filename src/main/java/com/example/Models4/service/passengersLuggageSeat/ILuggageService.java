package com.example.Models4.service.passengersLuggageSeat;

import com.example.Models4.model.Luggage;

import java.util.List;

public interface ILuggageService {
    public List<Luggage> getAllLuggage();

    public Luggage saveLuggage(Luggage luggage);

    public void deleteLuggage(Long id);

    public Luggage findLuggage(Long id);

    public List<Luggage> findModelLuggage();
}
