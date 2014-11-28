/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Geocoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Mask
 */
public class GeoCalculator {

    public static Integer[] getDistanciaTempo(String origem, String destino) {
        Integer tem = 0;
        Integer dist = 0;
        origem = RemoverAcentos.remover(origem);
        destino = RemoverAcentos.remover(destino);

        StringBuffer start = new StringBuffer();
        StringBuffer stop = new StringBuffer();
        start.append(origem);
        stop.append(destino);

        for (int i = 0; i < start.length(); i++) {
            if (start.charAt(i) == ' ') {
                start.deleteCharAt(i);
                start.insert(i, "%20");
            }
        }
        for (int i = 0; i < stop.length(); i++) {
            if (stop.charAt(i) == ' ') {
                stop.deleteCharAt(i);
                stop.insert(i, "%20");
            }
        }

        JSONObject json = null;
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + start + "&destinations=" + stop + "&mode=driving&sensor=false";
        System.out.println(url);
        try {
            json = readJsonFromUrl(url);
            json.get("rows");
            JSONArray arr = null;
            arr = json.getJSONArray("rows");
            tem = (Integer) arr.getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getInt("value");
            dist = (Integer) arr.getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("distance").getInt("value");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer[] array = {dist, tem};

        return array;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static String getPolyline(String origem, String destino, String waypoints) {
        
        origem = RemoverAcentos.remover(origem);
        waypoints = RemoverAcentos.remover(waypoints);

        StringBuffer add = new StringBuffer();
        StringBuffer way = new StringBuffer();
        StringBuffer dest = new StringBuffer();
        add.append(origem);
        dest.append(destino);
        way.append(waypoints);

        for (int i = 0; i < add.length(); i++) {
            if (add.charAt(i) == ' ') {
                add.deleteCharAt(i);
                add.insert(i, "%20");
            }
        }
        for (int i = 0; i < way.length(); i++) {
            if (way.charAt(i) == ' ') {
                way.deleteCharAt(i);
                way.insert(i, "%20");
            }
        }
        for (int i = 0; i < dest.length(); i++) {
            if (dest.charAt(i) == ' ') {
                dest.deleteCharAt(i);
                dest.insert(i, "%20");
            }
        }

        JSONObject json = null;
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+ add +"&destination="+dest+"&waypoints="+way+"&mode=driving&sensor=false";
        //String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + start + "&destinations=" + stop + "&mode=driving&sensor=false";
        String poly = "";
        System.out.println(url);
        boolean correto = false;
        while(!correto) {
            try {
                json = readJsonFromUrl(url);
                json.get("routes");
                JSONArray arr = null;
                arr = json.getJSONArray("routes");
                poly = arr.getJSONObject(0).getJSONObject("overview_polyline").getString("points");
                correto = true;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {

            }
        }
        
        return poly;
    }
    
    public static String getPolylineDestino(String inicio, String destino) {
        
        inicio = RemoverAcentos.remover(inicio);
        destino = RemoverAcentos.remover(destino);

        StringBuffer ini = new StringBuffer();
        StringBuffer dest = new StringBuffer();
        ini.append(inicio);
        dest.append(destino);

        for (int i = 0; i < ini.length(); i++) {
            if (ini.charAt(i) == ' ') {
                ini.deleteCharAt(i);
                ini.insert(i, "%20");
            }
        }
        for (int i = 0; i < dest.length(); i++) {
            if (dest.charAt(i) == ' ') {
                dest.deleteCharAt(i);
                dest.insert(i, "%20");
            }
        }

        JSONObject json = null;
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+ ini +"&destination="+dest+"&mode=driving&sensor=false";
        //String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + start + "&destinations=" + stop + "&mode=driving&sensor=false";
        String poly = "";
        System.out.println(url);
        boolean correto = false;
        while(!correto) {
            try {
                json = readJsonFromUrl(url);
                json.get("routes");
                JSONArray arr = null;
                arr = json.getJSONArray("routes");
                poly = arr.getJSONObject(0).getJSONObject("overview_polyline").getString("points");
                correto = true;
            } catch (JSONException e) {

            } catch (IOException e) {
                
            }
        }
        
        return poly;
    }

}
