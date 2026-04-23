package org.example.carRentalSystem;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Reservation {
    private final String id;
    private final User user;
    private final Car reservedCar;
    private final BookedTime bookedTime;
    private ReservationStatus status;

    public Reservation(User user, Car reservedCar, BookedTime bookedTime) {
        if (user == null || reservedCar == null || bookedTime == null) {
            throw new IllegalArgumentException("Reservation fields cannot be null");
        }
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.reservedCar = reservedCar;
        this.bookedTime = bookedTime;
        this.status = ReservationStatus.CONFIRMED;
    }

    public boolean blocksInventory() {
        return status == ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        if (status != ReservationStatus.CONFIRMED) {
            throw new IllegalStateException("Only confirmed reservations can be cancelled");
        }
        status = ReservationStatus.CANCELLED;
    }
}
