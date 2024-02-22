package com.example.Models4.service.hotel;

import com.example.Models4.model.Guest;

import java.util.List;

public interface IGuestService {
    public List<Guest> getGuests();

    public Guest saveGuest(Guest guest);

    public void deleteGuest(Long guestCode);

    public Guest findGuest(Long guestCode);
}
