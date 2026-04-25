package org.example.bookMyShow;

import lombok.Getter;

@Getter
public class MovieUser {
    private final String name;
    private final int id;

    public MovieUser(String name, int id){
        this.name = name;
        this.id = id;
    }
}
