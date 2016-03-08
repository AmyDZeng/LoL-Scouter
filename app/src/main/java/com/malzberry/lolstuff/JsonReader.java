package com.malzberry.lolstuff;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonReader {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        /*
        HttpURLConnection con;
        con = (HttpURLConnection) new URL("http://localhost:8080/myapp/service/generate").openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
        */
        //con.setRequestProperty("Accept", "*/*");

        //InputStream is = con.getInputStream();

        InputStream is = new URL(url).openStream();
        try{
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            //return jsonText;
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static String readStringJsonFromUrl(String url) throws IOException, JSONException {
        /*
        HttpURLConnection con;
        con = (HttpURLConnection) new URL("http://localhost:8080/myapp/service/generate").openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
        */
        //con.setRequestProperty("Accept", "*/*");

        //InputStream is = con.getInputStream();

        InputStream is = new URL(url).openStream();
        try{
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return jsonText;
            //JSONObject json = new JSONObject(jsonText);
            //return json;
        } finally {
            is.close();
        }
    }


}
