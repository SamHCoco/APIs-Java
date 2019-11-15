package api.weather;

import java.util.Scanner;
import org.json.JSONObject;

public class Weather implements Runnable {

    private static final String URI = "https://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "c38b28e71f71ffadffd8a9125ee2871a";
    private static final String COUNTRY = "uk";
    private static final String UNITS = "Metric";
    private Connection connection;

    /**
     * Calls the thread run method for an instantiated weather object,
     * to allow user to perform weather queries, based on locations, with the "Openweathermap API".
     */
    public void query(){
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Prints the JSON response data from 'Openweathermap API', specifically the following
     * data: country, temperature, description, and wind speed.
     * @param responseData The JSON response, in string form, from a 'Openweathermap API' GET request.
     */
    private void printWeatherData(String responseData){
        if(responseData != null){
            JSONObject data = new JSONObject(responseData);

            int temperature = data.getJSONObject("main").getInt("temp");
            int windSpeed = data.getJSONObject("wind").getInt("speed");
            String description = data.getJSONArray("weather").getJSONObject(0).getString("description");

            System.out.println(
                "Country: " + COUNTRY.toUpperCase() + "\n" +
                "Temperature: " + temperature + "°C" + "\n" +
                "Summary: " + description + "\n" +
                "Wind: " + windSpeed + " mph"
            );
        }
    }

    /**
     * Starts new thread, which handles prompting user for input for location of weather a query,
     * forming the URL to perform an 'Openweathermap API' GET request, and terminating the
     * application.
     */
    public void run(){
    // Example - https://api.openweathermap.org/data/2.5/weather?q=london,uk&appid=c38b28e71f71ffadffd8a9125ee2871a
        connection = new Connection();
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("Enter " + COUNTRY.toUpperCase() + " city name for weather report: ");
            String input = scanner.next();

            if(input.equals("quit")){
                connection.getConnection().disconnect();
                System.out.println("APPLICATION TERMINATED");
                return;
            }

            String queryString = URI + "?q=" + input + "," + COUNTRY + "&units=" + UNITS + "&appid=" + API_KEY;
            String response = connection.query(queryString);

            if(response != null){
                printWeatherData(response);
            } else {
                System.out.println("No Server Response Received");
            }
        }
    }
}
