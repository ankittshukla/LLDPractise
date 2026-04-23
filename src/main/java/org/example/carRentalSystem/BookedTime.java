package org.example.carRentalSystem;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookedTime {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public BookedTime(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Booking window cannot be null");
        }
        if (!from.isBefore(to)) {
            throw new IllegalArgumentException("Booking start must be before booking end");
        }
        this.from = from;
        this.to = to;
    }

    public boolean overlaps(BookedTime other) {
        return from.isBefore(other.to) && to.isAfter(other.from);
    }
}
