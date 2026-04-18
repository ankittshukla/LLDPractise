package org.example;

import org.example.elevatorSystem.ElevatorCar;
import org.example.elevatorSystem.ElevatorSystem;
import org.example.elevatorSystem.Floor;
import org.example.elevatorSystem.NearestElevatorAssignStrategy;
import org.example.ticTacToe.TicTacToeGame;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //ticTacToeGameCalls();

        elevatorSystemCalls();
    }
    public static void ticTacToeGameCalls(){
        TicTacToeGame ticTacToeGame = new TicTacToeGame();
        ticTacToeGame.start(3, 3);
        ticTacToeGame.playGame();
    }
    public static void elevatorSystemCalls(){
        NearestElevatorAssignStrategy elevatorAssignStrategy =
                new NearestElevatorAssignStrategy();
        ElevatorSystem elevatorSystem = new ElevatorSystem(4,
                10, elevatorAssignStrategy );
        Floor floor5 = elevatorSystem.getFloor(5);
        ElevatorCar elevatorCar = floor5.pressGoDown();
        elevatorSystem.runUntilIdle(20);

        elevatorCar.goToFloor(3);
        elevatorSystem.getFloor(2).pressGoUp();
        elevatorSystem.runUntilIdle(30);
    }
}
