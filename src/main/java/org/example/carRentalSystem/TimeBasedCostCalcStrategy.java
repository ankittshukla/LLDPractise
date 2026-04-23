package org.example.carRentalSystem;

import java.time.Duration;

public class TimeBasedCostCalcStrategy implements CostCalculationStrategy {

    @Override
    public double calculateCost(Reservation reservation) {
        Car reservedCar = reservation.getReservedCar();
        BookedTime bookedTime = reservation.getBookedTime();
        return calculateHours(bookedTime) * reservedCar.getRentPerHour();
    }

    double calculateHours(BookedTime bookedTime) {
        Duration duration = Duration.between(bookedTime.getFrom(), bookedTime.getTo());
        return duration.toMinutes() / 60.0;
    }
}
