package org.example.bookMyShow;

import java.util.List;

public class MovieBill {
    double totalPrice;
    Show show;
    List<Integer> seatIds;
    public double calculateTotal(Show show, List<Integer> seatIds){
        for(Integer seatId : seatIds){
            totalPrice += show.getPriceForSeatId(seatId);
        }
        return totalPrice;
    }
}
