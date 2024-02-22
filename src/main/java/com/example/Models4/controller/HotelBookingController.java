package com.example.Models4.controller;

import com.example.Models4.model.*;
import com.example.Models4.service.booking.IHotelBookingService;
import com.example.Models4.service.hotel.IGuestService;
import com.example.Models4.service.hotel.IHotelService;
import com.example.Models4.service.hotel.IReservedDateService;
import com.example.Models4.service.hotel.IRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("/hotelBooking")
public class HotelBookingController {

    @Autowired
    private IHotelBookingService hotelBookingService;

    @Autowired
    private IHotelService hotelService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IReservedDateService reservedDateService;

    @Autowired
    private IGuestService guestService;


    @Operation(summary = "Retrieve all active hotel bookings",
            description = "Retrieve all active hotel bookings.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of HotelBooking objects"),
            @ApiResponse(responseCode = "204", description = "No hotel bookings found")
    })
    @GetMapping("/get")
//    PRIVATE
    public ResponseEntity<List<HotelBooking>> getHotelBookings() {
        List<HotelBooking> bookings = hotelBookingService.getHotelBookings().stream().filter(
                HotelBooking::getActiveFlag).toList();

        return (bookings.isEmpty()) ? new ResponseEntity<>(
                HttpStatus.NO_CONTENT) : new ResponseEntity<>(bookings,
                                                              HttpStatus.OK);
    }

    @Operation(summary = "Retrieve a hotel booking by ID",
            description = "Retrieve a hotel booking by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HotelBooking object"),
            @ApiResponse(responseCode = "404", description = "Hotel booking not found")
    })
    @GetMapping("/get/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<?> getHotelBooking(@PathVariable Long id) {
        return hotelBookingService.findHotelBooking(
                id) == null ? new ResponseEntity<>(
                "There's no hotelBooking with " + id + " Id",
                HttpStatus.NOT_FOUND) : new ResponseEntity<>(
                hotelBookingService.findHotelBooking(id), HttpStatus.OK);
    }


    @Operation(summary = "Create a new hotel booking",
            description = "Create a new hotel booking for the specified room.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Newly created HotelBooking object"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or parameters")
    })
    @PostMapping("/create/{roomId}")
//    PUBLIC Client has to create Booking
    public ResponseEntity<?> saveHotelBooking(
            @PathVariable Long roomId,
            @RequestBody HotelBooking hotelBooking) {

        if(hotelBooking.getPeopleNumber() == null ||
                roomId == null ||
                hotelBooking.getGuests() == null){
            return new ResponseEntity<>("Please enter all the values",
                                        HttpStatus.BAD_REQUEST);
        }

        if (hotelBooking.getFromDate() == null || hotelBooking.getUntilDate() == null) {
            return new ResponseEntity<>("It's a mandatory add the dates",
                                        HttpStatus.BAD_REQUEST);
        }
        if (hotelBooking.getFromDate().isAfter(hotelBooking.getUntilDate()) || hotelBooking.getFromDate().isBefore(LocalDate.now())) {
            return new ResponseEntity<>(
                    "It's necessary to enter a from Date " + " and has be " + "before the " + " until date and also after the " + "actual date",
                    HttpStatus.BAD_REQUEST);
        }

        HotelBooking existingBooking =
                hotelBookingService.findHotelBooking(roomId);
        if(existingBooking != null){
            return new ResponseEntity<>("This booking already exist",
                                        HttpStatus.BAD_REQUEST);
        }

        LocalDate fromDate = hotelBooking.getFromDate();
        LocalDate untilDate = hotelBooking.getUntilDate();

        long daysRange = ChronoUnit.DAYS.between(fromDate, untilDate);

        Room room = roomService.findRoom(roomId);
        if (room == null) {
            return new ResponseEntity<>("No room with this id",
                                        HttpStatus.NOT_FOUND);
        }

        hotelBooking.setPeopleNumber(room.getRoomType());
        hotelBooking.setTotalPrice(room.getPriceNight() * daysRange);

        if (hotelBooking.getGuests().isEmpty()) {
            return new ResponseEntity<>("The guest list is empty",
                                        HttpStatus.BAD_REQUEST);
        }
        if (hotelBooking.getGuests().size() > room.getRoomType()) {
            return new ResponseEntity<>(
                    "The guest list has to be equal to " + "the people's room type " + room.getRoomType(),
                    HttpStatus.BAD_REQUEST);
        }

        List<Guest> guests = hotelBooking.getGuests();
        for (Guest guest : guests) {
            guest.setId(null);
            if (guest.getName() == null || guest.getName().isEmpty() || guest.getName().isBlank() || guest.getLastName() == null || guest.getLastName().isEmpty() || guest.getLastName().isBlank() || guest.getEmail() == null || guest.getEmail().isEmpty() || guest.getEmail().isBlank()) {
                return new ResponseEntity<>(
                        "The name, last name and email " + "can't be null or be empty",
                        HttpStatus.BAD_REQUEST);
            }
        }

        hotelBooking.setRoom(room);

        List<LocalDate> datesToReserve = new ArrayList<>();
        for (int index = 0; index <= daysRange; index++) {
            datesToReserve.add(fromDate.plusDays(index));
        }

        List<ReservedDate> datesReserved = reservedDateService.findByRoom(room);

        List<LocalDate> repeatedDates = new ArrayList<>();
        for (LocalDate localDate : datesToReserve) {
            List<ReservedDate> validating = datesReserved.stream().filter(
                    dateR -> dateR.getDate().equals(localDate)).toList();
            if (!validating.isEmpty()) {
                repeatedDates.add(localDate);
            }
        }
        if (!repeatedDates.isEmpty()) {
            String datesString = repeatedDates.toString().replace("[",
                                                                  "").replace(
                    "]", "").replaceAll(",", "\n");
            return new ResponseEntity<>(
                    "Already exist at least one date " + "booked" + ", you" + " can't book this room, " + "it's booked \nThe " + "following dates are " + "already reserved: \n" + datesString,
                    HttpStatus.BAD_REQUEST);
        }
        for (LocalDate uniqueAddDate : datesToReserve) {
            if (uniqueAddDate.isBefore(
                    room.getUntilDate()) && uniqueAddDate.isAfter(
                    LocalDate.now()))
            {
                ReservedDate saveReserveDate = new ReservedDate();
                saveReserveDate.setRoom(room);
                saveReserveDate.setDate(uniqueAddDate);
                reservedDateService.saveReservedDate(saveReserveDate);
            }
        }

        HotelBooking hotelBookingSaved =
                hotelBookingService.saveHotelBooking(hotelBooking);

        guests.forEach(guestSaved -> {
            guestSaved.setHotelBooking(hotelBookingSaved);
            guestService.saveGuest(guestSaved);
        });


        return new ResponseEntity<>(hotelBookingSaved,HttpStatus.OK);
    }


    @Operation(summary = "Disable a hotel booking",
            description = "Disable a hotel booking by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HotelBooking disabled"),
            @ApiResponse(responseCode = "404", description = "Hotel booking not found")
    })
    @PostMapping("/disable/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<String> disableHotelBooking(@PathVariable Long id) {
        HotelBooking hotelBooking = hotelBookingService.findHotelBooking(
                id);
        if (hotelBooking == null) {
            return new ResponseEntity<>("There's no bookings with this code",
                                        HttpStatus.NOT_FOUND);
        }
        hotelBooking.setActiveFlag(false);
        hotelBookingService.saveHotelBooking(hotelBooking);
        return new ResponseEntity<>("HotelBooking disabled", HttpStatus.OK);
    }

    @Operation(summary = "Enable a hotel booking",
            description = "Enable a hotel booking by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HotelBooking enabled"),
            @ApiResponse(responseCode = "404", description = "Hotel booking not found")
    })
    @PostMapping("/enable/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<String> enableHotelBooking(@PathVariable Long id) {
        HotelBooking hotelBooking = hotelBookingService.findHotelBooking(
                id);
        if (hotelBooking == null) {
            return new ResponseEntity<>("There's no bookings with this code",
                                        HttpStatus.NOT_FOUND);
        }
        hotelBooking.setActiveFlag(true);
        hotelBookingService.saveHotelBooking(hotelBooking);
        return new ResponseEntity<>("HotelBooking enabled", HttpStatus.OK);
    }


    @Operation(summary = "Update a hotel booking",
            description = "Update a hotel booking by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated HotelBooking object"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or parameters"),
            @ApiResponse(responseCode = "404", description = "Hotel booking not found")
    })
    @PutMapping("/update/{id}")
    // PRIVATE: ADMIN
    public ResponseEntity<?> editHotelBooking(
            @PathVariable Long id,
            @RequestBody HotelBooking updateHotelBooking) {

        if(id == null){
            return new ResponseEntity<>("The id can't be null",
                                        HttpStatus.BAD_REQUEST);
        }
        HotelBooking hotelBooking = hotelBookingService.findHotelBooking(id);
        if (hotelBooking == null) {
            return new ResponseEntity<>("No hotelBooking with id " + id,
                                        HttpStatus.NOT_FOUND);
        }

        hotelBooking.setId(id);
        hotelBooking.setFromDate(updateHotelBooking.getFromDate() == null ? hotelBooking.getFromDate(): updateHotelBooking.getFromDate());
        hotelBooking.setUntilDate(updateHotelBooking.getUntilDate() == null ? hotelBooking.getUntilDate(): updateHotelBooking.getUntilDate());
        hotelBooking.setRoom((updateHotelBooking.getRoom() == null ||  updateHotelBooking.getRoom().getId() == null)?
                                     hotelBooking.getRoom(): updateHotelBooking.getRoom());
        hotelBooking.setPeopleNumber(updateHotelBooking.getPeopleNumber() == null ? hotelBooking.getPeopleNumber():updateHotelBooking.getPeopleNumber());
        List<Guest> noNullGuest = new ArrayList<>();
        if(updateHotelBooking.getGuests() != null){
            noNullGuest =
                    updateHotelBooking.getGuests().stream().filter(guestNull -> guestNull.getId() != null).toList();
        }
        hotelBooking.setGuests((updateHotelBooking.getGuests() == null || updateHotelBooking.getGuests().size() != noNullGuest.size()) ?
                hotelBooking.getGuests(): updateHotelBooking.getGuests());
        hotelBooking.setTotalPrice(updateHotelBooking.getTotalPrice() == null ? hotelBooking.getTotalPrice() : updateHotelBooking.getTotalPrice());


        if (hotelBooking.getFromDate() == null || hotelBooking.getUntilDate() == null) {
            return new ResponseEntity<>("It's a mandatory add the dates",
                                        HttpStatus.BAD_REQUEST);
        }
        if (hotelBooking.getFromDate().isAfter(hotelBooking.getUntilDate()) || hotelBooking.getFromDate().isBefore(LocalDate.now())) {
            return new ResponseEntity<>(
                    "It's necessary to enter a from Date " + " and has be " + "before the " + " until date and also after the " + "actual date",
                    HttpStatus.BAD_REQUEST);
        }

        Long roomId = hotelBooking.getRoom().getId();

        LocalDate fromDate = hotelBooking.getFromDate();
        LocalDate untilDate = hotelBooking.getUntilDate();

        long daysRange = ChronoUnit.DAYS.between(fromDate, untilDate);

        Room room = roomService.findRoom(roomId);
        if (room == null) {
            return new ResponseEntity<>("No room with this id",
                                        HttpStatus.NOT_FOUND);
        }

        hotelBooking.setPeopleNumber(room.getRoomType());
        hotelBooking.setTotalPrice(room.getPriceNight() * daysRange);

        if (hotelBooking.getGuests().isEmpty()) {
            return new ResponseEntity<>("The guest list is empty",
                                        HttpStatus.BAD_REQUEST);
        }
        if (hotelBooking.getGuests().size() > room.getRoomType()) {
            return new ResponseEntity<>(
                    "The guest list has to be equal to " + "the people's room type " + room.getRoomType(),
                    HttpStatus.BAD_REQUEST);
        }

        List<Guest> guests = hotelBooking.getGuests();
        for (Guest guest : guests) {
            guest.setId(null);
            if (guest.getName() == null || guest.getName().isEmpty() || guest.getName().isBlank() || guest.getLastName() == null || guest.getLastName().isEmpty() || guest.getLastName().isBlank() || guest.getEmail() == null || guest.getEmail().isEmpty() || guest.getEmail().isBlank()) {
                return new ResponseEntity<>(
                        "The name, last name and email " + "can't be null or be empty",
                        HttpStatus.BAD_REQUEST);
            }
        }

        hotelBooking.setRoom(room);

        List<LocalDate> datesToReserve = new ArrayList<>();
        for (int index = 0; index <= daysRange; index++) {
            datesToReserve.add(fromDate.plusDays(index));
        }

        List<ReservedDate> datesReserved = reservedDateService.findByRoom(room);

        List<LocalDate> repeatedDates = new ArrayList<>();
        for (LocalDate localDate : datesToReserve) {
            List<ReservedDate> validating = datesReserved.stream().filter(
                    dateR -> dateR.getDate().equals(localDate)).toList();
            if (!validating.isEmpty()) {
                repeatedDates.add(localDate);
            }
        }
        if (repeatedDates.isEmpty()) {
            for (LocalDate uniqueAddDate : datesToReserve) {
                if (uniqueAddDate.isBefore(
                        room.getUntilDate()) && uniqueAddDate.isAfter(
                        LocalDate.now()))
                {
                    ReservedDate saveReserveDate = new ReservedDate();
                    saveReserveDate.setRoom(room);
                    saveReserveDate.setDate(uniqueAddDate);
                    reservedDateService.saveReservedDate(saveReserveDate);
                }
            }
        }

        HotelBooking hotelBookingSaved =
                hotelBookingService.saveHotelBooking(hotelBooking);

        guests.forEach(guestSaved -> {
            guestSaved.setHotelBooking(hotelBookingSaved);
            guestService.saveGuest(guestSaved);
        });



        hotelBookingService.saveHotelBooking(hotelBooking);
        return new ResponseEntity<>(hotelBooking, HttpStatus.OK);
    }

}
