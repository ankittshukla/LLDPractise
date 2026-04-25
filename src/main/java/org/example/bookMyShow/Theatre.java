package org.example.bookMyShow;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Theatre {
    int id;
    List<Screen> screenList;
    public Theatre(){
        screenList = new ArrayList<>();
    }
    public void addScreen(Screen screen){
        screenList.add(screen);
    }
}
