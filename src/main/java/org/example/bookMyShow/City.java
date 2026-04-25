package org.example.bookMyShow;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class City {
    List<Theatre> theatreList;
    public City(){
        theatreList = new ArrayList<>();
    }
    public List<Movie> getMovieList(){
        Set<Movie> movieSet = new HashSet<>();
        for(Theatre theatre : theatreList){
            for(Screen screen : theatre.getScreenList()){
                for(Show show: screen.getShowList()){
                    movieSet.add(show.getMovie());
                }
            }
        }
        return new ArrayList<>(movieSet);
    }
    public void addTheatre(Theatre theatre){
        theatreList.add(theatre);
    }
}
