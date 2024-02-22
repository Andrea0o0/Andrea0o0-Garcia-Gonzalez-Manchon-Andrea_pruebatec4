package com.example.Models4.service.hotel;

import com.example.Models4.model.ReservedDate;
import com.example.Models4.model.Room;

import java.util.List;

public interface IReservedDateService {
    public List<ReservedDate> getReservedDates();

    public ReservedDate saveReservedDate(ReservedDate reservedDate);

    public void deleteReservedDate(Long reservedDateCode);

    public ReservedDate findReservedDate(Long reservedDateCode);

    public List<ReservedDate> findByRoom(Room room);
}
