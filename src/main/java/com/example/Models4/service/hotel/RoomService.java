package com.example.Models4.service.hotel;

import com.example.Models4.model.Hotel;
import com.example.Models4.model.Room;
import com.example.Models4.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepo;

    @Override
    public List<Room> getRooms() {
        return roomRepo.findAll();
    }

    @Override
    public void saveRoom(Room room) {
        roomRepo.save(room);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepo.deleteById(id);
    }

    @Override
    public Room findRoom(Long id) {
        return roomRepo.findById(id).orElse(null);
    }

    @Override
    public List<Room> findAvailableRoomsByPlace(LocalDate fromDate, LocalDate toDate, Integer roomType, String place) {
        return roomRepo.findAvailableRoomsByPlace(place,fromDate,toDate,roomType);
    }

    @Override
    public List<Room> findAvailableRoomsByHotelName(LocalDate fromDate, LocalDate toDate, Integer roomType, String hotelName) {
        return roomRepo.findAvailableRoomsByHotelName(hotelName,fromDate,toDate,
                                                      roomType);
    }

    @Override
    public List<Room> findByHotel(Hotel hotel) {
        return roomRepo.findByHotel(hotel);
    }
}
