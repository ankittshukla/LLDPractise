package org.example.elevatorSystem;

public class FloorRequest extends ElevatorRequest {
    private final Direction direction;

    public FloorRequest(int floorNo, Direction direction) {
        super(floorNo);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
