package org.example;

import org.example.carRentalSystem.*;
import org.example.elevatorSystem.ElevatorCar;
import org.example.elevatorSystem.ElevatorSystem;
import org.example.elevatorSystem.Floor;
import org.example.elevatorSystem.NearestElevatorAssignStrategy;
import org.example.ticTacToe.TicTacToeGame;

import java.time.LocalDateTime;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //ticTacToeGameCalls();

        //elevatorSystemCalls();

        carRentalSystemCalls();
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
    public static void carRentalSystemCalls(){
        Inventory inventory = new Inventory();
        User user1 = new User("Ank", "ankitshukla@gmail.com", "DL-001");
        Car baleno = new Car(1, "baleno", "top", 250.0);
        Car taigun = new Car(2, "taigun", "top", 350.0);
        Car innova = new Car(3, "innova", "top", 500.0);
        Admin admin = new Admin("Rahul", 90, inventory);
        admin.addToInventory(baleno);
        admin.addToInventory(taigun);
        admin.addToInventory(innova);
        ManageBooking manageBooking = new ManageBooking(inventory);
        LocalDateTime reserveFrom = LocalDateTime.of(2026, 04, 19, 10, 0, 0);
        LocalDateTime reserveTo = LocalDateTime.of(2026, 04, 21, 15, 0, 0);
        BookedTime reservationTime = new BookedTime(reserveFrom, reserveTo);
        List<Car> availableCarList = manageBooking.getListOfAvailableCars(reservationTime);
        Car chosenCar = availableCarList.get(2);
        Reservation reservation = manageBooking.bookCar(chosenCar, user1, reservationTime);
        Bill bill = manageBooking.generateBill(reservation.getId());
        System.out.println("Reservation created: " + reservation.getId());
        System.out.println("Bill amount: " + bill.getTotalAmount());
    }
}
