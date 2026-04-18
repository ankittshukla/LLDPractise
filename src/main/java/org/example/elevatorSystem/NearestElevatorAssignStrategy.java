package org.example.elevatorSystem;

import java.util.List;

public class NearestElevatorAssignStrategy implements ElevatorAssignStrategy {
    @Override
    public ElevatorCar assignElevator(ElevatorRequest request, List<ElevatorCar> elevators) {
        if (elevators == null || elevators.isEmpty()) {
            throw new IllegalArgumentException("At least one elevator is required");
        }

        ElevatorCar nearestElevator = elevators.get(0);
        int bestScore = Integer.MAX_VALUE;
        for (ElevatorCar elevator : elevators) {
            int score = score(elevator, request);
            if (score < bestScore) {
                bestScore = score;
                nearestElevator = elevator;
            }
        }
        return nearestElevator;
    }

    private int score(ElevatorCar elevator, ElevatorRequest request) {
        int distance = Math.abs(elevator.getCurrentFloor() - request.getFloorNo());
        if (elevator.getCurrentState() == State.IDLE) {
            return distance + elevator.getPendingStopCount();
        }

        if (request instanceof FloorRequest floorRequest) {
            if (isMovingTowardsRequest(elevator, floorRequest)) {
                return distance + elevator.getPendingStopCount();
            }
            return distance
                    + elevator.getPendingStopCount()
                    + reversalPenalty(elevator, floorRequest.getFloorNo());
        }

        return distance + elevator.getPendingStopCount();
    }

    private boolean isMovingTowardsRequest(ElevatorCar elevator, FloorRequest request) {
        if (elevator.getCurrentDirection() != request.getDirection()) {
            return false;
        }

        if (request.getDirection() == Direction.UP) {
            Integer highestUpStop = elevator.getHighestUpStop();
            boolean requestIsAhead = request.getFloorNo() >= elevator.getCurrentFloor();
            boolean requestFitsCurrentRun = highestUpStop == null || request.getFloorNo() <= highestUpStop;
            return requestIsAhead && requestFitsCurrentRun;
        }

        Integer lowestDownStop = elevator.getLowestDownStop();
        boolean requestIsAhead = request.getFloorNo() <= elevator.getCurrentFloor();
        boolean requestFitsCurrentRun = lowestDownStop == null || request.getFloorNo() >= lowestDownStop;
        return requestIsAhead && requestFitsCurrentRun;
    }

    private int reversalPenalty(ElevatorCar elevator, int requestedFloor) {
        if (elevator.getCurrentDirection() == Direction.UP) {
            Integer highestUpStop = elevator.getHighestUpStop();
            int reversalPoint = highestUpStop == null ? elevator.getCurrentFloor() : highestUpStop;
            return Math.abs(reversalPoint - elevator.getCurrentFloor()) + Math.abs(reversalPoint - requestedFloor);
        }

        Integer lowestDownStop = elevator.getLowestDownStop();
        int reversalPoint = lowestDownStop == null ? elevator.getCurrentFloor() : lowestDownStop;
        return Math.abs(reversalPoint - elevator.getCurrentFloor()) + Math.abs(reversalPoint - requestedFloor);
    }
}
