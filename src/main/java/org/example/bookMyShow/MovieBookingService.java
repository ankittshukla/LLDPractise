package org.example.bookMyShow;

import java.util.List;

public class MovieBookingService {
    public void createBooking(MovieUser movieUser, List<Integer> seatList, Show show,
                                 Screen screen, Theatre theatre, Payment payment){
        if(!show.lockSeats(seatList))
            throw new RuntimeException("Seat unavailable");

        if(payment.getStatus() != PaymentStatus.PAID){
            show.releaseSeats(seatList);
            throw new RuntimeException("Payment not made");
        }
        else {
            show.confirmSeats(seatList);
            Booking booking = new Booking(movieUser, show, seatList, screen, theatre);
        }
    }
}
