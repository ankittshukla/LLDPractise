package org.example.bookMyShow;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class Booking {
    private final UUID bookingId;
    private final MovieUser movieUser;
    private final List<Integer> seatList;
    private final Screen screen;
    private final Show show;
    private final Theatre theatre;
    private final MovieBill movieBill;
    private Payment payment;
    private BookingStatus status;

    public Booking(
            MovieUser movieUser,
            Show show,
            List<Integer> seatList,
            Screen screen,
            Theatre theatre,
            MovieBill movieBill
    ){
        this.bookingId = UUID.randomUUID();
        this.movieUser = movieUser;
        this.show = show;
        this.screen = screen;
        this.seatList = List.copyOf(seatList);
        this.theatre = theatre;
        this.movieBill = movieBill;
        this.status = BookingStatus.PAYMENT_PENDING;
    }

    public void markConfirmed(Payment payment) {
        this.payment = payment;
        this.status = BookingStatus.CONFIRMED;
    }

    public void markCancelled(Payment payment) {
        this.payment = payment;
        this.status = BookingStatus.CANCELLED;
    }
}
