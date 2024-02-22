package com.example.Models4.controller;

import com.example.Models4.model.Hotel;
import com.example.Models4.model.Room;
import com.example.Models4.service.hotel.IHotelService;
import com.example.Models4.service.hotel.IRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private IHotelService hotelService;

    @Autowired
    private IRoomService roomService;


    @Operation(summary = "Get all active hotels",
            description = "Get all active hotels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotels retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No active hotels found")
    })
    @GetMapping("/get")
//    PRIVATE: ADMIN
    public ResponseEntity<List<Hotel>> getHotels() {
        List<Hotel> hotels =
                hotelService.getHotels().stream().filter(
                        Hotel::getActiveFlag).toList();
        if(hotels.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(hotels,HttpStatus.OK);
        }

    }


    @Operation(summary = "Get hotel by ID",
            description = "Get hotel by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @GetMapping("/get/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<?> getHotel (@PathVariable String id){
        return hotelService.findHotel(id) == null ? new ResponseEntity<>(
                "There's no hotel with " + id + " hotelCode or Id",
                HttpStatus.NOT_FOUND):
                new ResponseEntity<>(hotelService.findHotel(id),HttpStatus.OK);
    }


    @Operation(summary = "Create hotel",
            description = "Create a new hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/create")
//    PRIVATE: ADMIN
    public ResponseEntity<String> saveHotel(@RequestBody Hotel hotel) {
        if(hotel == null){
            return new ResponseEntity<>("It's necessary a hotel",
                                        HttpStatus.BAD_REQUEST);
        }

        List<String> nullsParameters = new ArrayList<>();
        if(hotel.getHotelCode() == null){
            nullsParameters.add("hotelCode");
        }
        if(hotel.getName() == null){
            nullsParameters.add("name Hotel");
        }
        if(hotel.getPlace() == null){
            nullsParameters.add("place");
        }
        if(hotel.getCheckIn() == null){
            nullsParameters.add("check in");
        }
        if(hotel.getCheckOut() == null){
            nullsParameters.add("check out");
        }
        if(hotel.getActiveFlag() == null){
            nullsParameters.add("active Flag");
        }

        Hotel existingHotel =
                hotelService.findHotel(hotel.getHotelCode());
        if(existingHotel != null){
            return new ResponseEntity<>(
                    "The hotel already exists",
                    HttpStatus.BAD_REQUEST);
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

        List<Room> rooms = hotel.getRooms();
        rooms.forEach(room -> room.setId(null));

        hotel = hotelService.saveHotel(hotel);

        rooms = hotel.getRooms();
        if (!rooms.isEmpty()) {
            for (Room room : rooms) {
                room.setHotelName(hotel.getName());
                room.setPlace(hotel.getPlace());
                room.setHotel(hotel);
                if (room.getRoomType() <= 0 || room.getRoomType() > 4) {
                    return new ResponseEntity<>(
                            "Please the room can't be to " + room.getRoomType() + (room.getRoomType() > 1 ? " people" : " person"),
                            HttpStatus.BAD_REQUEST);
                }
                if (room.getRoomType() == 1) {
                    room.setRoomTypeString("Single");
                } else if (room.getRoomType() == 2) {
                    room.setRoomTypeString("Double");
                } else if (room.getRoomType() == 3) {
                    room.setRoomTypeString("Familiar");
                } else {
                    room.setRoomTypeString("Familiar");
                }
                if (room.getFromDate().isAfter(
                        room.getUntilDate()) || room.getFromDate().isBefore(
                        LocalDate.now()))
                {
                    return new ResponseEntity<>(
                            "It's necessary to enter a from Date " + " and has be " + "before the " + " until date and also after the " + "actual date inside the rooms",
                            HttpStatus.BAD_REQUEST);
                }

                roomService.saveRoom(room);

            }
        }

        return new ResponseEntity<>("Hotel and rooms created successfully",
                                    HttpStatus.OK);
    }


    @Operation(summary = "Disable hotel by ID",
            description = "Disable hotel and its rooms by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel and rooms disabled successfully"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @PostMapping("/disable/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<String> disableHotel (@PathVariable String id){
        Hotel hotel = hotelService.findHotel(id);
        if(hotel == null){
            return new ResponseEntity<>("There's no hotel with this code",
                                        HttpStatus.NOT_FOUND);
        }
        hotel.setActiveFlag(false);
//        Disable rooms too
        List<Room> rooms =
                roomService.getRooms().stream().filter(room -> room.getHotel().getHotelCode().equalsIgnoreCase(hotel.getHotelCode())).toList();
        if(!rooms.isEmpty()) {
            rooms.forEach(room -> {
                room.setActiveFlag(false);
                roomService.saveRoom(room);
            });
        }
        hotelService.saveHotel(hotel);
        return new ResponseEntity<>("Hotel and rooms disabled",HttpStatus.OK);
    }

    @Operation(summary = "Enable hotel by ID",
            description = "Enable hotel and its rooms by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel and rooms enabled successfully"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @PostMapping("/enable/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<String> enableHotel (@PathVariable String id){
        Hotel hotel = hotelService.findHotel(id);
        if(hotel == null){
            return new ResponseEntity<>("There's no hotel with this code",
                                        HttpStatus.NOT_FOUND);
        }
        hotel.setActiveFlag(true);
//        Disable rooms too
        List<Room> rooms =
                roomService.getRooms().stream().filter(room -> room.getHotel().getHotelCode().equalsIgnoreCase(hotel.getHotelCode())).toList();
        if(!rooms.isEmpty()) {
            rooms.forEach(room -> {
                room.setActiveFlag(true);
                roomService.saveRoom(room);
            });
        }
        hotelService.saveHotel(hotel);
        return new ResponseEntity<>("Hotel and rooms enabled",HttpStatus.OK);
    }


    @Operation(summary = "Update hotel by ID and Params",
            description = "Update hotel information by its ID and more Params" +
                    " like name, place, check in, check out... ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @PutMapping("/update/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<?> editHotel(
            @PathVariable String id,
            @RequestParam(value = "name", required = false) String updateName,
            @RequestParam(value = "place", required = false) String updatePlace,
            @RequestParam(value = "checkIn", required = false) String updateCheckIn,
            @RequestParam(value = "checkOut", required = false) String updateCheckOut,
            @RequestParam(value = "activeFlag", required = false) Boolean updateActiveFlag,
            @RequestParam(value = "description", required = false) String updateDescription) {

        Hotel hotel = hotelService.findHotel(id);
        if (hotel == null) {
            return new ResponseEntity<>("No hotel with hotel code: " + id +
                                                " found.", HttpStatus.NOT_FOUND);
        }
        hotel.setName(updateName != null ? updateName : hotel.getName());
        hotel.setPlace(updatePlace != null ? updatePlace : hotel.getPlace());
        hotel.setCheckIn(updateCheckIn != null ? updateCheckIn : hotel.getCheckIn());
        hotel.setCheckOut(updateCheckOut != null ? updateCheckOut : hotel.getCheckOut());
        hotel.setActiveFlag(updateActiveFlag != null ? updateActiveFlag : hotel.getActiveFlag());
        hotel.setDescription(updateDescription != null ? updateDescription : hotel.getDescription());


        hotelService.saveHotel(hotel);
        List<Room> rooms = hotel.getRooms();
        if(!rooms.isEmpty()) {
            for (Room room : rooms) {
                room.setHotelName(hotel.getName());
                room.setPlace(hotel.getPlace());
                room.setHotel(hotel);
                if (room.getRoomType() <= 0 || room.getRoomType() > 4) {
                    return new ResponseEntity<>(
                            "Please the room can't be to " + room.getRoomType() + (room.getRoomType()>1 ?" people":
                                    " person"),
                            HttpStatus.BAD_REQUEST);
                }
                if(room.getRoomType() == 1){
                    room.setRoomTypeString("Single");
                } else if (room.getRoomType() == 2){
                    room.setRoomTypeString("Double");
                } else if (room.getRoomType() == 3){
                    room.setRoomTypeString("Familiar");
                } else {
                    room.setRoomTypeString("Familiar");
                }
                if (room.getFromDate().isAfter(room.getUntilDate()) || room.getFromDate().isBefore(
                        LocalDate.now())) {
                    return new ResponseEntity<>(
                            "It's necessary to enter a from Date " + " and has be " +
                                    "before the " + " until date and also after the " +
                                    "actual date inside the rooms",
                            HttpStatus.BAD_REQUEST);
                }

                Room existingRoom = null;
                if(room.getId() != null) {
                    existingRoom = roomService.findRoom(room.getId());
                }
                if(existingRoom == null){
                    roomService.saveRoom(room);
                }
            }
        }

        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

}
