package org.example.bookMyShow;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class City {
    private final String name;
    private final List<Theatre> theatreList;

    public City(String name){
        this.name = name;
        this.theatreList = new ArrayList<>();
    }

    public List<Movie> getMovieList(){
        return theatreList.stream()
                .flatMap(theatre -> theatre.getScreenList().stream())
                .flatMap(screen -> screen.getShowList().stream())
                .map(Show::getMovie)
                .distinct()
                .toList();
    }

    public void addTheatre(Theatre theatre){
        theatreList.add(theatre);
    }

    public Optional<Theatre> getTheatreById(int theatreId) {
        return theatreList.stream()
                .filter(theatre -> theatre.getId() == theatreId)
                .findFirst();
    }
}
