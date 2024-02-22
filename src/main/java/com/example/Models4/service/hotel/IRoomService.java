package com.example.Models4.service.hotel;

import com.example.Models4.model.Hotel;
import com.example.Models4.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    public List<Room> getRooms();

    public void saveRoom(Room room);

    public void deleteRoom(Long id);

    public Room findRoom(Long id);

    public List<Room> findAvailableRoomsByPlace(LocalDate fromDate, LocalDate toDate, Integer roomType, String place);

    public List<Room> findAvailableRoomsByHotelName(LocalDate fromDate, LocalDate toDate, Integer roomType, String hotelName);

    public List<Room> findByHotel(Hotel hotel);
}
