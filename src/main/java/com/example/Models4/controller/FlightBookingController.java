package com.example.Models4.controller;

import com.example.Models4.model.*;
import com.example.Models4.service.booking.IFlightBookingService;
import com.example.Models4.service.flight.IFlightService;
import com.example.Models4.service.passengersLuggageSeat.ILuggageService;
import com.example.Models4.service.passengersLuggageSeat.IPassengerService;
import com.example.Models4.service.passengersLuggageSeat.ISeatService;
import com.example.Models4.service.passengersLuggageSeat.LuggageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flightBooking")
public class FlightBookingController {

    @Autowired
    private IFlightBookingService flightBookingService;

    @Autowired
    private IFlightService flightService;

    @Autowired
    private IPassengerService passengerService;

    @Autowired
    private ILuggageService luggageService;

    @Autowired
    private ISeatService seatService;



    @GetMapping("/get")
//    PRIVATE: ADMIN
    public ResponseEntity<List<FlightBooking>> getFlightBookings() {
        List<FlightBooking> bookings =
                flightBookingService.getFlightBookings().stream().filter(
                FlightBooking::getActiveFlag).toList();

        return (bookings.isEmpty()) ? new ResponseEntity<>(
                HttpStatus.NO_CONTENT) : new ResponseEntity<>(bookings,
                                                              HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<?> getFlightBooking(@PathVariable Long id) {
        return flightBookingService.findFlightBooking(id) == null ? new ResponseEntity<>(
                "There's no flightBooking with " + id + " Id",
                HttpStatus.NOT_FOUND) : new ResponseEntity<>(
                flightBookingService.findFlightBooking(id), HttpStatus.OK);
    }

    @PostMapping("/create")
//    PUBLIC
//    it should be PRIVATE, but we don't have users to keep inside the model
    public ResponseEntity<String> saveFlightBooking(@RequestBody FlightBooking flightBooking) {

        if (flightBooking == null) {
            return new ResponseEntity<>("It's necessary a flightBooking body",
                                        HttpStatus.BAD_REQUEST);
        }

        String flightIdOneWay =
                flightBooking.getFlightOneWay().getFlightNumber();
        String flightIdReturn =
                flightBooking.getFlightReturn().getFlightNumber();

        if(flightIdOneWay == null || flightIdOneWay.isEmpty()){
            return new ResponseEntity<>("It's necessary at least one flight " +
                                                "inside the flightBooking",
                                        HttpStatus.BAD_REQUEST);
        }

        Flight flightOneWay =
                flightService.findFlight(flightIdOneWay);
        Flight flightReturn = flightService.findFlight(flightIdReturn);

        if(flightOneWay == null){
            return new ResponseEntity<>("The flight OneWay don't exist",
                                        HttpStatus.NOT_FOUND);
        }
        Boolean bookingType = flightBooking.getBookingType();
        if(bookingType == null ){
            return new ResponseEntity<>("The type of the booking it's a " +
                                                "mandatory",
                                        HttpStatus.BAD_REQUEST);
        }
//        bookingType = TRUE  --> means -> OneWayFlight & ReturnFlight
//        bookingType = FALSE --> means -> Only oneWayFlight
        if(bookingType){
            if(flightReturn == null){
                return new ResponseEntity<>("There must be a return flight " +
                                                    "because the bookingType " +
                                                    "is true and means it " +
                                                    "must be a round trip " +
                                                    "flightBooking.",
                                            HttpStatus.NOT_FOUND);
            }
        }
        if(flightBooking.getInfoPassengers() == null || flightBooking.getInfoPassengers().isEmpty()){
            return new ResponseEntity<>("The info from the passengers are " +
                                                "wrong",HttpStatus.BAD_REQUEST);
        }

        List<Passenger> infoPassengers = flightBooking.getInfoPassengers();
        Double passengersPrices = 0.0;

        for(Passenger passenger : infoPassengers ){

            // Checking if exist the luggageList inside the FlightBooking
            List<Luggage> luggageSelected = passenger.getLuggageList();
            List<Luggage> luggageModelList = luggageService.findModelLuggage();
            // if it's not null or empty luggageSelected
            double luggageTotalPrice = (double) 0;
            if( luggageSelected != null || !luggageSelected.isEmpty()){
//                In case that are more luggage that already exist: is
//                impossible
                if(luggageSelected.size() >= luggageModelList.size()){
                    return new ResponseEntity<>("You cannot add luggage than " +
                                                        "the luggage list",
                                                HttpStatus.BAD_REQUEST);
                }
//              If there are less than the Baggage list but some of the
//              baggage to be chosen are repeated, it would be invalid
//              because there can only be one type of baggage per passenger.
                List<Long> uniqueSelectedLuggage = new ArrayList<>();
                List<Luggage> repeatedSelectedLuggage = new ArrayList<>();
                for(Luggage luggage : luggageSelected) {
                    Luggage luggageToFind = luggageService.findLuggage(
                            luggage.getId());
                    if (luggageToFind == null) {
                        return new ResponseEntity<>(
                                "Please enter a valid " + "luggage List, " + "because this " + "luggage don't " + "exist",
                                HttpStatus.BAD_REQUEST);
                    }
                    if(!luggageToFind.getModel()){
                        return new ResponseEntity<>("This is not a luggage " +
                                                            "model",
                                                    HttpStatus.BAD_REQUEST);
                    } else {
//                        Now we will create new Luggage that don't be modified
//                        the model luggage based
                        luggage.setId(null);
                    }
                    if (!uniqueSelectedLuggage.contains(luggageToFind)) {
                        uniqueSelectedLuggage.add(luggage.getId());
                    } else {
                        repeatedSelectedLuggage.add(luggage);
                    }
                    if (uniqueSelectedLuggage.size() != luggageSelected.size() || !repeatedSelectedLuggage.isEmpty()) {
                        return new ResponseEntity<>(
                                "Luggage " + "repeated: \n" + repeatedSelectedLuggage.toString() + "\n only valid one type of luggage for passenger",
                                HttpStatus.BAD_REQUEST);
                    }
                    if(luggage.getPrice() == null){
                        return new ResponseEntity<>("Invalid price luggage " +
                                                            "for: \n" + luggage + "\n There must be at least for free price like a 0 value",HttpStatus.BAD_REQUEST);
                    }
                    luggageTotalPrice += luggage.getPrice();
                    luggage.setModel(false);
                }
                passenger.setLuggagePrice(luggageTotalPrice);
            }
            List<SeatFlight> seatsRowColumn = new ArrayList<>();
            if(passenger.getSeatFlightRowColumnOneWay() != null){
                seatsRowColumn.add(passenger.getSeatFlightRowColumnOneWay());
            }
            if(passenger.getSeatFlightRowColumnReturn() != null){
                seatsRowColumn.add(passenger.getSeatFlightRowColumnReturn());
            }

//           bookingType = TRUE  --> means -> OneWayFlight & ReturnFlight
//           bookingType = FALSE --> means -> Only oneWayFlight
            int sizeFlightRowColumn = bookingType ? 2 : 1;

            if (seatsRowColumn.size() != sizeFlightRowColumn) {
                seatsRowColumn = new ArrayList<>();
                for (int i = 0; i < sizeFlightRowColumn; i++) {
                    if (i == 0) {
                        passenger.setSeatFlightRowColumnOneWay(null);
                    } else {
                        passenger.setSeatFlightRowColumnReturn(null);
                    }
                }
                passenger.setSeatsPrice((double) 0);
            }
            else {
                for (int i = 0; i < seatsRowColumn.size(); i++) {
                    SeatFlight seatItem = seatsRowColumn.get(i);
                    if(seatItem.getRowSeat() == null || seatItem.getColumnSeat() == null){
                        return new ResponseEntity<>("The row and/or the column seatFlight parameter can't be null, in case you don't want to choose a seat leave the seats parameter empty",
                                                    HttpStatus.BAD_REQUEST);
                    }
                    if (seatItem.getRowSeat() < 0 || seatItem.getColumnSeat() < 0) {
                        return new ResponseEntity<>(
                                "The row and " + "the " + "column seat" + " can " + "be " + "0",
                                HttpStatus.BAD_REQUEST);
                    } else {
                        int row = seatItem.getRowSeat();
                        int column = seatItem.getColumnSeat();
                        String rowString = String.format("%02d", row);
                        char columnChar = (char) ('A' + column - 1);

                        seatItem.setSeatString(rowString + columnChar);
//                      seatsColumnRowPrice = R -> ROWS , C -> COLUMNS, P ->
//                      PRICE = seatRCP
                        Map<String,Double> seatRCP =
                                new HashMap<>();
                        Flight actualFlight;
                        if (i == 0) {
                            seatRCP.put("rows",
                                (double) flightOneWay.getRowsSeat());
                            seatRCP.put("columns",
                                                    (double) flightOneWay.getColumnsSeat());
                            seatRCP.put("price",
                                                    flightOneWay.getSeatPrice());
                            actualFlight = flightOneWay;
                        } else {
                            seatRCP.put("rows",
                                                    (double) flightReturn.getRowsSeat());
                            seatRCP.put("columns",
                                                    (double) flightReturn.getColumnsSeat());
                            seatRCP.put("price",
                                                    flightReturn.getSeatPrice());
                            actualFlight = flightReturn;
                        }
                        Double totalSeatPrice = (double) 0;

                        int flightRows = (int) Math.round(seatRCP.get("rows"));
                        int flightColumns = (int) Math.round(seatRCP.get(
                                "columns"));
                        Double flightSeatPrice = seatRCP.get("price");

//                        Checking if the seat is already booked
                        List<SeatFlight> seatsReserved = seatService.getAllSeats();
                        SeatFlight existingSeat = seatsReserved.stream()
                                .filter(seat -> (seat.getColumnSeat() == column && seat.getRowSeat() == row && seat.getFlightId().getFlightNumber().equalsIgnoreCase(actualFlight.getFlightNumber())))
                                .findFirst()
                                .orElse(null);
                        if (existingSeat != null) {
                            return new ResponseEntity<>("The seat you entered" +
                                                                " for the " +
                                                                "flight " + actualFlight.getFlightNumber() + " in the row " + row + " and the column " + column + " is already booked", HttpStatus.BAD_REQUEST);
                        }

                        if (row > flightRows) {
                            return new ResponseEntity<>(
                                    "There's " + "only " + flightRows + " " + "and you enter the seat inside a " + row + " that don't exist",
                                    HttpStatus.BAD_REQUEST);
                        }
                        if (flightColumns < column) {
                            return new ResponseEntity<>(
                                    "In " + "the flight there's only " + row +
                                            " " +
                                            " rows and " + "only " + flightColumns + " columns and you enter the seat inside a " + column + " column that don't exist",
                                    HttpStatus.BAD_REQUEST);
                        }
                        seatItem.setPrice(flightSeatPrice);
                        totalSeatPrice += flightSeatPrice;

                        if (seatItem.getPrice() == null) {
                            return new ResponseEntity<>(
                                    "There's " + "no row column price for this seat inside " + seatItem.getSeatString(),
                                    HttpStatus.BAD_REQUEST);
                        }
                        seatItem.setFlightId(actualFlight);
                        passenger.setSeatsPrice(totalSeatPrice);
                        if (i == 0) {
                            passenger.setSeatFlightRowColumnOneWay(seatItem);
                        } else {
                            passenger.setSeatFlightRowColumnReturn(seatItem);
                        }
                    }
                }
            }

            Double totalFlightPassengerPrice = bookingType ? (flightBooking.getFlightOneWay().getPrice() + flightBooking.getFlightReturn().getPrice()) : flightBooking.getFlightReturn().getPrice();
            passenger.setTotalPrice(
                    totalFlightPassengerPrice + passenger.getLuggagePrice() + passenger.getSeatsPrice());
            passengersPrices += passenger.getTotalPrice();
        }

        flightBooking.setTotalPrice(passengersPrices);

        List<String> nullsParameters = new ArrayList<>();
        if (flightBooking.getOrigin() == null) {
            nullsParameters.add("origin");
        }
        if (flightBooking.getDestination() == null) {
            nullsParameters.add("destination");
        }
        if (flightBooking.getSeatType() == null) {
            nullsParameters.add("seatType");
        }
        if (flightBooking.getPeopleNumber() == null) {
            flightBooking.setPeopleNumber(
                    flightBooking.getInfoPassengers().size());
        }
        if (flightBooking.getActiveFlag() == null) {
            nullsParameters.add("activeFlag");
        }
        if (!nullsParameters.isEmpty()) {
            String nullsString = nullsParameters.toString().replace("[",
                                                                    "").replace(
                    "]", "");
            if (nullsParameters.size() == 1) {
                return new ResponseEntity<>(
                        "The following value is missing from the flightBooking model: " + nullsString,
                        HttpStatus.BAD_REQUEST);
            } else {
                nullsString = nullsString.substring(0, nullsString.lastIndexOf(
                        ",")) + " and" + nullsString.substring(
                        nullsString.lastIndexOf(",") + 1);
                return new ResponseEntity<>(
                        "The following values are missing from the flightBooking model: " + nullsString,
                        HttpStatus.BAD_REQUEST);
            }
        }

        if (flightBooking.getOrigin().equalsIgnoreCase(
                flightBooking.getDestination()))
        {
            return new ResponseEntity<>(
                    flightBooking.getOrigin() + " origin " + "cannot be the " + "same as " + "destination" + flightBooking.getDestination(),
                    HttpStatus.BAD_REQUEST);
        }
        if (!flightBooking.getOrigin().equalsIgnoreCase(
                flightBooking.getFlightOneWay().getOrigin()) || !flightBooking.getDestination().equalsIgnoreCase(
                flightBooking.getFlightOneWay().getDestination()))
        {
            flightBooking.setOrigin(
                    flightBooking.getFlightOneWay().getOrigin());
            flightBooking.setDestination(
                    flightBooking.getFlightOneWay().getDestination());
        }

        FlightBooking flightBookingSaved = flightBookingService.saveFlightBooking(
                flightBooking);

        for (Passenger passengerItem : infoPassengers) {
            passengerItem.setFlightBooking(flightBookingSaved);

            SeatFlight seatsOneWay = passengerItem.getSeatFlightRowColumnOneWay();
            SeatFlight seatsReturn = passengerItem.getSeatFlightRowColumnReturn();

            if (seatsOneWay != null) {
                seatsOneWay = seatService.saveSeat(seatsOneWay);
            }
            if (seatsReturn != null) {
                seatsReturn = seatService.saveSeat(seatsReturn);
            }

            Passenger passengerSaved = passengerService.savePassenger(
                    passengerItem);

            List<Luggage> luggageList = passengerItem.getLuggageList();
            for (Luggage luggageItem : luggageList) {
                if (luggageItem == null) {
                    return new ResponseEntity<>(
                            "The luggage item inside " + "the list " + "luggage don't" + " exist",
                            HttpStatus.BAD_REQUEST);
                }
                luggageItem.setPassenger(passengerSaved);
                luggageService.saveLuggage(luggageItem);
            }

        }

        return new ResponseEntity<>("FlightBooking created successfully",
                                    HttpStatus.OK);
    }

    @PostMapping("/disable/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<String> disableFlightBooking(@PathVariable Long id) {
        FlightBooking flightBooking = flightBookingService.findFlightBooking(id);
        if (flightBooking == null) {
            return new ResponseEntity<>("There's no bookings with this code",
                                        HttpStatus.NOT_FOUND);
        }
        flightBooking.setActiveFlag(false);
        flightBookingService.saveFlightBooking(flightBooking);
        return new ResponseEntity<>("FlightBooking disabled", HttpStatus.OK);
    }

    @PostMapping("/enable/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<String> enableFlightBooking(@PathVariable Long id) {
        FlightBooking flightBooking = flightBookingService.findFlightBooking(id);
        if (flightBooking == null) {
            return new ResponseEntity<>("There's no bookings with this code",
                                        HttpStatus.NOT_FOUND);
        }
        flightBooking.setActiveFlag(true);
        flightBookingService.saveFlightBooking(flightBooking);
        return new ResponseEntity<>("FlightBooking enabled", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<?> editFlightBooking(
            @PathVariable Long id,
            @RequestBody FlightBooking updateflightBooking
            ) {

        FlightBooking flightBooking = flightBookingService.findFlightBooking(id);
        if (flightBooking == null) {
            return new ResponseEntity<>(
                    "No flightBooking with " + id,
                    HttpStatus.NOT_FOUND);
        }

        List<Passenger> originalPassengers = new ArrayList<>();
        Integer updatePeopleNumber = updateflightBooking.getPeopleNumber();
        String updateSeatType = updateflightBooking.getSeatType();
        List<Passenger> updateInfoPassengers =
                updateflightBooking.getInfoPassengers();
        Double updateTotalPrice = updateflightBooking.getTotalPrice();
        Boolean updateActiveFlag = updateflightBooking.getActiveFlag();

        flightBooking.setOrigin(flightBooking.getOrigin());
        flightBooking.setDestination(flightBooking.getDestination());
        flightBooking.setBookingType(flightBooking.getBookingType());
        flightBooking.setFlightOneWay(flightBooking.getFlightOneWay());
        flightBooking.setFlightReturn(flightBooking.getFlightReturn());
        flightBooking.setPeopleNumber(
                updatePeopleNumber != null ? updatePeopleNumber :
                        flightBooking.getPeopleNumber());
        flightBooking.setSeatType(
                updateSeatType != null ? updateSeatType : flightBooking.getSeatType());
        if(updateInfoPassengers != null){
            originalPassengers = flightBooking.getInfoPassengers();
            flightBooking.setInfoPassengers(updateInfoPassengers);
        }
        flightBooking.setTotalPrice(
                updateTotalPrice != null ? updateTotalPrice :
                        flightBooking.getTotalPrice());
        flightBooking.setActiveFlag(
                updateActiveFlag != null ? updateActiveFlag :
                        flightBooking.getActiveFlag());


        Boolean bookingType = flightBooking.getBookingType();
        Flight flightOneWay =
                flightBooking.getFlightOneWay();
        Flight flightReturn = flightBooking.getFlightReturn();

//        bookingType = TRUE  --> means -> OneWayFlight & ReturnFlight
//        bookingType = FALSE --> means -> Only oneWayFlight

        if(flightBooking.getInfoPassengers() == null || flightBooking.getInfoPassengers().isEmpty()){
            return new ResponseEntity<>("The info from the passengers are " +
                                                "wrong",HttpStatus.BAD_REQUEST);
        }

        List<Passenger> infoPassengers = flightBooking.getInfoPassengers();
        Double passengersPrices = 0.0;

        for(Passenger passenger : infoPassengers ){

            // Checking if exist the luggageList inside the FlightBooking
            List<Luggage> luggageSelected = passenger.getLuggageList();
            List<Luggage> luggageModelList = luggageService.findModelLuggage();
            // if it's not null or empty luggageSelected
            double luggageTotalPrice = (double) 0;
            if( luggageSelected != null || !luggageSelected.isEmpty()){
//                In case that are more luggage that already exist: is
//                impossible
                if(luggageSelected.size() >= luggageModelList.size()){
                    return new ResponseEntity<>("You cannot add luggage than " +
                                                        "the luggage list",
                                                HttpStatus.BAD_REQUEST);
                }
//              If there are less than the Baggage list but some of the
//              baggage to be chosen are repeated, it would be invalid
//              because there can only be one type of baggage per passenger.
                List<Long> uniqueSelectedLuggage = new ArrayList<>();
                List<Luggage> repeatedSelectedLuggage = new ArrayList<>();
                for(Luggage luggage : luggageSelected) {
                    Luggage luggageToFind = luggageService.findLuggage(
                            luggage.getId());
                    if (luggageToFind == null) {
                        return new ResponseEntity<>(
                                "Please enter a valid " + "luggage List, " + "because this " + "luggage don't " + "exist",
                                HttpStatus.BAD_REQUEST);
                    }
                    if(!luggageToFind.getModel()){
                        return new ResponseEntity<>("This is not a luggage " +
                                                            "model",
                                                    HttpStatus.BAD_REQUEST);
                    } else {
//                        Now we will create new Luggage that don't be modified
//                        the model luggage based
                        luggage.setId(null);
                    }
                    if (!uniqueSelectedLuggage.contains(luggageToFind)) {
                        uniqueSelectedLuggage.add(luggage.getId());
                    } else {
                        repeatedSelectedLuggage.add(luggage);
                    }
                    if (uniqueSelectedLuggage.size() != luggageSelected.size() || !repeatedSelectedLuggage.isEmpty()) {
                        return new ResponseEntity<>(
                                "Luggage " + "repeated: \n" + repeatedSelectedLuggage.toString() + "\n only valid one type of luggage for passenger",
                                HttpStatus.BAD_REQUEST);
                    }
                    if(luggage.getPrice() == null){
                        return new ResponseEntity<>("Invalid price luggage " +
                                                            "for: \n" + luggage + "\n There must be at least for free price like a 0 value",HttpStatus.BAD_REQUEST);
                    }
                    luggageTotalPrice += luggage.getPrice();
                    luggage.setModel(false);
                }
                passenger.setLuggagePrice(luggageTotalPrice);
            }
            List<SeatFlight> seatsRowColumn = new ArrayList<>();
            if(passenger.getSeatFlightRowColumnOneWay() != null){
                seatsRowColumn.add(passenger.getSeatFlightRowColumnOneWay());
            }
            if(passenger.getSeatFlightRowColumnReturn() != null){
                seatsRowColumn.add(passenger.getSeatFlightRowColumnReturn());
            }

//           bookingType = TRUE  --> means -> OneWayFlight & ReturnFlight
//           bookingType = FALSE --> means -> Only oneWayFlight
            int sizeFlightRowColumn = bookingType ? 2 : 1;

            if (seatsRowColumn.size() != sizeFlightRowColumn) {
                seatsRowColumn = new ArrayList<>();
                for (int i = 0; i < sizeFlightRowColumn; i++) {
                    if (i == 0) {
                        passenger.setSeatFlightRowColumnOneWay(null);
                    } else {
                        passenger.setSeatFlightRowColumnReturn(null);
                    }
                }
                passenger.setSeatsPrice((double) 0);
            }
            else {
                for (int i = 0; i < seatsRowColumn.size(); i++) {
                    SeatFlight seatItem = seatsRowColumn.get(i);
                    if(seatItem.getRowSeat() == null || seatItem.getColumnSeat() == null){
                        return new ResponseEntity<>("The row and/or the column seatFlight parameter can't be null, in case you don't want to choose a seat leave the seats parameter empty",
                                                    HttpStatus.BAD_REQUEST);
                    }
                    if (seatItem.getRowSeat() < 0 || seatItem.getColumnSeat() < 0) {
                        return new ResponseEntity<>(
                                "The row and " + "the " + "column seat" + " can " + "be " + "0",
                                HttpStatus.BAD_REQUEST);
                    } else {
                        int row = seatItem.getRowSeat();
                        int column = seatItem.getColumnSeat();
                        String rowString = String.format("%02d", row);
                        char columnChar = (char) ('A' + column - 1);

                        seatItem.setSeatString(rowString + columnChar);
//                      seatsColumnRowPrice = R -> ROWS , C -> COLUMNS, P ->
//                      PRICE = seatRCP
                        Map<String,Double> seatRCP =
                                new HashMap<>();
                        Flight actualFlight;
                        if (i == 0) {
                            seatRCP.put("rows",
                                        (double) flightOneWay.getRowsSeat());
                            seatRCP.put("columns",
                                        (double) flightOneWay.getColumnsSeat());
                            seatRCP.put("price",
                                        flightOneWay.getSeatPrice());
                            actualFlight = flightOneWay;
                        } else {
                            seatRCP.put("rows",
                                        (double) flightReturn.getRowsSeat());
                            seatRCP.put("columns",
                                        (double) flightReturn.getColumnsSeat());
                            seatRCP.put("price",
                                        flightReturn.getSeatPrice());
                            actualFlight = flightReturn;
                        }
                        Double totalSeatPrice = (double) 0;

                        int flightRows = (int) Math.round(seatRCP.get("rows"));
                        int flightColumns = (int) Math.round(seatRCP.get(
                                "columns"));
                        Double flightSeatPrice = seatRCP.get("price");

//                        Checking if the seat is already booked
                        List<SeatFlight> seatsReserved = seatService.getAllSeats();
                        SeatFlight existingSeat = seatsReserved.stream()
                                .filter(seat -> (seat.getColumnSeat() == column && seat.getRowSeat() == row && seat.getFlightId().getFlightNumber().equalsIgnoreCase(actualFlight.getFlightNumber())))
                                .findFirst()
                                .orElse(null);
                        if (existingSeat != null) {
                            return new ResponseEntity<>("The seat you entered" +
                                                                " for the " +
                                                                "flight " + actualFlight.getFlightNumber() + " in the row " + row + " and the column " + column + " is already booked", HttpStatus.BAD_REQUEST);
                        }

                        if (row > flightRows) {
                            return new ResponseEntity<>(
                                    "There's " + "only " + flightRows + " " + "and you enter the seat inside a " + row + " that don't exist",
                                    HttpStatus.BAD_REQUEST);
                        }
                        if (flightColumns < column) {
                            return new ResponseEntity<>(
                                    "In " + "the flight there's only " + row +
                                            " " +
                                            " rows and " + "only " + flightColumns + " columns and you enter the seat inside a " + column + " column that don't exist",
                                    HttpStatus.BAD_REQUEST);
                        }
                        seatItem.setPrice(flightSeatPrice);
                        totalSeatPrice += flightSeatPrice;

                        if (seatItem.getPrice() == null) {
                            return new ResponseEntity<>(
                                    "There's " + "no row column price for this seat inside " + seatItem.getSeatString(),
                                    HttpStatus.BAD_REQUEST);
                        }
                        seatItem.setFlightId(actualFlight);
                        passenger.setSeatsPrice(totalSeatPrice);
                        if (i == 0) {
                            passenger.setSeatFlightRowColumnOneWay(seatItem);
                        } else {
                            passenger.setSeatFlightRowColumnReturn(seatItem);
                        }
                    }
                }
            }

            Double totalFlightPassengerPrice = bookingType ? (flightBooking.getFlightOneWay().getPrice() + flightBooking.getFlightReturn().getPrice()) : flightBooking.getFlightReturn().getPrice();
            passenger.setTotalPrice(
                    totalFlightPassengerPrice + passenger.getLuggagePrice() + passenger.getSeatsPrice());
            passengersPrices += passenger.getTotalPrice();
        }

        flightBooking.setTotalPrice(passengersPrices);

        List<String> nullsParameters = new ArrayList<>();
        if (flightBooking.getOrigin() == null) {
            nullsParameters.add("origin");
        }
        if (flightBooking.getDestination() == null) {
            nullsParameters.add("destination");
        }
        if (flightBooking.getSeatType() == null) {
            nullsParameters.add("seatType");
        }
        if (flightBooking.getPeopleNumber() == null) {
            flightBooking.setPeopleNumber(
                    flightBooking.getInfoPassengers().size());
        }
        if (flightBooking.getActiveFlag() == null) {
            nullsParameters.add("activeFlag");
        }
        if (!nullsParameters.isEmpty()) {
            String nullsString = nullsParameters.toString().replace("[",
                                                                    "").replace(
                    "]", "");
            if (nullsParameters.size() == 1) {
                return new ResponseEntity<>(
                        "The following value is missing from the flightBooking model: " + nullsString,
                        HttpStatus.BAD_REQUEST);
            } else {
                nullsString = nullsString.substring(0, nullsString.lastIndexOf(
                        ",")) + " and" + nullsString.substring(
                        nullsString.lastIndexOf(",") + 1);
                return new ResponseEntity<>(
                        "The following values are missing from the flightBooking model: " + nullsString,
                        HttpStatus.BAD_REQUEST);
            }
        }

        if (flightBooking.getOrigin().equalsIgnoreCase(
                flightBooking.getDestination()))
        {
            return new ResponseEntity<>(
                    flightBooking.getOrigin() + " origin " + "cannot be the " + "same as " + "destination" + flightBooking.getDestination(),
                    HttpStatus.BAD_REQUEST);
        }
        if (!flightBooking.getOrigin().equalsIgnoreCase(
                flightBooking.getFlightOneWay().getOrigin()) || !flightBooking.getDestination().equalsIgnoreCase(
                flightBooking.getFlightOneWay().getDestination()))
        {
            flightBooking.setOrigin(
                    flightBooking.getFlightOneWay().getOrigin());
            flightBooking.setDestination(
                    flightBooking.getFlightOneWay().getDestination());
        }

        FlightBooking flightBookingSaved = flightBookingService.saveFlightBooking(
                flightBooking);

        for (Passenger passengerItem : infoPassengers) {
            passengerItem.setFlightBooking(flightBookingSaved);

            SeatFlight seatsOneWay = passengerItem.getSeatFlightRowColumnOneWay();
            SeatFlight seatsReturn = passengerItem.getSeatFlightRowColumnReturn();

            if (seatsOneWay != null) {
                seatsOneWay = seatService.saveSeat(seatsOneWay);
            }
            if (seatsReturn != null) {
                seatsReturn = seatService.saveSeat(seatsReturn);
            }

            Passenger passengerSaved = passengerService.savePassenger(
                    passengerItem);

            List<Luggage> luggageList = passengerItem.getLuggageList();
            for (Luggage luggageItem : luggageList) {
                if (luggageItem == null) {
                    return new ResponseEntity<>(
                            "The luggage item inside " + "the list " + "luggage don't" + " exist",
                            HttpStatus.BAD_REQUEST);
                }
                luggageItem.setPassenger(passengerSaved);
                luggageService.saveLuggage(luggageItem);
            }

        }

        flightBookingService.saveFlightBooking(flightBooking);
        for (Passenger originalPassenger : originalPassengers) {
            for (Luggage originalLuggage :
                    originalPassenger.getLuggageList()) {
                luggageService.deleteLuggage(originalLuggage.getId());
            }
            passengerService.deletePassenger(originalPassenger.getId());
        }
        return new ResponseEntity<>(flightBooking, HttpStatus.OK);
    }
}