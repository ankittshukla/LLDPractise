package org.example.carRentalSystem;

import lombok.Getter;

@Getter
public class Bill {
    private final Reservation reservation;
    private final double totalAmount;

    public Bill(Reservation reservation, CostCalculationStrategy costCalculationStrategy) {
        if (reservation == null || costCalculationStrategy == null) {
            throw new IllegalArgumentException("Bill inputs cannot be null");
        }
        this.reservation = reservation;
        this.totalAmount = costCalculationStrategy.calculateCost(reservation);
    }
}
