package org.example.bookMyShow;

import lombok.Getter;

@Getter
public class Seat {
    int id;
    SeatStatus status;
    SeatCategory category;
    public Seat(int id, SeatStatus status, SeatCategory seatCategory){
        this.id = id;
        this.status = status;
        this.category = seatCategory;
    }
}
