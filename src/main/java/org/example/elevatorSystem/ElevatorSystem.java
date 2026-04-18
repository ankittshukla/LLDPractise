package org.example.elevatorSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElevatorSystem {
    private static final int MIN_FLOOR = 0;
    private final List<ElevatorCar> elevators;
    private final List<Floor> floors;
    private final int maxFloor;

    public ElevatorSystem(int noOfElevators, int numberOfFloors,
                          ElevatorAssignStrategy elevatorAssignStrategy) {
        if (noOfElevators <= 0 || numberOfFloors <= 0) {
            throw new IllegalArgumentException("Elevators and floors must be positive");
        }
        elevators = new ArrayList<>(noOfElevators);
        floors = new ArrayList<>(numberOfFloors);
        maxFloor = numberOfFloors - 1;
        ElevatorDispatcher elevatorDispatcher =
                new ElevatorDispatcher(elevatorAssignStrategy, elevators);
        createElevators(noOfElevators);
        createFloors(numberOfFloors, elevatorDispatcher);
    }

    private void createElevators(int noOfElevators) {
        for (int i = 0; i < noOfElevators; i++) {
            elevators.add(new ElevatorCar(i, MIN_FLOOR, maxFloor));
        }
    }

    private void createFloors(int numberOfFloors, ElevatorDispatcher elevatorDispatcher) {
        for (int i = 0; i < numberOfFloors; i++) {
            floors.add(new Floor(i, MIN_FLOOR, maxFloor, elevatorDispatcher));
        }
    }

    public Floor getFloor(int floorNo) {
        return floors.get(floorNo);
    }

    public ElevatorCar getElevator(int elevatorId) {
        return elevators.get(elevatorId);
    }

    public List<ElevatorCar> getElevators() {
        return Collections.unmodifiableList(elevators);
    }

    public List<Floor> getFloors() {
        return Collections.unmodifiableList(floors);
    }

    public void step() {
        for (ElevatorCar elevator : elevators) {
            elevator.step();
        }
    }

    public void runUntilIdle(int maxSteps) {
        for (int step = 0; step < maxSteps && hasPendingWork(); step++) {
            step();
        }
    }

    public boolean hasPendingWork() {
        for (ElevatorCar elevator : elevators) {
            if (elevator.hasPendingRequests() || elevator.getCurrentState() == State.DOOR_OPEN) {
                return true;
            }
        }
        return false;
    }
}
