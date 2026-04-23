package org.example.carRentalSystem;

public interface CostCalculationStrategy {
    public double calculateCost(Reservation reservation);
}
