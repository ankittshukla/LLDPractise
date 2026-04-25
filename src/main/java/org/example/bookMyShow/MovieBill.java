package org.example.bookMyShow;

import java.util.List;

public class MovieBill {
    private final Show show;
    private final List<Integer> seatIds;
    private final double totalPrice;

    public MovieBill(Show show, List<Integer> seatIds) {
        this.show = show;
        this.seatIds = List.copyOf(seatIds);
        this.totalPrice = seatIds.stream()
                .mapToDouble(show::getPriceForSeatId)
                .sum();
    }

    public Show getShow() {
        return show;
    }

    public List<Integer> getSeatIds() {
        return seatIds;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
