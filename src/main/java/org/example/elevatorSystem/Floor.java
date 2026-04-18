package org.example.elevatorSystem;

public class Floor {
    private final int floorNumber;
    private final int minFloor;
    private final int maxFloor;
    private final ElevatorDispatcher elevatorDispatcher;

    public Floor(int floorNumber, int minFloor, int maxFloor, ElevatorDispatcher elevatorDispatcher) {
        this.floorNumber = floorNumber;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.elevatorDispatcher = elevatorDispatcher;
    }

    public ElevatorCar pressGoUp() {
        if (floorNumber == maxFloor) {
            throw new IllegalStateException("Top floor cannot request upward travel");
        }
        FloorRequest request = new FloorRequest(getFloorNumber(), Direction.UP);
        return elevatorDispatcher.dispatchElevator(request);
    }

    public ElevatorCar pressGoDown() {
        if (floorNumber == minFloor) {
            throw new IllegalStateException("Ground floor cannot request downward travel");
        }
        FloorRequest request = new FloorRequest(getFloorNumber(), Direction.DOWN);
        return elevatorDispatcher.dispatchElevator(request);
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
