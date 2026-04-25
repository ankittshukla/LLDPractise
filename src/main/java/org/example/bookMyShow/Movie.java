package org.example.bookMyShow;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Movie {
    private final String name;
    private final int id;

    public Movie(String name, int id){
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie movie)) {
            return false;
        }
        return id == movie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
