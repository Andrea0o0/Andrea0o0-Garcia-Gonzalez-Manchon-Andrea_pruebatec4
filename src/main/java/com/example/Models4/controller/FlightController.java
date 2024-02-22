package com.example.Models4.controller;

import com.example.Models4.model.Flight;
import com.example.Models4.model.FlightBooking;
import com.example.Models4.service.flight.FlightService;
import com.example.Models4.service.flight.IFlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private final IFlightService flightService;

    @Autowired
    public FlightController(IFlightService flightServiceTest) {
        this.flightService = flightServiceTest;
    }

    @Operation(summary = "Retrieve Flights", description = "Retrieves a list of flights.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of flights retrieved"),
            @ApiResponse(responseCode = "204", description = "No content - if no flights are found")
    })
    @GetMapping("/get")
//    PRIVATE: ADMIN
    public ResponseEntity<List<Flight>> getFlights() {
        List<Flight> flights =
                flightService.getFlights().stream().filter(
                        Flight::getActiveFlag).toList();

        return (flights.isEmpty()) ? new ResponseEntity<>(
                HttpStatus.NO_CONTENT) : new ResponseEntity<>(flights,
                                                              HttpStatus.OK);
    }

    @Operation(summary = "Retrieve Flight by ID", description = "Retrieves a flight by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight details retrieved"),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @GetMapping("/get/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<?> getFlight (@PathVariable String id){
        return flightService.findFlight(id) == null ? new ResponseEntity<>(
                "There's no flight with " + id + " flightNumber or Id",
                HttpStatus.NOT_FOUND):
                new ResponseEntity<>(flightService.findFlight(id),HttpStatus.OK);
    }


    @Operation(summary = "Save New Flight", description = "Saves a new flight.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request - if request body is missing or invalid")
    })
    @PostMapping("/create")
//    PRIVATE: ADMIN
    public ResponseEntity<String> saveFlight(@RequestBody Flight flight) {
        if(flight == null){
            return new ResponseEntity<>("It's necessary a flight",
                                        HttpStatus.BAD_REQUEST);
        }
        String seatType = flight.getSeatType();
        if (!seatType.equalsIgnoreCase("Business") && !seatType.equalsIgnoreCase("Economy") && !seatType.equalsIgnoreCase("Premium Economy") && !seatType.equalsIgnoreCase("First Class")){
            return new ResponseEntity<>(seatType + " is not a valid type of class seat" ,
                                        HttpStatus.BAD_REQUEST);
        }
        if (flight.getTakeoffDate().isAfter(flight.getArrivalDate()) || flight.getTakeoffDate().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>(
                    "It's necessary to enter a takeOff " + "date, must be before the " + "arrival and also after the " + "actual time",
                    HttpStatus.BAD_REQUEST);
        }
        List<String> nullsParameters = new ArrayList<>();
        if(flight.getFlightNumber() == null){
            nullsParameters.add("flightNumber");
        }
        if(flight.getOrigin() == null){
            nullsParameters.add("origin");
        }
        if(flight.getDestination() == null){
            nullsParameters.add("destination");
        }
        if(flight.getSeatType() == null){
            nullsParameters.add("seatType");
        }
        if(flight.getPrice() == null){
            nullsParameters.add("price");
        }
        if(flight.getTakeoffDate() == null){
            nullsParameters.add("takeOffDate");
        }
        if(flight.getArrivalDate() == null){
            nullsParameters.add("arrivalDate");
        }
        if(flight.getSeatsNumber() == null){
            nullsParameters.add("seatsNumber");
        }
        if(flight.getReservedSeats() == null){
            nullsParameters.add("reservedSeats");
        }
        if (flight.getActiveFlag() == null) {
            nullsParameters.add("activeFlag");
        }
        Flight existingFlight =
                flightService.findFlight(flight.getFlightNumber());
        if(existingFlight != null){
            return new ResponseEntity<>(
                    "The flight already exists",
                    HttpStatus.BAD_REQUEST);
        }

        if(!nullsParameters.isEmpty()){
            String nullsString =
                    nullsParameters.toString().replace("[","").replace("]","");
            if(nullsParameters.size() == 1){
                return new ResponseEntity<>(
                        "The following value is missing from the flight model: " + nullsString,
                        HttpStatus.BAD_REQUEST);
            } else {
                nullsString = nullsString.substring(0,nullsString.lastIndexOf(
                        ",")) +
                        " and" +
                        nullsString.substring(nullsString.lastIndexOf(",")+1);
                return new ResponseEntity<>(
                        "The following values are missing from the flight model: " + nullsString,
                        HttpStatus.BAD_REQUEST);
            }
        }

        if(flight.getOrigin().equalsIgnoreCase(flight.getDestination())){
            return new ResponseEntity<>(flight.getOrigin() + " origin " +
                                                "cannot be the " +
                    "same as " +
                    "destination" + flight.getDestination(),HttpStatus.BAD_REQUEST);
        }

        if(flight.getReservedSeats() > flight.getSeatsNumber()){
            return new ResponseEntity<>("The reserved seats value can't be " +
                                                "higher than the seat's value" +
                                                " from the flight",
                                        HttpStatus.BAD_REQUEST);
        }

        flightService.saveFlight(flight);
        return new ResponseEntity<>("Fight created successfully", HttpStatus.OK);
    }

    @Operation(summary = "Disable Flight", description = "Disables a flight by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight disabled successfully"),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @PostMapping("/disable/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<String> disableFlight (@PathVariable String id){
        Flight flight = flightService.findFlight(id);
        if(flight == null){
            return new ResponseEntity<>("There's no flights with this code",
                                        HttpStatus.NOT_FOUND);
        }
        flight.setActiveFlag(false);
        flightService.saveFlight(flight);
        return new ResponseEntity<>("Flight disabled",HttpStatus.OK);
    }

    @Operation(summary = "Enable Flight", description = "Enables a flight by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight enabled successfully"),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @PostMapping("/enable/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<String> enableFlight (@PathVariable String id){
        Flight flight = flightService.findFlight(id);
        if(flight == null){
            return new ResponseEntity<>("There's no flights with this code",
                                        HttpStatus.NOT_FOUND);
        }
        flight.setActiveFlag(true);
        flightService.saveFlight(flight);
        return new ResponseEntity<>("Flight enabled",HttpStatus.OK);
    }

    @Operation(summary = "Update Flight", description = "Updates details of a" +
            " flight by its ID and params like origin, destination, seatType." +
            "..")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request - if input data is invalid"),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @PutMapping("/update/{id}")
//    PRIVATE: ADMIN
    public ResponseEntity<?> editFlight(
            @PathVariable String id,
            @RequestParam(value = "origin", required = false) String updateOrigin,
            @RequestParam(value = "destination", required = false) String updateDestination,
            @RequestParam(value = "seatType", required = false) String updateSeatType,
            @RequestParam(value = "price", required = false) Double updatePrice,
//            @RequestParam(value = "seatsRowsColumnsPrice", required = false) List<Map<String,Double>> updateSeatsRowsColumnsPrice,
            @RequestParam(value = "takeoffDate", required = false) LocalDateTime updateTakeoffDate,
            @RequestParam(value = "arrivalDate", required = false) LocalDateTime updateArrivalDate,
            @RequestParam(value = "seatsNumber", required = false) Integer updateSeatsNumber,
            @RequestParam(value = "reservedSeats", required = false) Integer updateReservedSeats,
            @RequestParam(value = "activeFlag", required = false) Boolean updateActiveFlag){

        Flight flight = flightService.findFlight(id);
        if (flight == null) {
            return new ResponseEntity<>("No flight with "+ id + " " +
                                                "flightNumber",HttpStatus.NOT_FOUND);
        }

        flight.setOrigin(updateOrigin != null ? updateOrigin : flight.getOrigin());
        flight.setDestination(updateDestination != null ? updateDestination : flight.getDestination());
        flight.setSeatType(updateSeatType != null ? updateSeatType : flight.getSeatType());
        flight.setPrice(updatePrice != null ? updatePrice : flight.getPrice());
        flight.setTakeoffDate(updateTakeoffDate != null ? updateTakeoffDate : flight.getTakeoffDate());
        flight.setArrivalDate(updateArrivalDate != null ? updateArrivalDate : flight.getArrivalDate());
        flight.setActiveFlag(updateActiveFlag != null ? updateActiveFlag : flight.getActiveFlag());
        if(flight.getOrigin().equalsIgnoreCase(flight.getDestination())){
            return new ResponseEntity<>(flight.getOrigin() + " origin " +
                                                "cannot be the " +
                                                "same as " +
                                                "destination" + flight.getDestination(),HttpStatus.BAD_REQUEST);
        }

        if(flight.getReservedSeats() > flight.getSeatsNumber()){
            return new ResponseEntity<>("The reserved seats value can't be higher than the seat's value from the flight",
                                        HttpStatus.BAD_REQUEST);
        }

        if(updateSeatType != null) {
            String seatType = flight.getSeatType();
            if (!seatType.equalsIgnoreCase(
                    "Business") && !seatType.equalsIgnoreCase(
                    "Economy") && !seatType.equalsIgnoreCase(
                    "Premium Economy") && !seatType.equalsIgnoreCase(
                    "First Class"))
            {
                return new ResponseEntity<>(
                        seatType + " is not a valid type of class seat",
                        HttpStatus.BAD_REQUEST);
            }
        }
        if (flight.getTakeoffDate().isAfter(flight.getArrivalDate()) || flight.getTakeoffDate().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>(
                    "It's necessary to enter a takeOff " + "date, must be before the " + "arrival and also after the " + "actual time",
                    HttpStatus.BAD_REQUEST);
        }

        flightService.saveFlight(flight);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @Operation(summary = "Search One-Way Flights", description = "Searches " +
            "for one-way flights. THE DESTINATION IS NOT REQUIRED, IF INSIDE THE PARAM with the value\n" + "    destination are ALL (IgnoreCase) the destination will automatically\n" + "    The RANGE DATES is optional, in case of a second Date (untilDate), the\n" + "    function will interpret it like a date range One way flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "One-way flights found"),
            @ApiResponse(responseCode = "400", description = "Bad request - if input data is invalid"),
            @ApiResponse(responseCode = "404", description = "Flights not found")
    })
    @GetMapping("/oneway")
//    THE DESTINATION IS NOT REQUIRED, IF INSIDE THE PARAM with the value
//    destination are ALL (IgnoreCase) the destination will automatically
//    The RANGE DATES is optional, in case of a second Date (untilDate), the
//    function will interpret it like a date range One way flight
//    The options are: One Way Exact day -> with or without destination and
//    One Way with flexible dates -> with or without destination
//    Is optional the type Seat filter
//    Is optional the price filter
//    nowhere that means all the destination match
//    PUBLIC
    public ResponseEntity<?> oneWayFlight(
            @RequestParam(value = "origin", required = false) String origin,
            @RequestParam(value = "destination", required = false) String destination,
            @RequestParam(value = "seatsNumber", required = false) Integer seatsNumber,
            @RequestParam(value = "fromDate", required = false) LocalDate fromDate,
            @RequestParam(value = "untilDate", required = false) LocalDate untilDate,
            @RequestParam(value = "seatType", required = false) String seatType,
            @RequestParam(value = "maxPrice",required = false) Double price){

        List<Flight> flights;
        if (origin == null ){
            return new ResponseEntity<>("It's necessary to enter the origin",
                                        HttpStatus.BAD_REQUEST);
        }

        if (seatsNumber == null ){
            return new ResponseEntity<>("It's necessary to enter the number " +
                                                "of seats",
                                        HttpStatus.BAD_REQUEST);
        } else if(seatsNumber > 8){
            return new ResponseEntity<>("You can only reserve 8 seats or less",
                                        HttpStatus.BAD_REQUEST);
        }

        if (fromDate == null){
            return new ResponseEntity<>("It's necessary to enter a valid date",
                                        HttpStatus.BAD_REQUEST);
        }

        if (fromDate.isBefore(LocalDate.now()) ){
            return new ResponseEntity<>("It's necessary to enter the date of " +
                                                "the flight",
                                        HttpStatus.BAD_REQUEST);
        }

        if(destination != null) {
            if(origin.equalsIgnoreCase(destination)) {
                return new ResponseEntity<>(
                        origin + " origin " + "cannot be the " + "same as " +
                                "destination",
                        HttpStatus.BAD_REQUEST);
            }
        }


        if(untilDate != null) {
            if (fromDate.isAfter(untilDate) || fromDate.isBefore(
                    LocalDate.now()))
            {
                return new ResponseEntity<>(
                        "It's necessary to enter a valid " + "date the start range date, " + "has to higher than end date " + "and also higher or equal " + "than the actual date",
                        HttpStatus.BAD_REQUEST);
            }
        }

//        YOU CAN'T RESERVE MORE THAN 8 SEATS AT THE SAME TIME // SE PROHIBE
//        RESERVAR MÁS DE 8 ASIENTOS A LA VEZ

        if (destination == null ){
            if(untilDate == null) {
                flights = flightService.findOneWayFlightNoDestination(origin,
                                                                      fromDate,
                                                                      seatsNumber);
            } else {
               flights =
                       flightService. findOneWayDateRangeFlightNoDestination(origin,fromDate,untilDate,seatsNumber);
            }
        } else {
            if(untilDate == null) {
                flights = flightService.findOneWayFlight(origin, destination,
                                                         fromDate, seatsNumber);
            } else {
                flights = flightService.findOneWayFlightRangeDates(origin,
                                                                   destination,fromDate,untilDate,seatsNumber);
            }
        }
        if(flights == null){
            return new ResponseEntity<>("There's no flights for this search",
                                        HttpStatus.NOT_FOUND);
        }

        String fromDateString =
                fromDate.getDayOfMonth() + "-" + fromDate.getMonthValue() +
                        "-" + fromDate.getYear();

        if(price != null) {
            flights = flights.stream().filter(
                    flight -> flight.getPrice() <= price).toList();

            if (flights.isEmpty()) {
                return new ResponseEntity<>(
                        "There's no flights " + origin + " to " + destination + " in " + fromDateString + " with " + price + " as the maximum price at this moment",
                        HttpStatus.NOT_FOUND);
            }
        }

        if(seatType == null){
            return new ResponseEntity<>(flights,HttpStatus.OK);
        } else if (!seatType.equalsIgnoreCase("Business") && !seatType.equalsIgnoreCase("Economy") && !seatType.equalsIgnoreCase("Premium Economy") && !seatType.equalsIgnoreCase("First Class")){
            return new ResponseEntity<>(seatType + " is not a valid type of class seat" ,
                                        HttpStatus.BAD_REQUEST);
        }
        flights =
                flights.stream().filter(flight -> flight.getSeatType().equalsIgnoreCase(seatType)).toList();


        if (flights.isEmpty()) {
            return new ResponseEntity<>("There's no flights "+ origin + " to " + destination + " in " + fromDateString + " with " + seatType + " class at this moment",
                                        HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(flights,HttpStatus.OK);
    }


    @Operation(summary = "Search One-Way and Return Flights", description = "Searches for one-way and return flights.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "One-way and return flights found"),
            @ApiResponse(responseCode = "400", description = "Bad request - if input data is invalid"),
            @ApiResponse(responseCode = "404", description = "Flights not found")
    })
    @GetMapping("/onewayReturn")
//    PUBLIC
//    Can look for flights oneWay and return, with or without destination,
//    with exact dates, or with flexible dates by choosing how many days will
//    be the duration of the trip
    public ResponseEntity<?> returnFlight(
            @RequestParam(value = "origin", required = false) String origin,
            @RequestParam(value = "destination", required = false) String destination,
            @RequestParam(value = "seatsNumber", required = false) Integer seatsNumber,
            @RequestParam(value = "fromDate", required = false) LocalDate fromDate,
            @RequestParam(value = "untilDate", required = false) LocalDate untilDate,
            @RequestParam(value = "days", required = false) Integer days,
            @RequestParam(value = "seatType", required = false) String seatType,
            @RequestParam(value = "maxPrice",required = false) Double price) {

        if (origin == null) {
            return new ResponseEntity<>("It's necessary to enter the origin",
                                        HttpStatus.BAD_REQUEST);
        }

        if(destination != null) {
            if(origin.equalsIgnoreCase(destination)) {
                return new ResponseEntity<>(
                        origin + " origin " + "cannot be the " + "same as " +
                                "destination",
                        HttpStatus.BAD_REQUEST);
            }
        }

        if (seatsNumber == null) {
            return new ResponseEntity<>(
                    "It's necessary to enter the number of seats",
                    HttpStatus.BAD_REQUEST);
        }
//        YOU CAN'T RESERVE MORE THAN 8 SEATS AT THE SAME TIME // SE PROHIBE RESERVAR MÁS DE 8 ASIENTOS A LA VEZ
        else if (seatsNumber > 8) {
            return new ResponseEntity<>("You can only reserve 8 seats or less",
                                        HttpStatus.BAD_REQUEST);
        }
        if (fromDate == null || untilDate == null) {
            return new ResponseEntity<>(
                    "It's necessary to enter the dates range of the flights",
                    HttpStatus.BAD_REQUEST);
        }

        if (fromDate.isAfter(untilDate) || fromDate.isBefore(LocalDate.now())) {
            return new ResponseEntity<>(
                    "It's necessary to enter a valid date the start range date, has to higher than end date and also higher or equal than the actual date",
                    HttpStatus.BAD_REQUEST);
        }

        String fromDateString = fromDate.getDayOfMonth() + "-" + fromDate.getMonthValue() + "-" + fromDate.getYear();
        String untilDateString = untilDate.getDayOfMonth() + "-" + untilDate.getMonthValue() + "-" + untilDate.getYear();

//      IF DAYS EXIST (!= null) it means that it's a return with a
//      flexible dates and the user from a days int wants options between
//      two dates
//      IF DAYS DON'T EXIST (== null) it means that it's a return with an
//      exact dates and the user want flights from the fromDate until the
//      UntilDate, the same dates

//      Calculate the duration difference between both dates
        long daysRange = ChronoUnit.DAYS.between(fromDate, untilDate);


        if(days != null) {
            if (daysRange < days) {
                return new ResponseEntity<>(
                        "It's necessary to enter a valid " + "number of days," +
                                " because " + "between " + fromDateString +
                                " to " + untilDateString + " are " + (daysRange) + (daysRange>1?" days":"day"),
                        HttpStatus.BAD_REQUEST);
            }
        }

        if (days == null) {
            days = (int) daysRange - 1 ;
        }


//      The structure will be like this:
//          fromDate to untilDate:
//              |_____ origin to destination:
//                        |________ oneWay:
//                        |           |______ List<Flights>
//                        |________ return:
//                                    |______ List<Flights>
//      EXAMPLE:
//         15-07-2024 to 20-07-2024:
//              |_____ BCN to ZURICH:
//                        |________ oneWay:
//                        |           |______ List<Flights>
//                        |                    (origin: BCN,
//                        |                     destination: Zurich,
//                        |                     dateTakeOff: 15-07-2024)
//                        |________ return:
//                                    |______ List<Flights>
//                                             (origin: Zurich,
//                                              destination: BCN,
//                                              dateTakeOff: 20-07-2024)
//            ... (More origin destination and more dates in case exist)
//
//      In case there's no destination will create a Loop inside the
//      list of flights from the oneWay, and the destination will be
//      automatically the same as the index destination of each flight
//      from the list

        Map<String, Map<String, Map<String, List<Flight>>>> flights = new LinkedHashMap<>();

        for (int i = 0; i <= (daysRange - days); i++) {
            Map<String, Map<String, List<Flight>>> flightsOriginDestination = new HashMap<>();
            Map<String, List<Flight>> flightsOneWayReturn = new HashMap<>();
            LocalDate startDate = fromDate.plusDays(i);
            LocalDate finalDate = startDate.plusDays(days);

            List<Flight> flightsOneWay;
            List<Flight> flightsReturn;

            List<String> destinations = new ArrayList<>();
            if (destination == null) {
                flightsOneWay = flightService.findOneWayFlightNoDestination(
                        origin, startDate, seatsNumber);
            } else {
                flightsOneWay = flightService.findOneWayFlight(origin,
                                                               destination,
                                                               startDate,
                                                               seatsNumber);
            }
            if (!flightsOneWay.isEmpty()) {
                flightsOneWay.forEach(flight -> {
                    if (!destinations.contains(flight.getDestination())) {
                        destinations.add(flight.getDestination());
                    }
                });
            }
                for (String destinationOneWay : destinations) {
                    flightsOneWay = flightService.findOneWayFlight(origin,
                                                                   destinationOneWay,startDate,seatsNumber);
                    flightsReturn = flightService.findOneWayFlight(
                            destinationOneWay, origin, finalDate, seatsNumber);
                    if (price != null) {
                        flightsReturn = flightsReturn.stream().filter(
                                flight -> flight.getPrice() <= price).toList();
                        flightsOneWay = flightsOneWay.stream().filter(
                                flight -> flight.getPrice() <= price).toList();
                    }

                    if (seatType != null) {
                        if (!seatType.equalsIgnoreCase(
                                "Business") && !seatType.equalsIgnoreCase(
                                "Economy") && !seatType.equalsIgnoreCase(
                                "Premium Economy") && !seatType.equalsIgnoreCase(
                                "First Class"))
                        {
                            return new ResponseEntity<>(
                                    seatType + " is not a valid type of class seat",
                                    HttpStatus.BAD_REQUEST);
                        }
                        if (!flightsReturn.isEmpty() && !flightsOneWay.isEmpty()) {
                            List<Flight> seatTypeFlightsOneWay = flightsOneWay.stream().filter(
                                    flight -> flight.getSeatType().equalsIgnoreCase(
                                            seatType)).toList();
                            List<Flight> seatTypeFlightsReturn = flightsReturn.stream().filter(
                                    flight -> flight.getSeatType().equalsIgnoreCase(
                                            seatType)).toList();

                            if (!seatTypeFlightsOneWay.isEmpty() && !seatTypeFlightsReturn.isEmpty()) {
                                flightsOneWayReturn.put("oneWay",
                                                        seatTypeFlightsOneWay);
                                flightsOneWayReturn.put("return",
                                                        seatTypeFlightsReturn);
                                flightsOriginDestination.put(
                                        origin + " to " + destinationOneWay,
                                        flightsOneWayReturn);

                            }
                        }
                    } else {
                        if (!flightsReturn.isEmpty() && !flightsOneWay.isEmpty()) {
                            flightsOneWayReturn.put("oneWay", flightsOneWay);
                            flightsOneWayReturn.put("return", flightsReturn);
                            flightsOriginDestination.put(
                                    origin + " to " + destinationOneWay,
                                    flightsOneWayReturn);
                        }
                    }
                }
                if (!flightsOriginDestination.isEmpty()) {
                    flights.put(startDate + " to " + finalDate,
                                flightsOriginDestination);
                }
            }
        if(flights.isEmpty()){
            return new ResponseEntity<>("Sorry there's no flights for this " +
                                                "search",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }


}
