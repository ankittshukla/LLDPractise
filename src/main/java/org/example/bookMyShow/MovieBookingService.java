package org.example.bookMyShow;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MovieBookingService {
    private final Map<UUID, Booking> bookingById;

    public MovieBookingService() {
        this.bookingById = new ConcurrentHashMap<>();
    }

    public Booking createBooking(
            MovieUser movieUser,
            List<Integer> seatIds,
            Show show,
            Screen screen,
            Theatre theatre,
            Payment payment
    ) {
        validateShowBelongsToScreen(show, screen);
        validateScreenBelongsToTheatre(screen, theatre);
        if (!show.reserveSeats(seatIds)) {
            throw new IllegalStateException("One or more seats are no longer available");
        }

        boolean seatsConfirmed = false;
        try {
            MovieBill movieBill = new MovieBill(show, seatIds);
            validatePayment(payment, movieBill);

            Booking booking = new Booking(movieUser, show, seatIds, screen, theatre, movieBill);
            bookingById.put(booking.getBookingId(), booking);

            if (payment.getStatus() == PaymentStatus.PAID) {
                show.confirmSeats(seatIds);
                booking.markConfirmed(payment);
                seatsConfirmed = true;
                return booking;
            }

            booking.markCancelled(payment);
            throw new IllegalStateException("Payment not completed");
        } catch (RuntimeException exception) {
            if (!seatsConfirmed) {
                show.releaseSeats(seatIds);
            }
            throw exception;
        }
    }

    public Booking getBooking(UUID bookingId) {
        Booking booking = bookingById.get(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found: " + bookingId);
        }
        return booking;
    }

    public void cancelBooking(UUID bookingId) {
        Booking booking = getBooking(bookingId);
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Only confirmed bookings can be cancelled");
        }

        booking.getShow().releaseSeats(booking.getSeatList());
        Payment refund = new Payment(booking.getMovieBill());
        refund.markRefunded();
        booking.markCancelled(refund);
    }

    public List<Booking> getBookingsForUser(int userId) {
        return bookingById.values().stream()
                .filter(booking -> booking.getMovieUser().getId() == userId)
                .sorted(Comparator.comparing(Booking::getBookingId))
                .toList();
    }

    private void validatePayment(Payment payment, MovieBill movieBill) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment is required");
        }
        if (Double.compare(payment.getAmount(), movieBill.getTotalPrice()) != 0) {
            throw new IllegalArgumentException("Payment amount does not match bill total");
        }
    }

    private void validateShowBelongsToScreen(Show show, Screen screen) {
        if (!screen.getShowList().contains(show) || show.getScreen() != screen) {
            throw new IllegalArgumentException("Show does not belong to screen");
        }
    }

    private void validateScreenBelongsToTheatre(Screen screen, Theatre theatre) {
        if (!theatre.getScreenList().contains(screen)) {
            throw new IllegalArgumentException("Screen does not belong to theatre");
        }
    }
}
