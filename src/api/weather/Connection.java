package api.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Connection {

    private HttpURLConnection connection;
    private BufferedReader reader;
    private StringBuffer responseData;


    public HttpURLConnection getConnection(){
        return connection;
    }

    /**
     * Performs a 'GET' request to the specified web service API and returns the response
     * as a string.
     * @param apiURL The URL of the desired API, along with any relevant query parameters.
     * @return Response from the specified API as a string.
     */
    public String query(String apiURL){
        try {
            URL url = new URL(apiURL);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000); // 3 seconds
            connection.setReadTimeout(3000); // 3 seconds

            int httpStatus = connection.getResponseCode();

            if(httpStatus == 200){
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                responseData = new StringBuffer();
                String line;

                while((line = reader.readLine()) != null){
                    responseData.append(line);
                }

                return responseData.toString();
            }

            return null;

        } catch(MalformedURLException e){
            System.out.println("MALFORMED URL ERROR - CONNECTION CLASS - query(): " + e.getMessage());
        } catch(IOException e){
            System.out.println("I/O ERROR - CONNECTION CLASS - query(): " + e.getMessage());
        }
        return null;
    }
}