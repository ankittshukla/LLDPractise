package org.example.elevatorSystem;

public class DisplayInnerPannel {
    public void showInfo(int floorNumber, Direction dir) {
        String dirString = switch (dir) {
            case UP -> "Up";
            case DOWN -> "Down";
            case IDLE -> "Idle";
        };
        System.out.println("Floor no : "  + floorNumber + " Dir : " + dirString);
    }
}
