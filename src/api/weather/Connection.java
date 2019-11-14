package api.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connection {

    private final String URI;
    private final String API_KEY;
    private HttpURLConnection connection;
    private BufferedReader reader;
    private StringBuffer responseData;

    public Connection(String URI, String API_KEY){
        this.URI = URI;
        this.API_KEY = API_KEY;
    }

    public HttpURLConnection getConnection(){
        return connection;
    }

    public String query(String queryString){
        try {
            URL url = new URL(URI + queryString + "&appid=" + API_KEY);

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