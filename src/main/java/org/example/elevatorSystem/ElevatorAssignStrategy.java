package org.example.elevatorSystem;

import java.util.List;

public interface ElevatorAssignStrategy {
    ElevatorCar assignElevator(ElevatorRequest request, List<ElevatorCar> elevators);
}
