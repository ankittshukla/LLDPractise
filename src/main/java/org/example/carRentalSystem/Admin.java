package org.example.carRentalSystem;

import lombok.Getter;

@Getter
public class Admin {
    private final String name;
    private final int id;
    private final Inventory inventory;

    public Admin(String name, int id) {
        this(name, id, new Inventory());
    }

    public Admin(String name, int id, Inventory inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory cannot be null");
        }
        this.name = name;
        this.id = id;
        this.inventory = inventory;
    }

    public void addToInventory(Car car) {
        inventory.addCarToInventory(car);
    }
}
