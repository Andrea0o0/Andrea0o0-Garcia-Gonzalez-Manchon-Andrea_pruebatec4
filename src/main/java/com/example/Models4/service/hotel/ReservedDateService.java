package com.example.Models4.service.hotel;

import com.example.Models4.model.ReservedDate;
import com.example.Models4.model.Room;
import com.example.Models4.repository.ReservedDateRepository;
import com.example.Models4.service.hotel.IReservedDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservedDateService implements IReservedDateService {
    @Autowired
    private ReservedDateRepository reservedDateRepo;

    @Override
    public List<ReservedDate> getReservedDates() {
        return reservedDateRepo.findAll();
    }

    @Override
    public ReservedDate saveReservedDate(ReservedDate reservedDate) {
        return reservedDateRepo.saveAndFlush(reservedDate);
    }

    @Override
    public void deleteReservedDate(Long reservedDateCode) {
        reservedDateRepo.deleteById(reservedDateCode);
    }

    @Override
    public ReservedDate findReservedDate(Long reservedDateCode) {
        return reservedDateRepo.findById(reservedDateCode).orElse(null);
    }

    @Override
    public List<ReservedDate> findByRoom(Room room) {
        return reservedDateRepo.findByRoom(room);
    }
}
