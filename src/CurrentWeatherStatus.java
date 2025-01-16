import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;

public class CurrentWeatherStatus {
    public String[] currentWeather(String location){
        String url = "";
        try {
            url = new BufferedReader(new FileReader("APICode.txt")).readLine();
        } catch (IOException e) {
            System.out.println("API Code Not Found");
        }
        location = location.replaceAll(" ", "_");
        url = url + location;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            String[] failed = new String[1];
            failed[0] = "connection";
            return failed;
        }
        assert response != null;
        if (response.body().contains("1006")) {
            String[] failed = new String[1];
            failed[0] = "failed";
            return failed;
        } else {
            return new responseChecker(response.body()).getResponse();
        }
    }
}