package org.example.bookMyShow;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieBookingServiceTest {

    @Test
    void shouldCreateConfirmedBookingAndBookSeats() {
        TestFixture fixture = new TestFixture();
        MovieBill movieBill = new MovieBill(fixture.show, List.of(1, 2));
        Payment payment = new Payment(movieBill);
        payment.markPaid();

        Booking booking = fixture.service.createBooking(
                fixture.user,
                List.of(1, 2),
                fixture.show,
                fixture.screen,
                fixture.theatre,
                payment
        );

        assertNotNull(booking.getBookingId());
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        assertEquals(700.0, booking.getMovieBill().getTotalPrice(), 0.0001);
        assertEquals(SeatStatus.BOOKED, fixture.show.getSeatStatus(1));
        assertEquals(SeatStatus.BOOKED, fixture.show.getSeatStatus(2));
        assertEquals(payment.getPaymentId(), booking.getPayment().getPaymentId());
    }

    @Test
    void shouldReleaseSeatWhenPaymentFails() {
        TestFixture fixture = new TestFixture();
        MovieBill movieBill = new MovieBill(fixture.show, List.of(1));
        Payment payment = new Payment(movieBill);
        payment.markFailed();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> fixture.service.createBooking(
                        fixture.user,
                        List.of(1),
                        fixture.show,
                        fixture.screen,
                        fixture.theatre,
                        payment
                )
        );

        assertTrue(exception.getMessage().contains("Payment not completed"));
        assertEquals(SeatStatus.AVAILABLE, fixture.show.getSeatStatus(1));
    }

    @Test
    void shouldRejectInvalidSeatIds() {
        TestFixture fixture = new TestFixture();
        MovieBill movieBill = new MovieBill(fixture.show, List.of(1));
        Payment payment = new Payment(movieBill);
        payment.markPaid();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> fixture.service.createBooking(
                        fixture.user,
                        List.of(99),
                        fixture.show,
                        fixture.screen,
                        fixture.theatre,
                        payment
                )
        );

        assertTrue(exception.getMessage().contains("does not exist"));
    }

    @Test
    void shouldCancelConfirmedBookingAndReleaseSeats() {
        TestFixture fixture = new TestFixture();
        MovieBill movieBill = new MovieBill(fixture.show, List.of(1));
        Payment payment = new Payment(movieBill);
        payment.markPaid();
        Booking booking = fixture.service.createBooking(
                fixture.user,
                List.of(1),
                fixture.show,
                fixture.screen,
                fixture.theatre,
                payment
        );

        fixture.service.cancelBooking(booking.getBookingId());

        Booking updatedBooking = fixture.service.getBooking(booking.getBookingId());
        assertEquals(BookingStatus.CANCELLED, updatedBooking.getStatus());
        assertEquals(PaymentStatus.REFUNDED, updatedBooking.getPayment().getStatus());
        assertEquals(SeatStatus.AVAILABLE, fixture.show.getSeatStatus(1));
    }

    @Test
    void shouldAllowOnlyOneConcurrentReservationForSameSeat() throws Exception {
        TestFixture fixture = new TestFixture();
        try (var executor = Executors.newFixedThreadPool(2)) {
            CountDownLatch ready = new CountDownLatch(2);
            CountDownLatch start = new CountDownLatch(1);

            Future<Boolean> firstAttempt = executor.submit(() -> attemptConcurrentBooking(fixture, ready, start));
            Future<Boolean> secondAttempt = executor.submit(() -> attemptConcurrentBooking(fixture, ready, start));

            ready.await();
            start.countDown();

            boolean firstResult = firstAttempt.get();
            boolean secondResult = secondAttempt.get();

            assertTrue(firstResult ^ secondResult);
            assertEquals(SeatStatus.BOOKED, fixture.show.getSeatStatus(1));
            assertEquals(1, fixture.service.getBookingsForUser(fixture.user.getId()).size());
        }
    }

    private boolean attemptConcurrentBooking(TestFixture fixture, CountDownLatch ready, CountDownLatch start) {
        try {
            ready.countDown();
            start.await();
            MovieBill movieBill = new MovieBill(fixture.show, List.of(1));
            Payment payment = new Payment(movieBill);
            payment.markPaid();
            fixture.service.createBooking(
                    fixture.user,
                    List.of(1),
                    fixture.show,
                    fixture.screen,
                    fixture.theatre,
                    payment
            );
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private static class TestFixture {
        private final MovieUser user = new MovieUser("Ankit", 1);
        private final Screen screen = new Screen(1, "Audi 1");
        private final Theatre theatre = new Theatre(1, "PVR");
        private final Movie movie = new Movie("Dhurander", 1);
        private final Show show;
        private final MovieBookingService service = new MovieBookingService();

        private TestFixture() {
            screen.addSeat(new Seat(1, SeatCategory.GOLD));
            screen.addSeat(new Seat(2, SeatCategory.SILVER));
            theatre.addScreen(screen);
            HashMap<SeatCategory, Double> pricing = new HashMap<>();
            pricing.put(SeatCategory.GOLD, 400.0);
            pricing.put(SeatCategory.SILVER, 300.0);
            show = new Show(
                    1,
                    LocalDateTime.of(2026, 4, 24, 10, 0),
                    LocalDateTime.of(2026, 4, 24, 13, 0),
                    movie,
                    pricing,
                    screen
            );
            screen.addShow(show);
        }
    }
}
