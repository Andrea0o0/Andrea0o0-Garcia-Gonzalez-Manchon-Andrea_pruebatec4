package com.example.Models4.service.booking;

import com.example.Models4.model.HotelBooking;
import com.example.Models4.repository.HotelBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelBookingService implements IHotelBookingService {

    @Autowired
    private HotelBookingRepository hotelBookingRepo;

    @Override
    public List<HotelBooking> getHotelBookings() {
        return hotelBookingRepo.findAll();
    }

    @Override
    public HotelBooking saveHotelBooking(HotelBooking hotelBooking) {
        return hotelBookingRepo.saveAndFlush(hotelBooking);
    }

    @Override
    public void deleteHotelBooking(Long hotelBooking) {
        hotelBookingRepo.deleteById(hotelBooking);
    }

    @Override
    public HotelBooking findHotelBooking(Long id) {
        return hotelBookingRepo.findById(id).orElse(null);
    }
}