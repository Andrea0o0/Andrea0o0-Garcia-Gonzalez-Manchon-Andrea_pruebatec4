package com.example.Models4.service.booking;

import com.example.Models4.model.Booking;
import com.example.Models4.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Override
    public List<Booking> getBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public void saveBooking(Booking booking) {
        bookingRepo.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepo.deleteById(id);
    }

    @Override
    public Booking findBooking(Long id) {
        return bookingRepo.findById(id).orElse(null);
    }
}