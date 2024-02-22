package com.example.Models4.service.hotel;

import com.example.Models4.model.Hotel;
import com.example.Models4.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService implements IHotelService {

    @Autowired
    private HotelRepository hotelRepo;

    @Override
    public List<Hotel> getHotels() {
        return hotelRepo.findAll();
    }

    @Override
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepo.saveAndFlush(hotel);
    }

    @Override
    public void deleteHotel(String hotelCode) {
        hotelRepo.deleteById(hotelCode);
    }

    @Override
    public Hotel findHotel(String hotelCode) {
        return hotelRepo.findById(hotelCode).orElse(null);
    }
}
