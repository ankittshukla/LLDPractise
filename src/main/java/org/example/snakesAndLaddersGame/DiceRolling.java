package org.example.snakesAndLaddersGame;

import java.util.concurrent.ThreadLocalRandom;

public class DiceRolling {
    int count;
    public DiceRolling(int diceCount){
        this.count = diceCount;
    }
    public int rollDice(){
        int maxValue = 6 * count;
        return ThreadLocalRandom.current().nextInt(count, maxValue + 1);
    }
}
