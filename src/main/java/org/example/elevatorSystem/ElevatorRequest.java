package org.example.elevatorSystem;

public abstract class ElevatorRequest {
    private final int floorNo;

    protected ElevatorRequest(int floorNo) {
        this.floorNo = floorNo;
    }

    public int getFloorNo() {
        return floorNo;
    }
}
