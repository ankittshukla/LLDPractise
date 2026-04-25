package org.example.bookMyShow;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class MovieBookingApp {
    private final List<City> cityList;

    public MovieBookingApp(){
        this.cityList = new ArrayList<>();
    }

    public void addCity(City city){
        cityList.add(city);
    }

    public Optional<City> getCityByName(String cityName) {
        return cityList.stream()
                .filter(city -> city.getName().equalsIgnoreCase(cityName))
                .findFirst();
    }
}
