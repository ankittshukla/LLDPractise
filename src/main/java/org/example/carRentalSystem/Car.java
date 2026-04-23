package org.example.carRentalSystem;

import lombok.Getter;

@Getter
public class Car {
    private final int id;
    private final String name;
    private final String model;
    private final double rentPerHour;

    public Car(int id, String name, String model, double rentPerHour) {
        if (rentPerHour <= 0) {
            throw new IllegalArgumentException("Rent per hour must be positive");
        }
        this.id = id;
        this.name = name;
        this.model = model;
        this.rentPerHour = rentPerHour;
    }

    public Car(String name, String model, double rentPerHour) {
        this(0, name, model, rentPerHour);
    }
}
