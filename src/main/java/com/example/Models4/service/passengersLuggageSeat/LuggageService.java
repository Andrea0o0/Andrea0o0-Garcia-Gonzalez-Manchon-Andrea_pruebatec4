package com.example.Models4.service.passengersLuggageSeat;

import com.example.Models4.model.Luggage;
import com.example.Models4.repository.LuggageRepository;
import com.example.Models4.service.passengersLuggageSeat.ILuggageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LuggageService implements ILuggageService {
    
    @Autowired
    private LuggageRepository luggageRepo;

    @Override
    public List<Luggage> getAllLuggage() {
        return luggageRepo.findAll();
    }

    @Override
    public Luggage saveLuggage(Luggage luggage) {
        return luggageRepo.saveAndFlush(luggage);
    }

    @Override
    public void deleteLuggage(Long id) {
        luggageRepo.deleteById(id);
    }

    @Override
    public Luggage findLuggage(Long id) {
        return luggageRepo.findById(id).orElse(null);
    }

    @Override
    public List<Luggage> findModelLuggage() {
        return luggageRepo.findModelLuggage();
    }
}
