package com.example.Models4.repository;

import com.example.Models4.model.Hotel;
import com.example.Models4.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    @Query("SELECT r FROM Room r INNER JOIN r.hotel h " +
            "WHERE h.place = :place " +
            "AND r.fromDate <= :toDate " +
            "AND r.untilDate >= :fromDate " +
            "AND r.roomType <= :roomType")
    List<Room> findAvailableRoomsByPlace(
            @Param("place") String place,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("roomType") Integer roomType);

    @Query("SELECT r FROM Room r INNER JOIN r.hotel h " +
            "WHERE h.name = :hotelName " +
            "AND r.fromDate <= :toDate " +
            "AND r.untilDate >= :fromDate " +
            "AND r.roomType <= :roomType")
    List<Room> findAvailableRoomsByHotelName(
            @Param("hotelName") String hotelName,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("roomType") Integer roomType);

    @Query("SELECT r FROM Room r WHERE r.hotel = ?1")
    List<Room> findByHotel(Hotel hotel);

}
