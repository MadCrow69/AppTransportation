package Control;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Model.Distance;
import Model.Duration;
import Model.Route;
import Model.RouteJson;
import cz.msebera.android.httpclient.Header;

public class ImportarJson {

    //RouteJson route = new RouteJson();
    //private  List<Route> routeDirections = new ArrayList<Route>();
    //private  List<RouteJson> routeMatrix = new ArrayList<RouteJson>();
    private  Route routeDirections;
    private RouteJson routeMatrix;

    public RouteJson ImportarMatrix(String url) {
        //AsyncHttpClient httpClient = new AsyncHttpClient();   // Metodo Asyncrono
        SyncHttpClient httpClient = new SyncHttpClient();       // Metodo Syncrono
        httpClient.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                Log.d("MEUAPP" + "Url Baixada", response.toString());
                try {
                    parseJSonMatrix(response);

                } catch (JSONException e) {
                    Log.d("MEUAPP" + "Deu M o JSON", response.toString());
                    e.printStackTrace();
                }
            }
        });
        return routeMatrix;
    }

    public Route ImportarDirection(String url) {
        //AsyncHttpClient httpClient = new AsyncHttpClient();   // Metodo Asyncrono
        SyncHttpClient httpClient = new SyncHttpClient();       // Metodo Syncrono
        httpClient.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                Log.d("MEUAPP" + "Url Baixada", response.toString());
                try {
                    parseJSonDirection(response);
                } catch (JSONException e) {
                    Log.d("MEUAPP" + "Deu M o JSON", response.toString());
                    e.printStackTrace();
                }
            }
        });
        return routeDirections;
    }



    private void parseJSonMatrix(String data) throws JSONException {
    //private void parseJSonMatrix(JSONObject  jsonDado) throws JSONException {
        if (data == null) {
            Log.d("MEUAPP" + "Json Matrix Null", toString());
            return;
        }

        JSONObject jsonDado = new JSONObject(data);
        RouteJson route = new RouteJson();

        JSONArray jsonDetino = jsonDado.getJSONArray("destination_addresses");
        route.end_location = jsonDetino.getString(0);   // Pega só a primeira posição

        JSONArray jsonOrigem = jsonDado.getJSONArray("origin_addresses");
        route.start_location = jsonOrigem.getString(0); // Pega só a primeira posição

        JSONArray jsonRows = jsonDado.getJSONArray("rows");
        JSONObject jsonElemen = jsonRows.getJSONObject(0);   // Pegar somente primeira posição (se for necessario fazer com mais end. realizar laço

        JSONArray dados = jsonElemen.getJSONArray("elements");
        jsonDado = dados.getJSONObject(0);   // Pegar somente primeira posição (se for necessario fazer com mais end. realizar laço

        JSONObject jsonDistancia = jsonDado.getJSONObject("distance");
        Distance tempDistance = new Distance(jsonDistancia.getString("text"), jsonDistancia.getInt("value"));
        route.distance = tempDistance.value;

        JSONObject jsonDuracao = jsonDado.getJSONObject("duration");
        Duration tempduration = new Duration(jsonDuracao.getString("text"), jsonDuracao.getInt("value"));
        route.duration = tempduration.value;

        //routeMatrix.add(route);
        routeMatrix = route;

    }

    private void parseJSonDirection(String data) throws JSONException {
        if (data == null) {
            Log.d("MEUAPP" + "JsonDirect Null", toString());
            return;
        }

        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");
        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
            Route route = new Route();

            JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
            JSONObject jsonLeg = jsonLegs.getJSONObject(0);
            JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
            JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
            JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
            JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");

            route.distance = new Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"));
            route.duration = new Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"));
            route.endAddress = jsonLeg.getString("end_address");
            route.startAddress = jsonLeg.getString("start_address");
            route.startLocation = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
            route.endLocation = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));
            route.points = decodePolyLine(overview_polylineJson.getString("points"));

            //routeDirections.add(route);
            routeDirections = route;
        }
        //listener.onDirectionFinderSuccess(routes);
    }

    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }
}
