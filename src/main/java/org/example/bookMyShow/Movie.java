package org.example.bookMyShow;

import lombok.Getter;

@Getter
public class Movie {
    String name;
    int id;
    public Movie(String name, int id){
        this.name = name;
        this.id = id;
    }
}
