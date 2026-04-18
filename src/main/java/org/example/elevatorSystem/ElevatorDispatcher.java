package org.example.elevatorSystem;

import java.util.List;

public class ElevatorDispatcher {
    private final ElevatorAssignStrategy elevatorAssignStrategy;
    private final List<ElevatorCar> elevators;

    public ElevatorDispatcher(ElevatorAssignStrategy elevatorAssignStrategy, List<ElevatorCar> elevators) {
        this.elevatorAssignStrategy = elevatorAssignStrategy;
        this.elevators = elevators;
    }

    public ElevatorCar dispatchElevator(FloorRequest request) {
        ElevatorCar elevatorCar = elevatorAssignStrategy.assignElevator(request, elevators);
        elevatorCar.submitExternalRequest(request);
        return elevatorCar;
    }
}
