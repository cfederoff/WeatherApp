import java.net.URI;
import java.util.Scanner;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;

public class CurrentWeatherStatus {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String url = "http://api.weatherapi.com/v1/current" +
                ".json?key=569af61ab3fa46ef98a11428240901&q=";
        System.out.println("Where do you want to see the current weather from?");
        String location = scanner.nextLine();
        location = location.replaceAll(" ", "_");
        url = url + location;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assert response != null;
        if (response.body().contains("1006")) {
            System.out.println("Invalid Location Entered");
        } else {
            String[] currentWeather = new responseChecker(response.body()).getResponse();
            System.out.println("In " + currentWeather[0] + ", the temperature in F is " + currentWeather[1] + " and " +
                    "the " +
                    "condition outside is " + currentWeather[2]);
        }
    }
}