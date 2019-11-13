package api;

import api.weather.Weather;

public class Main {

    public static void main(String[] args) {
        Weather weather = new Weather();
        weather.query();
    }
}
