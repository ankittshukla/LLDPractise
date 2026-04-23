package org.example.carRentalSystem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ManageBooking {
    private final Inventory inventory;
    private final CostCalculationStrategy costCalculationStrategy;
    private final Map<String, Reservation> reservations;

    public ManageBooking(Inventory inventory) {
        this(inventory, new TimeBasedCostCalcStrategy());
    }

    public ManageBooking(Inventory inventory, CostCalculationStrategy costCalculationStrategy) {
        if (inventory == null || costCalculationStrategy == null) {
            throw new IllegalArgumentException("Booking dependencies cannot be null");
        }
        this.inventory = inventory;
        this.costCalculationStrategy = costCalculationStrategy;
        this.reservations = new LinkedHashMap<>();
    }

    public List<Car> getListOfAvailableCars(BookedTime bookedTime) {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : inventory.getCarList()) {
            if (isCarAvailable(car, bookedTime)) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }

    public Reservation bookCar(Car car, User user, BookedTime bookedTime) {
        if (car == null || user == null || bookedTime == null) {
            throw new IllegalArgumentException("Booking inputs cannot be null");
        }
        if (!inventory.findCarById(car.getId()).isPresent()) {
            throw new IllegalArgumentException("Car is not present in inventory");
        }
        if (!isCarAvailable(car, bookedTime)) {
            throw new IllegalStateException("Car is not available for the selected window");
        }
        Reservation reservation = new Reservation(user, car, bookedTime);
        reservations.put(reservation.getId(), reservation);
        return reservation;
    }

    public void cancelReservation(String reservationId) {
        Reservation reservation = getReservation(reservationId)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found"));
        reservation.cancel();
    }

    public Optional<Reservation> getReservation(String reservationId) {
        return Optional.ofNullable(reservations.get(reservationId));
    }

    public Bill generateBill(String reservationId) {
        Reservation reservation = getReservation(reservationId)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found"));
        return new Bill(reservation, costCalculationStrategy);
    }

    private boolean isCarAvailable(Car car, BookedTime requestedWindow) {
        return reservations.values().stream()
                .filter(Reservation::blocksInventory)
                .filter(reservation -> reservation.getReservedCar().getId() == car.getId())
                .noneMatch(reservation -> reservation.getBookedTime().overlaps(requestedWindow));
    }
}
