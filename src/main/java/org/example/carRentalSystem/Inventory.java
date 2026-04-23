package org.example.carRentalSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Inventory {
    private final List<Car> carList;

    public Inventory() {
        this.carList = new ArrayList<>();
    }

    public List<Car> getCarList() {
        return List.copyOf(carList);
    }

    public void addCarToInventory(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null");
        }
        carList.add(car);
    }

    public Optional<Car> findCarById(int carId) {
        return carList.stream()
                .filter(car -> car.getId() == carId)
                .findFirst();
    }
}
