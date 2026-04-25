package org.example.bookMyShow;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class Show {
    private final int id;
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final Movie movie;
    private final Screen screen;
    private final Map<SeatCategory, Double> seatCategoryPricing;
    private final Map<Integer, SeatStatus> seatStatusMap;
    private final Map<Integer, ReentrantLock> seatLocks;
    private final Map<Integer, Seat> seatByIdMap;

    public Show(
            int id,
            LocalDateTime from,
            LocalDateTime to,
            Movie movie,
            Map<SeatCategory, Double> seatCategoryPricing,
            Screen screen
    ){
        validateShowWindow(from, to);
        this.id = id;
        this.from = from;
        this.to = to;
        this.movie = movie;
        this.screen = screen;
        this.seatCategoryPricing = Map.copyOf(seatCategoryPricing);
        this.seatByIdMap = new HashMap<>();
        this.seatLocks = new HashMap<>();
        this.seatStatusMap = new HashMap<>();
        for (Seat seat : screen.getSeatList()) {
            validatePricingExistsForSeat(seat);
            seatStatusMap.put(seat.getId(), SeatStatus.AVAILABLE);
            seatLocks.put(seat.getId(), new ReentrantLock());
            seatByIdMap.put(seat.getId(), seat);
        }
    }

    public boolean reserveSeats(List<Integer> seatIds){
        List<Integer> sorted = new ArrayList<>(seatIds);
        Collections.sort(sorted);
        List<ReentrantLock> acquiredLocks = new ArrayList<>();

        validateSeatIds(sorted);
        try {
            for (int seatId : sorted) {
                ReentrantLock lock = seatLocks.get(seatId);
                lock.lock();
                acquiredLocks.add(lock);
            }

            for (int seatId : sorted) {
                if (seatStatusMap.get(seatId) != SeatStatus.AVAILABLE) {
                    return false;
                }
            }

            for (int seatId : sorted) {
                seatStatusMap.put(seatId, SeatStatus.LOCKED);
            }
            return true;
        } finally {
            for (ReentrantLock lock : acquiredLocks) {
                lock.unlock();
            }
        }
    }

    public void confirmSeats(List<Integer> seatIds){
        updateSeatState(seatIds, SeatStatus.LOCKED, SeatStatus.BOOKED);
    }

    public void releaseSeats(List<Integer> seatIds){
        updateSeatState(seatIds, SeatStatus.LOCKED, SeatStatus.AVAILABLE);
    }

    public double getPriceForSeatId(Integer seatId){
        validateSeatIds(List.of(seatId));
        Seat seat = seatByIdMap.get(seatId);
        return seatCategoryPricing.get(seat.getCategory());
    }

    public SeatStatus getSeatStatus(int seatId) {
        validateSeatIds(List.of(seatId));
        return seatStatusMap.get(seatId);
    }

    private void updateSeatState(List<Integer> seatIds, SeatStatus expectedStatus, SeatStatus newStatus) {
        List<Integer> sorted = new ArrayList<>(seatIds);
        Collections.sort(sorted);
        List<ReentrantLock> acquiredLocks = new ArrayList<>();

        validateSeatIds(sorted);
        try {
            for (int seatId : sorted) {
                ReentrantLock lock = seatLocks.get(seatId);
                lock.lock();
                acquiredLocks.add(lock);
            }

            for (int seatId : sorted) {
                SeatStatus currentStatus = seatStatusMap.get(seatId);
                if (currentStatus != expectedStatus) {
                    throw new IllegalStateException(
                            "Seat " + seatId + " expected in state " + expectedStatus + " but found " + currentStatus
                    );
                }
            }

            for (int seatId : sorted) {
                seatStatusMap.put(seatId, newStatus);
            }
        } finally {
            for (ReentrantLock lock : acquiredLocks) {
                lock.unlock();
            }
        }
    }

    private void validateSeatIds(List<Integer> seatIds) {
        if (seatIds == null || seatIds.isEmpty()) {
            throw new IllegalArgumentException("At least one seat must be selected");
        }

        Set<Integer> uniqueSeatIds = new HashSet<>(seatIds);
        if (uniqueSeatIds.size() != seatIds.size()) {
            throw new IllegalArgumentException("Duplicate seat ids are not allowed");
        }

        for (int seatId : seatIds) {
            if (!seatByIdMap.containsKey(seatId)) {
                throw new IllegalArgumentException("Seat " + seatId + " does not exist for show " + id);
            }
        }
    }

    private void validatePricingExistsForSeat(Seat seat) {
        if (!seatCategoryPricing.containsKey(seat.getCategory())) {
            throw new IllegalArgumentException("No pricing configured for seat category " + seat.getCategory());
        }
    }

    private void validateShowWindow(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null || !from.isBefore(to)) {
            throw new IllegalArgumentException("Show time window is invalid");
        }
    }
}
