package org.example.bookMyShow;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class Booking {
    UUID bookingId;
    MovieUser movieUser;
    List<Integer> seatList;
    Screen screen;
    Show show;
    Theatre theatre;

    public Booking(MovieUser movieUser, Show show, List<Integer> seatList, Screen screen, Theatre theatre){
        bookingId = UUID.randomUUID();
        this.movieUser = movieUser;
        this.show = show;
        this.screen = screen;
        this.seatList = seatList;
        this.theatre = theatre;
    }
}
