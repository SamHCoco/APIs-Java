package api.weather;

import java.util.Scanner;

public class Weather implements Runnable {

    private static final String URI = "https://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "c38b28e71f71ffadffd8a9125ee2871a";
    private static final String COUNTRY = "uk";
    private Connection connection;


    public void query(){
        Thread thread = new Thread(this);
        thread.start();
    }

    private void printWeatherData(String responseData){
        System.out.println(responseData);
    }

    public void run(){
    // Example - https://api.openweathermap.org/data/2.5/weather?q=london,uk&appid=c38b28e71f71ffadffd8a9125ee2871a
        connection = new Connection(URI, API_KEY);
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("************************************************************************");
            System.out.println("Enter UK city name for a weather report: ");
            String input = scanner.next();

            if(input.equals("quit")){
                connection.getConnection().disconnect();
                return;
            }

            String queryString = "?q=" + input + "," + COUNTRY;
            String response = connection.query(queryString);
            printWeatherData(response);
        }
    }
}
