package org.example.carRentalSystem;

import lombok.Getter;

@Getter
public class User {
    private final String name;
    private final String email;
    private final String driversLicenseNumber;

    public User(String name, String email) {
        this(name, email, "");
    }

    public User(String name, String email, String driversLicenseNumber) {
        if (name == null || name.isBlank() || email == null || email.isBlank()) {
            throw new IllegalArgumentException("User name and email are required");
        }
        this.name = name;
        this.email = email;
        this.driversLicenseNumber = driversLicenseNumber == null ? "" : driversLicenseNumber;
    }
}
