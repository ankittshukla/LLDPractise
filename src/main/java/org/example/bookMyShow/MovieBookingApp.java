package org.example.bookMyShow;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MovieBookingApp {
    List<City> cityList;
    public MovieBookingApp(){
        cityList = new ArrayList<>();
    }
    public void addCity(City city){
        cityList.add(city);
    }
}
