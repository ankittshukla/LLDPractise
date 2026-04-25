package org.example.bookMyShow;

import lombok.Getter;

@Getter
public class Seat {
    private final int id;
    private final SeatCategory category;

    public Seat(int id, SeatCategory seatCategory){
        this.id = id;
        this.category = seatCategory;
    }
}
