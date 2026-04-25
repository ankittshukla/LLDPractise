package org.example.bookMyShow;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Screen {
    int id;
    List<Show> showList;
    List<Seat> seatList;
    public Screen(){
        showList = new ArrayList<>();
        seatList = new ArrayList<>();
    }
    public void addSeat(Seat seat){
        seatList.add(seat);
    }
    public void addShow(Show show){
        showList.add(show);
    }
}
