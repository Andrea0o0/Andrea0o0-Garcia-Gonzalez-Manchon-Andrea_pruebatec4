**Andrea García-González Manchón - Prueba Técnica 4:**

# Travel Agency Be_travel 

``` mermaid
classDiagram

class Hotel {
  + id: String
  + name: String
  + checkIn: String
+ checkOut: String
  + activeFlag: Boolean
  + description: String
  + rooms: List<Room>
}

class ReservedDate {
  + id: Long
+ date: LocalDate
+ room: Room
}

class HotelBooking {
    super: 
    - id: Long
    - activeFlag: Boolean
    ______________________
 + fromDate: LocalDate
+ untilDate: LocalDate
+ room: Room
+ peopleNumber: Integer
+ guests: List<Guest>
+ totalPrice: Double
}

class FlightBooking {
     super: 
    - id: Long
    - activeFlag: Boolean
    ______________________
  + origin: String
+ destination: String
+ bookingType: Boolean
+ flightOneWay: Flight
+ flightReturn: Flight
+ peopleNumber: Integer
+ seatType: String
+ infoPassengers: List<Passenger>
+ totalPrice: Double
}

class SeatFlight {
  + id: Long
+ rowSeat: Integer
+ columnSeat: Integer
+ seatString: String
+ price: Double
+ passengerOneWay: Passenger
+ passengerReturn: Passenger
+ flightId: Flight
}

class Passenger {
  + id: Long
+ name: String
+ lastName: String
+ identification: String
+ email: String
+ luggageList: List<Luggage>
+ luggagePrice: Double
+ seatFlightRowColumnOneWay: SeatFlight
+ seatFlightRowColumnReturn: SeatFlight
+ seatsPrice: Double
+ totalPrice: Double
+ flightBooking: FlightBooking
}

class Luggage {
  + id: Long
+ luggageType: String
+ description: String
+ invoiced: Boolean
+ model: Boolean
+ kg: Double
+ price: Double
+ passenger: Passenger
}

class Booking {
    - id: Long
    - activeFlag: Boolean
}

class Room {
    + id: Long
    + hotelName: String
    + place: String
    + roomNumber: String
    + roomType: Integer
    + roomTypeString: String
    + priceNight: Double
    + fromDate: LocalDate
    + untilDate: LocalDate
    + activeFlag: Boolean
    + reservedDates: List<ReservedDate>
    + description: String
    + bookings: List<HotelBooking>
    + hotel: Hotel
}

class Flight {
    + flightNumber: String
    + origin: String
    + destination: String
    + seatType: String
    + price: Double
    + takeoffDate: LocalDateTime
    + arrivalDate: LocalDateTime
    + rowsSeat: Integer
    + columnsSeat: Integer
    + seatPrice: Double
    + seatsNumber: Integer
    + reservedSeats: Integer
    + activeFlag: Boolean
    + oneWayBookings: List<FlightBooking>
    + returnBookings: List<FlightBooking>
    + seatsBooked: List<SeatFlight>
}

Booking <|-- HotelBooking: extends from Booking
Booking <|-- FlightBooking: extends from Booking

Hotel --|> Room: 1 to n
Room --|> Hotel: n to 1

Room --|> ReservedDate: 1 to n
ReservedDate --|> Room: n to 1

HotelBooking --|> Room: 1 to 1
Room --|> HotelBooking: 1 to n

FlightBooking --|> Flight: 1 to 1
Flight --|> FlightBooking: 1 to n

FlightBooking --|> SeatFlight: 1 to n
SeatFlight --|> FlightBooking: n to 1

Passenger --|> FlightBooking: 1 to n
FlightBooking --|> Passenger: n to 1

Luggage --|> Passenger: 1 to n
Passenger --|> Luggage: n to 1

```

## Important:
The routes for creating reservations, both flights and hotels, are public, because there are no users linked to the reservation, but there are guests and passengers, which is why it has been considered to make the route public.

## Requirements:

1. Clone the repository
2. Use IDE Intellij
3. Use JDK 17 or less (to avoid mistakes)
4. Download MySQL dependency
5. Import Postman Collection
7. Import Shift_Operator Database data to your Database
8. Run the code

## Assumptions:

### Room Model:
Each room must have a unique room number within a hotel.
The room type must be one of the following: Single, Double, Suite, Family.
Price per night should be a non-negative value.
The start date of the room availability must be before the end date.
Description of the room should provide comprehensive details about its features and amenities.

### Hotel Model:
Each hotel must have a unique hotel code.
Hotel name should be distinct and descriptive.
Place refers to the location where the hotel is situated.
Check-in and check-out times should be specified accurately.
The description of the hotel should include relevant information about its facilities, services, and location.

### Reserved Date Model:
Reserved dates should not overlap for the same room.
Each reserved date should correspond to an existing room.
Date validation should ensure that reserved dates are in the future and within the operational period of the hotel.

### Flight Model:
Each flight has a unique flight number, departure, and arrival locations.
Capacity represents maximum passengers; price per seat is non-negative.
Flight duration is calculated accurately based on departure and arrival times.

### FlightBooking Model:
Each booking has a unique reference number and is associated with a specific flight or flights.
Passenger details include name, email, and contact information.
Booking flag indicates confirmation, disabled or enabled.

### Client, Person, Employee Models:
We imagine that this API is in the process of being realized and that is why there are models, repositories, interfaces and services for them, for their future use.

### Be_Travel Database MySql:
I have exported the database information in [mysqlShift](https://github.com/Andrea0o0/Andrea0o0-Garcia-Gonzalez-Manchon-Andrea_pruebatec4/tree/main/sql) inside the folder.

### PostMan:
I have exported the database information in [postman](https://github.com/Andrea0o0/Andrea0o0-Garcia-Gonzalez-Manchon-Andrea_pruebatec4/tree/main/postman) inside the folder.
