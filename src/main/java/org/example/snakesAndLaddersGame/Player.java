package org.example.snakesAndLaddersGame;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    String name;
    int id;
    Cell currPos;
    public Player(String name, int id){
        this.name = name;
        this.id = id;
    }
}
