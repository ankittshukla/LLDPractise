package org.example.elevatorSystem;

import java.util.NavigableSet;
import java.util.TreeSet;

public class ElevatorController {
    private final NavigableSet<Integer> upStops;
    private final NavigableSet<Integer> downStops;

    public ElevatorController() {
        upStops = new TreeSet<>();
        downStops = new TreeSet<>();
    }

    public void submitExternalRequest(FloorRequest request, int currentFloor) {
        Direction direction = request.getDirection();
        if (request.getFloorNo() == currentFloor) {
            addStop(request.getFloorNo(), direction);
            return;
        }
        addStop(request.getFloorNo(), direction);
    }

    public void submitInternalRequest(InsideRequest request, int currentFloor) {
        Direction direction = request.getFloorNo() >= currentFloor ? Direction.UP : Direction.DOWN;
        addStop(request.getFloorNo(), direction);
    }

    public boolean hasPendingStops() {
        return !upStops.isEmpty() || !downStops.isEmpty();
    }

    public Integer getNextStop(int currentFloor, Direction currentDirection) {
        if (!hasPendingStops()) {
            return null;
        }

        if (currentDirection == Direction.UP) {
            Integer nextUpStop = upStops.ceiling(currentFloor);
            if (nextUpStop != null) {
                return nextUpStop;
            }
            return downStops.isEmpty() ? upStops.last() : downStops.last();
        }
        if (currentDirection == Direction.DOWN) {
            Integer nextDownStop = downStops.floor(currentFloor);
            if (nextDownStop != null) {
                return nextDownStop;
            }
            return upStops.isEmpty() ? downStops.last() : upStops.first();
        }

        Integer upCandidate = upStops.isEmpty() ? null : upStops.ceiling(currentFloor);
        Integer downCandidate = downStops.isEmpty() ? null : downStops.floor(currentFloor);
        if (upCandidate == null) {
            return downStops.last();
        }
        if (downCandidate == null) {
            return upStops.first();
        }

        int upDistance = Math.abs(upCandidate - currentFloor);
        int downDistance = Math.abs(downCandidate - currentFloor);
        return upDistance <= downDistance ? upCandidate : downCandidate;
    }

    public boolean shouldStopAt(int floorNo, Direction travelDirection) {
        if (travelDirection == Direction.UP) {
            return upStops.contains(floorNo);
        }
        if (travelDirection == Direction.DOWN) {
            return downStops.contains(floorNo);
        }
        return upStops.contains(floorNo) || downStops.contains(floorNo);
    }

    public void completeStop(int floorNo) {
        upStops.remove(floorNo);
        downStops.remove(floorNo);
    }

    public int getPendingStopCount() {
        return upStops.size() + downStops.size();
    }

    public Integer getHighestUpStop() {
        return upStops.isEmpty() ? null : upStops.last();
    }

    public Integer getLowestDownStop() {
        return downStops.isEmpty() ? null : downStops.first();
    }

    private void addStop(int floorNo, Direction direction) {
        if (direction == Direction.DOWN) {
            downStops.add(floorNo);
            return;
        }
        upStops.add(floorNo);
    }
}
