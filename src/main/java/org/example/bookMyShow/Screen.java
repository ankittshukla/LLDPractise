package org.example.bookMyShow;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class Screen {
    private final int id;
    private final String name;
    private final List<Show> showList;
    private final List<Seat> seatList;

    public Screen(int id, String name){
        this.id = id;
        this.name = name;
        this.showList = new ArrayList<>();
        this.seatList = new ArrayList<>();
    }

    public void addSeat(Seat seat){
        seatList.add(seat);
    }

    public void addShow(Show show){
        showList.add(show);
    }

    public Optional<Seat> getSeatById(int seatId) {
        return seatList.stream()
                .filter(seat -> seat.getId() == seatId)
                .findFirst();
    }
}
