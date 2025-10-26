import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Task2 {
    public static void main(String[] args) {
        try {
            String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=17.3850&longitude=78.4867&current_weather=true";

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            String json = response.toString();
            String temp = extractValue(json, "\"temperature\":", ",");
            String windspeed = extractValue(json, "\"windspeed\":", ",");
            String winddir = extractValue(json, "\"winddirection\":", ",");
            String time = extractValue(json, "\"time\":\"", "\"}");

            System.out.println("\n CURRENT WEATHER REPORT");
            System.out.println("--------------------------");
            System.out.println("Temperature : " + temp + " °C");
            System.out.println("Wind Speed  : " + windspeed + " km/h");
            System.out.println("Wind Dir.   : " + winddir + "°");
            System.out.println("Time        : " + time);

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String extractValue(String json, String key, String endChar) {
        int start = json.indexOf(key);
        if (start == -1) return "N/A";
        start += key.length();
        int end = json.indexOf(endChar, start);
        if (end == -1) end = json.length();
        return json.substring(start, end).replaceAll("[^0-9.:\\-]", "");
    }
}
