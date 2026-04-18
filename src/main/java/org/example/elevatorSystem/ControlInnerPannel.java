package org.example.elevatorSystem;

public class ControlInnerPannel {
    private final ElevatorCar elevatorCar;

    public ControlInnerPannel(ElevatorCar elevatorCar) {
        this.elevatorCar = elevatorCar;
    }

    public void pressFloorButton(int floorNo) {
        elevatorCar.submitInternalRequest(floorNo);
    }
}
