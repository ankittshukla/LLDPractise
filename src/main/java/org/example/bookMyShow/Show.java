package org.example.bookMyShow;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class Show {
    int id;
    LocalDateTime from;
    LocalDateTime to;
    Movie movie;
    HashMap<SeatCategory, Double> seatCategoryPricing;
    HashMap<Integer, SeatStatus> seatStatusMap;
    HashMap<Integer, ReentrantLock> seatLocks;
    HashMap<Integer, Seat> seatToIdMap;
    public Show(LocalDateTime from, LocalDateTime to, Movie movie,
                HashMap<SeatCategory, Double> seatCategoryPricing, Screen screen){
        this.from = from;
        this.to = to;
        this.movie = movie;
        this.seatCategoryPricing = seatCategoryPricing;
        this.seatToIdMap = new HashMap<>();
        this.seatLocks = new HashMap<>();
        this.seatStatusMap = new HashMap<>();
        for(Seat seat : screen.getSeatList()){
            seatStatusMap.put(seat.getId(), SeatStatus.AVAILABLE);
            seatLocks.put(seat.getId(), new ReentrantLock());
            seatToIdMap.put(seat.getId(), seat);
        }
    }
    public boolean lockSeats(List<Integer> seatIds){
        List<Integer> sorted = new ArrayList<>(seatIds);
        Collections.sort(sorted);
        List<ReentrantLock> acquiredLocks = new ArrayList<>();

        try{
            for(int seatId: sorted){
                ReentrantLock lock = seatLocks.get(seatId);
                lock.lock();
                acquiredLocks.add(lock);
            }

            for(int seatId : sorted){
                if(seatStatusMap.get(seatId) != SeatStatus.AVAILABLE){
                    return false;
                }
            }

            for(int seatId : sorted){
                seatStatusMap.put(seatId, SeatStatus.LOCKED);
            }
            return true;
        }
        finally{
            for(ReentrantLock lock: acquiredLocks){
                lock.unlock();
            }
        }
    }
    public void confirmSeats(List<Integer> seatIds){
        for(int seatId: seatIds){
            seatStatusMap.put(seatId, SeatStatus.BOOKED);
        }
    }

    public void releaseSeats(List<Integer> seatIds){
        for(int seatId: seatIds){
            seatStatusMap.put(seatId, SeatStatus.AVAILABLE);
        }
    }
    public double getPriceForSeatId(Integer seatId){
        Seat seat = seatToIdMap.get(seatId);
        SeatCategory seatCategory = seat.getCategory();
        return seatCategoryPricing.get(seatCategory);
    }
}
