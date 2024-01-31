package Control;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CriaUrl {
    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/distancematrix/json?";//distancematrix "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String MATRIX_URL_API = "https://maps.googleapis.com/maps/api/distancematrix/json?";//distancematrix "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String GOOGLE_API_KEY = "AIzaSyDFzvjtMq0RCoN5YTkoXpq0hiGt9z7USKM";//"AIzaSyDFzvjtMq0RCoN5YTkoXpq0hiGt9z7USKM"; // "AIzaSyDIOTatQ4jC5gOdq8C0ftBgBv_Ed-zxIYg" "AIzaSyDnwLF2-WfK8cVZt9OoDYJ9Y8kspXhEHfI";


    public String createUrlDirectionsAPI(String origin, String destination) throws UnsupportedEncodingException {
        String urlOrigin = URLEncoder.encode(origin, "utf-8");
        String urlDestination = URLEncoder.encode(destination, "utf-8");
        return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination +
                "&key=" + GOOGLE_API_KEY;
    }

    public String createUrlMatrixAPI(String origin, String destination) throws UnsupportedEncodingException {
        String urlOrigin = URLEncoder.encode(origin, "utf-8");
        String urlDestination = URLEncoder.encode(destination, "utf-8");

        return MATRIX_URL_API + "origins=" + urlOrigin + "&destinations=" + urlDestination +
                "&mode=driving&language=pt-BR&key=" + GOOGLE_API_KEY;
    }
}
