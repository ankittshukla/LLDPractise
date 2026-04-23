package org.example.carRentalSystem;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ManageBookingTest {

    @Test
    void shouldBookCarAndGenerateBill() {
        Inventory inventory = new Inventory();
        Car car = new Car(1, "Baleno", "top", 250.0);
        inventory.addCarToInventory(car);
        ManageBooking manageBooking = new ManageBooking(inventory);
        User user = new User("Ankit", "ankit@example.com", "DL-123");
        BookedTime slot = new BookedTime(
                LocalDateTime.of(2026, 4, 19, 10, 0),
                LocalDateTime.of(2026, 4, 19, 13, 30)
        );

        Reservation reservation = manageBooking.bookCar(car, user, slot);
        Bill bill = manageBooking.generateBill(reservation.getId());

        assertNotNull(reservation.getId());
        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
        assertEquals(875.0, bill.getTotalAmount(), 0.0001);
    }

    @Test
    void shouldRejectOverlappingReservationForSameCar() {
        Inventory inventory = new Inventory();
        Car car = new Car(1, "Taigun", "gt", 300.0);
        inventory.addCarToInventory(car);
        ManageBooking manageBooking = new ManageBooking(inventory);
        User firstUser = new User("A", "a@example.com", "DL-001");
        User secondUser = new User("B", "b@example.com", "DL-002");

        manageBooking.bookCar(
                car,
                firstUser,
                new BookedTime(
                        LocalDateTime.of(2026, 4, 20, 9, 0),
                        LocalDateTime.of(2026, 4, 20, 12, 0)
                )
        );

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> manageBooking.bookCar(
                        car,
                        secondUser,
                        new BookedTime(
                                LocalDateTime.of(2026, 4, 20, 11, 0),
                                LocalDateTime.of(2026, 4, 20, 14, 0)
                        )
                )
        );

        assertTrue(exception.getMessage().contains("not available"));
    }

    @Test
    void shouldReturnCarToAvailableListAfterCancellation() {
        Inventory inventory = new Inventory();
        Car car = new Car(1, "Innova", "zx", 500.0);
        inventory.addCarToInventory(car);
        ManageBooking manageBooking = new ManageBooking(inventory);
        User user = new User("A", "a@example.com", "DL-001");
        BookedTime slot = new BookedTime(
                LocalDateTime.of(2026, 4, 21, 8, 0),
                LocalDateTime.of(2026, 4, 21, 11, 0)
        );

        Reservation reservation = manageBooking.bookCar(car, user, slot);
        manageBooking.cancelReservation(reservation.getId());
        List<Car> availableCars = manageBooking.getListOfAvailableCars(slot);

        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
        assertEquals(1, availableCars.size());
        assertEquals(car.getId(), availableCars.getFirst().getId());
    }

    @Test
    void shouldRejectInvalidBookingWindow() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new BookedTime(
                        LocalDateTime.of(2026, 4, 22, 10, 0),
                        LocalDateTime.of(2026, 4, 22, 10, 0)
                )
        );

        assertFalse(exception.getMessage().isBlank());
    }
}
