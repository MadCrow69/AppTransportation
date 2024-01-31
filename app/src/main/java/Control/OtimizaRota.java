package Control;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Model.Distance;
import Model.Duration;
import Model.Matrix;
import Model.Route;
import Model.RouteJson;


//public class OtimizaRota {
public class OtimizaRota extends AsyncTask<Integer, Integer, Void> {

    //private String dadosJson;
    private int tam;
    private Matrix[][] graph;
    private ProgressDialog progressDialog;      // Implementar

    private ArrayList<String> destinos = new ArrayList();       // Lista com endereços de teste
    private ArrayList<String> urlList = new ArrayList();       // Lista com endereços de teste

    private ArrayList<Route> routesDirection = new ArrayList(); // Lista do Json
    private ArrayList<RouteJson> routesMatrix = new ArrayList();


    // Substituir para uma que puxe os endereços selecionados pelo usuario
    void carregaDestinos () {        // Funcao de testes para ter rotas a serem testadas
        destinos.add("Rua Jose de Oliveira Franco, 1467");
        destinos.add("Rua inaja, 366");
        destinos.add("Rua Willian Walter Atkinson, 268");
        destinos.add("Rua Konrad Adenauer, 442");
        destinos.add("R. Maj. Heitor Guimarães, 174");
    }

    public OtimizaRota(ArrayList<String> destinos) {
        this.destinos = destinos;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        Log.d("MEUAPP: " + "Inciado Testes", toString());

        //carregaDestinos();    // usar caso não venha da main
        tam = destinos.size();
        for (String origem: destinos){  // Cria as URL com todas as possiveis combiações de rotas (precisa remover o igual para igual
            for (String destino:destinos){
                if(origem != destino) {
                    try {
                        urlList.add(new CriaUrl().createUrlMatrixAPI(origem, destino));
                        //urlList.add(createUrlMatrixAPI(origem, destino));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    urlList.add("IGUAL");
                }
            }
        }
        trataDadosMatrix();
        return null;
    }


    void trataDadosMatrix(){
        Log.d("MEUAPP: " + "Dado Matrix", toString());
        for (String temp: urlList){ // Para cada URL pega os dados referente a rota
            if(temp != "IGUAL"){
                new DownloadMatrix().execute(temp);
                routesMatrix.add(new ImportarJson().ImportarMatrix(temp)); // Adiciona os dadados importados do Json
            }
        }

        Log.d("MEUAPP: " + "RouteTAM: "+routesMatrix.size(), toString());

        int count = 0;      // Usado para saber a posição da lista
        graph = new Matrix[tam][tam];   // Carrega a matrix de custos com base nos dados coletados da API
        for (int x = 0; x < tam; x++) {
            for (int y = 0; y < tam; y++) {
                if(count < routesMatrix.size()) {
                    graph[x][y] = new Matrix();     // Instancia o elemento
                    if (x != y) {       // Carrega com os dados da APIgoogle
                        graph[x][y].marcador = 'S';
                        graph[x][y].distancia = routesMatrix.get(count).distance;
                        graph[x][y].duracao = routesMatrix.get(count).duration;
                        graph[x][y].destino = routesMatrix.get(count).end_location;
                        graph[x][y].origem = routesMatrix.get(count).start_location;

                        graph[x][y].url = urlList.get(count);
                    } else {            // Caso origem destino sejam iguais, invalida custos.
                        graph[x][y].marcador = 'N';
                        graph[x][y].duracao = 2147483647;
                        graph[x][y].distancia = 2147483647;
                        graph[x][y].destino = "IGUAL";
                        graph[x][y].origem = "IGUAL";
                        graph[x][y].url = urlList.get(count);
                    }
                }
                count++;
            }
        }
        validaCusto();
        // Criar a alocação dos dados no Banco de Dados
    }




    private class DownloadMatrix extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            try {
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                //dadosJson = buffer.toString();
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String res) {
            try {
                parseJSonMatrix(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }



    public int validaCusto(){
        Log.d("MEUAPP: " + "Incia Custos", toString());
        ArrayList<Integer> restricoes = new ArrayList<>();
        ArrayList<Integer> custos = new ArrayList<>();

        Integer total = 0;

        for(int colX = 0; colX < tam; colX ++){		// Varredura por Coluna
            int minTemp = 2147483647;				// Inicia com maior custo possivel
            int posLin = 2147483647;				// Inicia com maior posição possivel

            for(int linY = 0; linY < tam; linY ++){	// Varredura por linha
                boolean pula = false;				// Variavel de controle p validação da linha
                if(colX > 0){				        // Verifica se já está na segunda coluna
                    for(int temp : restricoes){		// Valida se essa linha tem restrição
                        if(temp == linY ){			// Caso tenha
                            pula = true;			// Não faz o teste de valor minimo
                        }
                    }
                    if (pula == false){						        // Caso não,
                        if(graph[linY][colX].distancia < minTemp){	// valida o menor custo da mesma
                            //System.out.println("Menor valor: " + Matrix[colY][linX] + "Linha: " + colY);
                            posLin = linY;
                            minTemp = graph[linY][colX].distancia;	// E atualiza o minimo da coluna
                            graph[linY][colX].marcador = 'V';
                        }
                    }
                }else{						// Caso seja a primeira coluna
                    if(graph[linY][colX].distancia < minTemp){	// Faz o teste de menor em todo da coluna
                        //System.out.println("Menor valor: " + Matrix[colY][linX] + "Linha: " + colY);
                        posLin = linY;
                        minTemp = graph[linY][colX].distancia;
                        graph[linY][colX].marcador = 'V';
                    }
                }
            }
            //System.out.println("Final linha >>>Posicao: " + posLin + "\tCusto; " + minTemp);
            restricoes.add(posLin);		// Adiciona a linha encontrada na lista de restrições
            custos.add(minTemp);		// Adiciona o custo na lista de custos
            total = total + minTemp;	// Faz a contagem do custo total
            //Collections.sort(posLinha);
        }
        //Collections.sort(restricoes);
        //System.out.println("Posicoes: " +restricoes);

        //System.out.println("TOTAL; " + total);
        Log.d("MEUAPP: " + "Total " + total, toString());

        // Inicio do bloco que irá criar as coordenadas das rotas d
        ArrayList<String> urlRoute = new ArrayList();
        for(int colX = 0; colX < tam; colX ++) {        // Varredura por Coluna
            for (int linY = 0; linY < tam; linY++) {    // Varredura por linha
                if (graph[linY][colX].marcador == 'V') {
                    try {
                        urlRoute.add(new CriaUrl().createUrlDirectionsAPI(graph[linY][colX].origem, graph[linY][colX].destino)); //graph[linY][colX].distancia
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        for (String temp: urlRoute){ // Para cada URL faz a coleta dos dados de rota
            if(temp != "IGUAL"){

                new DownloadDirection().execute(temp);
                routesDirection.add(new ImportarJson().ImportarDirection(temp)); // Adiciona os dadados importados do Json
            }
        }


        return total;
    }

    private class DownloadDirection extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            try {
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                //dadosJson = buffer.toString();
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String res) {
            try {
                parseJSonMatrix(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }



    private RouteJson parseJSonMatrix(String data) throws JSONException {
        //private void parseJSonMatrix(JSONObject  jsonDado) throws JSONException {
        if (data == null) {
            Log.d("MEUAPP" + "Json Matrix Null", toString());
            return null;
        }

        //RouteJson routeMatrix;

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
        return route;
    }

    private Route parseJSonDirection(String data) throws JSONException {
        if (data == null) {
            Log.d("MEUAPP" + "JsonDirect Null", toString());
            return null;
        }

        Route route = new Route();
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");
        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);

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

        }
        return route;
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

