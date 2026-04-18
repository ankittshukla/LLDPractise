package org.example.elevatorSystem;

public class ElevatorCar {
    private final int elevatorId;
    private final int minFloor;
    private final int maxFloor;
    private int currentFloor;
    private Direction currentDirection;
    private final DisplayInnerPannel displayInnerPannel;
    private final ControlInnerPannel controlInnerPannel;
    private final ElevatorController elevatorController;
    private State currentState;

    public ElevatorCar(int elevatorId, int minFloor, int maxFloor) {
        displayInnerPannel = new DisplayInnerPannel();
        elevatorController = new ElevatorController();
        controlInnerPannel = new ControlInnerPannel(this);
        this.elevatorId = elevatorId;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        currentDirection = Direction.IDLE;
        currentState = State.IDLE;
    }

    public void goToFloor(int floorNo) {
        controlInnerPannel.pressFloorButton(floorNo);
    }

    public void submitExternalRequest(FloorRequest request) {
        validateFloor(request.getFloorNo());
        elevatorController.submitExternalRequest(request, currentFloor);
    }

    public void submitInternalRequest(int floorNo) {
        validateFloor(floorNo);
        elevatorController.submitInternalRequest(new InsideRequest(floorNo), currentFloor);
    }

    public void step() {
        if (!elevatorController.hasPendingStops()) {
            currentDirection = Direction.IDLE;
            currentState = State.IDLE;
            displayInnerPannel.showInfo(currentFloor, currentDirection);
            return;
        }

        if (currentState == State.DOOR_OPEN) {
            closeDoor();
        }

        Integer nextStop = elevatorController.getNextStop(currentFloor, currentDirection);
        if (nextStop == null) {
            currentDirection = Direction.IDLE;
            currentState = State.IDLE;
            return;
        }

        if (nextStop == currentFloor) {
            elevatorController.completeStop(currentFloor);
            openDoor();
            return;
        }

        moveOneFloorToward(nextStop);
        if (elevatorController.shouldStopAt(currentFloor, currentDirection)) {
            elevatorController.completeStop(currentFloor);
            openDoor();
        }
    }

    private void moveOneFloorToward(int destinationFloor) {
        int step = destinationFloor > currentFloor ? 1 : -1;
        currentFloor += step;
        currentDirection = step > 0 ? Direction.UP : Direction.DOWN;
        currentState = step > 0 ? State.MOVING_UP : State.MOVING_DOWN;
        displayInnerPannel.showInfo(currentFloor, currentDirection);
    }

    private void openDoor() {
        currentState = State.DOOR_OPEN;
        System.out.println("Elevator " + elevatorId + " door opened at floor " + currentFloor);
    }

    private void closeDoor() {
        currentState = State.IDLE;
        System.out.println("Elevator " + elevatorId + " door closed at floor " + currentFloor);
    }

    private void validateFloor(int floorNo) {
        if (floorNo < minFloor || floorNo > maxFloor) {
            throw new IllegalArgumentException(
                    "Floor " + floorNo + " is outside service range " + minFloor + " to " + maxFloor);
        }
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public State getCurrentState() {
        return currentState;
    }

    public boolean hasPendingRequests() {
        return elevatorController.hasPendingStops();
    }

    public int getPendingStopCount() {
        return elevatorController.getPendingStopCount();
    }

    public Integer getHighestUpStop() {
        return elevatorController.getHighestUpStop();
    }

    public Integer getLowestDownStop() {
        return elevatorController.getLowestDownStop();
    }
}
