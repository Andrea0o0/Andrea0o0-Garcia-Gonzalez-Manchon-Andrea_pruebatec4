package com.example.Models4.controller;

import com.example.Models4.dto.HotelRoomDto;
import com.example.Models4.model.Hotel;
import com.example.Models4.model.ReservedDate;
import com.example.Models4.model.Room;
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
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private IRoomService roomService;
    @Autowired
    private IHotelService hotelService;

    @Autowired
    private IReservedDateService reservedDateService;


    @Operation(summary = "Get all rooms", description = "Get all rooms with active flag true")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rooms retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No rooms available")
    })
    @GetMapping("/get")
//    PRIVATE
    public ResponseEntity<List<Room>> getRooms() {
        List<Room> rooms =
                roomService.getRooms().stream().filter(
                        Room::getActiveFlag).toList();

        return (rooms.isEmpty()) ? new ResponseEntity<>(
                HttpStatus.NO_CONTENT) : new ResponseEntity<>(rooms,
                                                              HttpStatus.OK);
    }

    @Operation(summary = "Get room by ID", description = "Get room by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room found successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @GetMapping("/get/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<?> getRoom (@PathVariable Long id){
        return roomService.findRoom(id) == null ? new ResponseEntity<>(
                "There's no room with " + id + " id",
                HttpStatus.NOT_FOUND):
                new ResponseEntity<>(roomService.findRoom(id),HttpStatus.OK);
    }

    @Operation(summary = "Get rooms by hotel ID", description = "Get rooms associated with a specific hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rooms retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Hotel or rooms not found")
    })
    @GetMapping("/get/hotel/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<?> getRoomsByHotel (@PathVariable String id){
        if(id == null){
            return new ResponseEntity<>("The "+ id + " hotel it's a mandatory",
                                        HttpStatus.NOT_FOUND);
        }
        Hotel hotel = hotelService.findHotel(id);
        if(hotel == null){
            return new ResponseEntity<>("No hotel with this id "+ id,
                                        HttpStatus.NOT_FOUND);
        }
        return roomService.findByHotel(hotel) == null ? new ResponseEntity<>(
                "There's no room for " + hotel.getName(),
                HttpStatus.NOT_FOUND):
                new ResponseEntity<>(roomService.findByHotel(hotel),HttpStatus.OK);
    }

    @Operation(summary = "Create a new room",
            description = "Create a new room and associate it with a hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @PostMapping("/create")
//    PRIVATE: ADMIN
    public ResponseEntity<String> saveRoom(@RequestBody Room room,
    @RequestParam(value = "hotelId", required = false) String hotelId) {
        if(room == null){
            return new ResponseEntity<>("It's necessary a room",
                                        HttpStatus.BAD_REQUEST);
        }

        List<String> nullsParameters = new ArrayList<>();
        if(room.getRoomNumber() == null){
            nullsParameters.add("room number");
        }
        if(room.getRoomType() == null){
            nullsParameters.add("room type");
        }
        if(room.getRoomTypeString() == null){
            nullsParameters.add("room type String");
        }
        if(room.getPriceNight() == null){
            nullsParameters.add("price night");
        }
        if(room.getFromDate() == null){
            nullsParameters.add("from date");
        }
        if(room.getUntilDate() == null){
            nullsParameters.add("until date");
        }
        if(room.getActiveFlag() == null){
            nullsParameters.add("active Flag");
        }
        if(room.getReservedDates() == null){
            nullsParameters.add("reserved Dates");
        }
        if(room.getDescription() == null){
            nullsParameters.add("description");
        }
        if(room.getHotel() == null && hotelId == null){
            nullsParameters.add("hotel or hotelId");
        }

        if(!nullsParameters.isEmpty()){
            String nullsString =
                    nullsParameters.toString().replace("[","").replace("]","");
            if(nullsParameters.size() == 1){
                return new ResponseEntity<>(
                        "The following value is missing from the hotel model:" +
                                " " + nullsString,
                        HttpStatus.BAD_REQUEST);
            } else {
                nullsString = nullsString.substring(0,nullsString.lastIndexOf(
                        ",")) +
                        " and" +
                        nullsString.substring(nullsString.lastIndexOf(",")+1);
                return new ResponseEntity<>(
                        "The following values are missing from the hotel " +
                                "model: " + nullsString,
                        HttpStatus.BAD_REQUEST);
            }

        }

        if (room.getFromDate().isAfter(room.getUntilDate()) || room.getFromDate().isBefore(
                LocalDate.now())) {
            return new ResponseEntity<>(
                    "It's necessary to enter a from Date " + " and has be " +
                            "before the " + " until date and also after the " +
                            "actual date",
                    HttpStatus.BAD_REQUEST);
        }

        Room existingRoom = null;
        if(room.getId() != null) {
            existingRoom = roomService.findRoom(room.getId());
        }
        if(existingRoom != null){
            return new ResponseEntity<>(
                    "The room already exist",
                    HttpStatus.BAD_REQUEST);
        }

        if(hotelId == null){
            Hotel hotel = room.getHotel();
            if(hotel == null){
                return new ResponseEntity<>("The hotel must be inside the " +
                                                    "body, or inside params " +
                                                    "hotelId",
                                            HttpStatus.BAD_REQUEST);
            }
            hotelId = hotel.getHotelCode();
        }
        Hotel existingHotel =
                hotelService.findHotel(hotelId);
        if(existingHotel == null){
            return new ResponseEntity<>(
                    "The hotel don't exist",
                    HttpStatus.BAD_REQUEST);
        }

        if (room.getRoomType() <= 0 || room.getRoomType() > 4) {
            return new ResponseEntity<>(
                    "Please the room can't be to " + room.getRoomType() + (room.getRoomType()>1 ?" people":
                    " person"),
                    HttpStatus.BAD_REQUEST);
        }

        switch (room.getRoomType()) {
            case 1:
                room.setRoomTypeString("Single");
                break;
            case 2:
                room.setRoomTypeString("Double");
                break;
            case 3:
                room.setRoomTypeString("Suite");
                break;
            case 4:
                room.setRoomTypeString("Family");
                break;
        }

        room.setHotelName(existingHotel.getName());
        room.setPlace(existingHotel.getPlace());
        room.setHotel(existingHotel);

        roomService.saveRoom(room);
        return new ResponseEntity<>("Room created successfully",
                                    HttpStatus.OK);
    }

    @Operation(summary = "Disable a room by ID",
            description = "Disable a room with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room disabled successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @PostMapping("/disable/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<String> disableRoom (@PathVariable Long id){
        Room room = roomService.findRoom(id);
        if(room == null){
            return new ResponseEntity<>("There's no room with this code",
                                        HttpStatus.NOT_FOUND);
        }
        room.setActiveFlag(false);
        roomService.saveRoom(room);
        return new ResponseEntity<>("Room disabled",HttpStatus.OK);
    }

    @Operation(summary = "Enable a room by ID",
            description = "Enable a room with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room enabled successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @PostMapping("/enable/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<String> enableRoom (@PathVariable Long id){
        Room room = roomService.findRoom(id);
        if(room == null){
            return new ResponseEntity<>("There's no room with this code",
                                        HttpStatus.NOT_FOUND);
        }
        room.setActiveFlag(true);
        roomService.saveRoom(room);
        return new ResponseEntity<>("Room enabled",HttpStatus.OK);
    }


    @Operation(summary = "Update a room by ID",
            description = "Update details of a room with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @PutMapping("/update/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<?> editRoom(
            @PathVariable Long id,
            @RequestBody Room updatedRoom) {

        Room room = roomService.findRoom(id);
        if (room == null) {
            return new ResponseEntity<>("No room with ID: " + id + " found.", HttpStatus.NOT_FOUND);
        }

        room.setRoomNumber(updatedRoom.getRoomNumber() != null ? updatedRoom.getRoomNumber() : room.getRoomNumber());
        room.setRoomType(updatedRoom.getRoomType() != null ? updatedRoom.getRoomType() : room.getRoomType());
        room.setRoomTypeString(updatedRoom.getRoomTypeString() != null ? updatedRoom.getRoomTypeString() : room.getRoomTypeString());
        room.setPriceNight(updatedRoom.getPriceNight() != null ? updatedRoom.getPriceNight() : room.getPriceNight());
        room.setFromDate(updatedRoom.getFromDate() != null ? updatedRoom.getFromDate() : room.getFromDate());
        room.setUntilDate(updatedRoom.getUntilDate() != null ? updatedRoom.getUntilDate() : room.getUntilDate());
        room.setActiveFlag(updatedRoom.getActiveFlag() != null ? updatedRoom.getActiveFlag() : room.getActiveFlag());
        room.setDescription(updatedRoom.getDescription() != null ? updatedRoom.getDescription() : room.getDescription());
        room.setReservedDates(updatedRoom.getReservedDates() != null ? updatedRoom.getReservedDates() : room.getReservedDates());

        if (room.getFromDate().isAfter(room.getUntilDate()) || room.getFromDate().isBefore(
                LocalDate.now())) {
            return new ResponseEntity<>(
                    "It's necessary to enter a from Date " + " and has be " + "before the " + " until date and also after the " + "actual date",
                    HttpStatus.BAD_REQUEST);
        }
        roomService.saveRoom(room);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @Operation(summary = "Search rooms",
            description = "Search for available rooms based on various parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rooms retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
            @ApiResponse(responseCode = "404", description = "No rooms found")
    })
    @GetMapping("/search")
//    PUBLIC
    public ResponseEntity<?> searchRooms(
            @RequestParam(value = "place", required = false) String place,
            @RequestParam(value = "hotel", required = false) String hotelName,
            @RequestParam(value = "fromDate", required = false) LocalDate fromDate,
            @RequestParam(value = "untilDate", required = false) LocalDate untilDate,
            @RequestParam(value = "nights", required = false) Integer nights,
            @RequestParam(value = "maxPriceNight", required = false) Double maxPriceNight,
            @RequestParam(value = "people", required = false) Integer people) {


        if (place == null) {
            if (hotelName == null) {
                return new ResponseEntity<>(
                        "It's a mandatory enter the place or the hotelName on" + " " + "the search hotels",
                        HttpStatus.BAD_REQUEST);
            }
        }
        if (fromDate == null || untilDate == null) {
            return new ResponseEntity<>("It's a mandatory add the dates",
                                        HttpStatus.BAD_REQUEST);
        }
        if (fromDate.isAfter(untilDate) || fromDate.isBefore(LocalDate.now())) {
            return new ResponseEntity<>(
                    "It's necessary to enter a from Date " + " and has be " + "before the " + " until date and also after the " + "actual date",
                    HttpStatus.BAD_REQUEST);
        }

        if (people == null) {
            return new ResponseEntity<>("It's a mandatory add the rooms",
                                        HttpStatus.BAD_REQUEST);
        }
        if (people > 4) {
            return new ResponseEntity<>(
                    "You can't create a search for more " + "than 4 people",
                    HttpStatus.BAD_REQUEST);
        }

        Hotel hotel = null;
        if (hotelName != null) {
            List<Hotel> allHotels = hotelService.getHotels();
            Hotel matchHotel = allHotels.stream().filter(
                    hotelMatch -> hotelMatch.getName().equalsIgnoreCase(
                            hotelName)).findFirst().orElse(null);
            Hotel containsHotel = allHotels.stream().filter(
                    hotelContains -> hotelContains.getName().contains(
                            hotelName)).findFirst().orElse(null);

            if (matchHotel == null && containsHotel == null) {
                return new ResponseEntity<>(
                        "The hotel name don't exist and " + "also any hotel contains " + "the name",
                        HttpStatus.NOT_FOUND);
            } else if (matchHotel != null) {
                hotel = matchHotel;
            } else {
                hotel = containsHotel;
            }
        }

        String fromDateString = fromDate.getDayOfMonth() + "-" + fromDate.getMonthValue() + "-" + fromDate.getYear();
        String untilDateString = untilDate.getDayOfMonth() + "-" + untilDate.getMonthValue() + "-" + untilDate.getYear();

        long daysRange = ChronoUnit.DAYS.between(fromDate, untilDate) ;

        if (nights != null) {
            if (daysRange < nights) {
                return new ResponseEntity<>(
                        "It's necessary to enter a valid " + "number of " +
                                "nights," + " because " + "between " + fromDateString + " to " + untilDateString + " are " + (daysRange) + (daysRange > 1 ? " days" : "day"),
                        HttpStatus.BAD_REQUEST);
            }
        }

        if (nights == null) {
            nights = (int) daysRange - 1;
        }

//          STRUCTURE
//            "fromDate to untilDate":
//                      |_________________ "Hotel":
//                      |                        |_________"Options Room 1"
//                      |                        |_________"Options Room 2"
//                      |
//                      |_________________ "Hotel 2":
//                                               |_________"Options Room 1"
//                                               |_________"Options Room 2"

        Map<String, List<HotelRoomDto>> datesHotelRooms = new HashMap<>();
        for (int i = 0; i <= (daysRange - nights); i++) {
            LocalDate startDate = fromDate.plusDays(i);
            LocalDate finalDate = startDate.plusDays(nights);
            List<LocalDate> datesToReserve = new ArrayList<>();
            for (int index = 0; index <= (nights + 1); index++) {
                datesToReserve.add(startDate.plusDays(index));
            }

            List<HotelRoomDto> roomsAvailableHotel = new ArrayList<>();

            List<Room> roomsHotel;
            if (hotel != null) {
//                    Rooms match with:
//                    · HOTEL NAME,
//                    · NUMBER TYPE ROOM EQUAL OR LESS,
//                    · AVAILABLE FROM DATE TO UNTIL DATE

                roomsHotel = roomService.findAvailableRoomsByHotelName(
                        startDate, finalDate, people, hotel.getName());
                if (roomsHotel == null) {
                    return new ResponseEntity<>(
                            "There's no rooms " + "available for " + "the Hotel " + hotel.getName(),
                            HttpStatus.NOT_FOUND);
                }
            } else {
                roomsHotel = roomService.findAvailableRoomsByPlace(startDate,
                                                                   finalDate,
                                                                   people,
                                                                   place);
                if (roomsHotel == null) {
                    return new ResponseEntity<>(
                            "There's no hotels and rooms available for " + place,
                            HttpStatus.NOT_FOUND);
                }
            }
            List<Room> availableRooms = new ArrayList<>();
            for (Room roomHotel : roomsHotel) {
                List<ReservedDate> reservedDates =
                        reservedDateService.findByRoom(roomHotel);
                if (reservedDates.isEmpty()) {
                    availableRooms.add(roomHotel);
                } else {
                    AtomicInteger reserved = new AtomicInteger();
                    for (LocalDate dateToReserve : datesToReserve) {
                        for (ReservedDate reservedD : reservedDates) {
                            if (reservedD.getDate().equals(dateToReserve)) {

                                reserved.getAndIncrement();
                                break;
                            }
                        }
                    }
                    if (reserved.get() == 0) {
                        availableRooms.add(roomHotel);
                    }
                }
            }
            if (!availableRooms.isEmpty()) {
                List<Hotel> uniqueHotelsRoom = new ArrayList<>();
                for (Room availableRoom : availableRooms) {
                    Hotel hotelRoom = availableRoom.getHotel();
                    if (hotelRoom != null) {
                        if (!uniqueHotelsRoom.contains(
                                hotelRoom))
                        {
                            uniqueHotelsRoom.add(hotelRoom);
                        }
                    }
                }
                for (Hotel uniqueHotel : uniqueHotelsRoom) {
                    List<Room> roomsUniqueHotel = availableRooms.stream().filter(
                            roomH -> roomH.getHotel().equals(
                                    uniqueHotel)).toList();
                    if(maxPriceNight != null && !roomsUniqueHotel.isEmpty()){
                        roomsUniqueHotel =
                                roomsUniqueHotel.stream().filter(roomU -> roomU.getPriceNight()<=maxPriceNight).toList();
                    }
                    if(!roomsUniqueHotel.isEmpty()) {
                        HotelRoomDto hotelAndRooms = getHotelRoomDto(
                                uniqueHotel, roomsUniqueHotel);
                        roomsAvailableHotel.add(hotelAndRooms);
                    }
                }
            }
            datesHotelRooms.put(startDate + " to " + finalDate,
                                roomsAvailableHotel);
        }
        if (datesHotelRooms.isEmpty()) {
            return new ResponseEntity<>("There's no results for your search",
                                        HttpStatus.NOT_FOUND);
        } else {
            Set<String> keys = datesHotelRooms.keySet();
            List<String> sortedKeys = new ArrayList<>(keys);
            Collections.sort(sortedKeys);
            Map<String, List<HotelRoomDto>> datesHotelRoomSorted =
                    new HashMap<>();
            for(String key : sortedKeys){
                datesHotelRoomSorted.put(key,datesHotelRooms.get(key));
            }

            return new ResponseEntity<>(datesHotelRoomSorted, HttpStatus.OK);
        }
    }

    private static HotelRoomDto getHotelRoomDto(Hotel uniqueHotel, List<Room> roomsUniqueHotel) {
        HotelRoomDto hotelAndRooms = new HotelRoomDto();
        hotelAndRooms.setAvailableRooms(roomsUniqueHotel);
        hotelAndRooms.setHotelCode(uniqueHotel.getHotelCode());
        hotelAndRooms.setName(uniqueHotel.getName());
        hotelAndRooms.setPlace(uniqueHotel.getPlace());
        hotelAndRooms.setCheckIn(uniqueHotel.getCheckIn());
        hotelAndRooms.setCheckOut(uniqueHotel.getCheckOut());
        hotelAndRooms.setActiveFlag(uniqueHotel.getActiveFlag());
        hotelAndRooms.setDescription(uniqueHotel.getDescription());
        return hotelAndRooms;
    }

    @Operation(summary = "Add reserved dates for a room",
            description = "Add reserved dates for a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dates reserved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
            @ApiResponse(responseCode = "404", description = "Room or hotel not found")
    })
    @GetMapping("/reservedDates/{id}")
//    PUBLIC
    public ResponseEntity<?> changeDatesReserved(
            @PathVariable Long id,
            @RequestParam(value = "date", required = false) LocalDate date,
            @RequestBody List<LocalDate> dates
    ) {
        if (id == null) {
            return new ResponseEntity<>("Mandatory enter the hotelCode",
                                        HttpStatus.BAD_REQUEST);
        }

        Room room = roomService.findRoom(id);
        if (room == null) {
            return new ResponseEntity<>("Invalid room id, no room found",
                                        HttpStatus.NOT_FOUND);
        }

        if (room.getHotel() == null) {
            return new ResponseEntity<>("Sorry in this room not exist any linked hotel",
                                        HttpStatus.BAD_REQUEST);
        }

        Hotel hotel = hotelService.findHotel(room.getHotel().getHotelCode());
        if (hotel == null) {
            return new ResponseEntity<>("Invalid hotel code, no hotel found",
                                        HttpStatus.NOT_FOUND);
        }

        List<ReservedDate> datesReserved = reservedDateService.findByRoom(room);

        if (date == null && dates == null) {
            return new ResponseEntity<>("Enter date or dates",
                                        HttpStatus.BAD_REQUEST);
        }

        if (date != null) {
            for (ReservedDate reservedDate : datesReserved) {
                if (date.equals(reservedDate.getDate())) {
                    return new ResponseEntity<>("Already exists this date",
                                                HttpStatus.BAD_REQUEST);
                }
            }
            if(date.isBefore(
                    room.getUntilDate()) && date.isAfter(
                    LocalDate.now())){
            ReservedDate saveReserveDate = new ReservedDate();
            saveReserveDate.setRoom(room);
            saveReserveDate.setDate(date);
            reservedDateService.saveReservedDate(saveReserveDate);
            } else {
                return new ResponseEntity<>("Enter dates between now and the " +
                                                    "until date from the room",
                                            HttpStatus.BAD_REQUEST);
            }
        }

        if (date == null) {
            List<LocalDate> uniqueAddDates = new ArrayList<>();
            for (LocalDate localDate : dates) {
                List<ReservedDate> validating = datesReserved.stream().filter(
                        dateR -> dateR.getDate().equals(localDate)).toList();
                if (validating.isEmpty()) {
                    uniqueAddDates.add(localDate);
                }
            }
            if(uniqueAddDates.isEmpty()){
                return new ResponseEntity<>("Already exist all the dates",
                                            HttpStatus.BAD_REQUEST);
            }
            for (LocalDate uniqueAddDate : uniqueAddDates) {
                if (uniqueAddDate.isBefore(
                        room.getUntilDate()) && uniqueAddDate.isAfter(
                        LocalDate.now())) {
                    ReservedDate saveReserveDate = new ReservedDate();
                    saveReserveDate.setRoom(room);
                    saveReserveDate.setDate(uniqueAddDate);
                    reservedDateService.saveReservedDate(saveReserveDate);
                }
            }
        }


        datesReserved = reservedDateService.findByRoom(room);
        StringBuilder responseMessage = new StringBuilder("Modified the dates reserved from the room:\n");

        datesReserved.forEach(dateRead -> responseMessage.append(dateRead.toString()).append("\n"));

        return new ResponseEntity<>(responseMessage.toString(), HttpStatus.OK);
    }


    @Operation(summary = "Delete and add new reserved dates for a room",
            description = "Delete existing reserved dates and add new ones for a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dates updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
            @ApiResponse(responseCode = "404", description = "Room or hotel not found")
    })
    @GetMapping("/newReservedDates/{id}")
//    PUBLIC
    public ResponseEntity<?> deleteAndNewDatesReserved(
            @PathVariable Long id,
            @RequestBody List<LocalDate> updateListDates
    ) {
        if (id == null) {
            return new ResponseEntity<>("Mandatory enter the hotelCode",
                                        HttpStatus.BAD_REQUEST);
        }

        Room room = roomService.findRoom(id);
        if (room == null) {
            return new ResponseEntity<>("Invalid room id, no room found",
                                        HttpStatus.NOT_FOUND);
        }

        if (room.getHotel() == null) {
            return new ResponseEntity<>("Sorry in this room not exist any linked hotel",
                                        HttpStatus.BAD_REQUEST);
        }

        Hotel hotel = hotelService.findHotel(room.getHotel().getHotelCode());
        if (hotel == null) {
            return new ResponseEntity<>("Invalid hotel code, no hotel found",
                                        HttpStatus.NOT_FOUND);
        }

        List<ReservedDate> datesReserved = reservedDateService.findByRoom(room);

        if (updateListDates == null) {
            return new ResponseEntity<>("Enter date, dates or newDates",
                                        HttpStatus.BAD_REQUEST);
        }

        for (ReservedDate dateToDelete : datesReserved) {
            reservedDateService.deleteReservedDate(dateToDelete.getId());
        }
        List<LocalDate> uniqueDates = new ArrayList<>();
        updateListDates.forEach(upDate -> {
            if (upDate.isBefore(
                    room.getUntilDate()) && upDate.isAfter(
                    LocalDate.now()))
            {
                if (!uniqueDates.contains(upDate)) {
                    uniqueDates.add(upDate);
                }
            }
        });
        for (LocalDate newDate : uniqueDates) {
            ReservedDate saveReserveDate = new ReservedDate();
            saveReserveDate.setRoom(room);
            saveReserveDate.setDate(newDate);
            reservedDateService.saveReservedDate(saveReserveDate);
        }

        datesReserved = reservedDateService.findByRoom(room);
        StringBuilder responseMessage = new StringBuilder("Modified the dates reserved from the room:\n");

        datesReserved.forEach(dateRead -> responseMessage.append(dateRead.toString()).append("\n"));

        return new ResponseEntity<>(responseMessage.toString(), HttpStatus.OK);
    }



}
