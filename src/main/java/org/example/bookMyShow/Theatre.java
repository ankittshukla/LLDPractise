package org.example.bookMyShow;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class Theatre {
    private final int id;
    private final String name;
    private final List<Screen> screenList;

    public Theatre(int id, String name){
        this.id = id;
        this.name = name;
        this.screenList = new ArrayList<>();
    }

    public void addScreen(Screen screen){
        screenList.add(screen);
    }

    public Optional<Screen> getScreenById(int screenId) {
        return screenList.stream()
                .filter(screen -> screen.getId() == screenId)
                .findFirst();
    }
}
