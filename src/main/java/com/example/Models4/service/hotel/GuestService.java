package com.example.Models4.service.hotel;

import com.example.Models4.model.Guest;
import com.example.Models4.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService implements IGuestService{
    @Autowired
    private GuestRepository guestRepo;

    @Override
    public List<Guest> getGuests() {
        return guestRepo.findAll();
    }

    @Override
    public Guest saveGuest(Guest guest) {
        return guestRepo.saveAndFlush(guest);
    }

    @Override
    public void deleteGuest(Long guestCode) {
        guestRepo.deleteById(guestCode);
    }

    @Override
    public Guest findGuest(Long guestCode) {
        return guestRepo.findById(guestCode).orElse(null);
    }
}
