package com.example.testmurabaha;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Base64;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ApiAuthentificationClient {
    private String baseUrl;
    private String username;
    private String password;
    private String urlResource;
    private String httpMethod;
    private String urlPath;
    private String lastResponse;
    private String payLoad;
    private HashMap<String, String> parameters;
    private Map<String, List<String>> headerFields;

    public ApiAuthentificationClient (String baseUrl, String username, String password){
        setBaseUrl(baseUrl);
        this.username = username;
        this.password = password;
        this.urlResource = "";
        this.urlPath = "";
        this.httpMethod = "GET";
        parameters = new HashMap<>();
        lastResponse = "";
        payLoad = "";
        headerFields = new HashMap<>();
        System.setProperty("jsse.enableSNIExtension","false");
    }



    public ApiAuthentificationClient setBaseUrl(String baseUrl){
        this.baseUrl = baseUrl;
        if (!baseUrl.substring(baseUrl.length() - 1).equals("/")){
            this.baseUrl += "/";
        }
        return this;
    }

    public ApiAuthentificationClient setUrlResource (String urlResource) {
        this.urlResource = urlResource;
        return this;
    }

    public final ApiAuthentificationClient setUrlPath (String urlPath){
        this.urlPath = urlPath;
        return this;
    }

    public ApiAuthentificationClient setHttpMethod (String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public String getLastResponse (){
        return lastResponse;
    }

    public Map<String, List<String>> getHeaderFields (){
        return headerFields;
    }

    public ApiAuthentificationClient setParameters (HashMap<String,String> parameters){
        this.parameters = parameters;
        return this;
    }

    public ApiAuthentificationClient clearParameters (){
        this.parameters.clear();
        return this;
    }

    public ApiAuthentificationClient setParameters (String key, String value) {
        this.parameters.put(key,value);
        return this;
    }

    public ApiAuthentificationClient removeParameters (String key){
        this.parameters.remove(key);
        return this;
    }

    public ApiAuthentificationClient clearAll (){
        parameters.clear();
        baseUrl = "";
        this.username = "";
        this.password = "";
        this.urlPath = "";
        this.urlResource = "";
        this.httpMethod = "";
        lastResponse = "";
        payLoad = "";
        headerFields.clear();
        return this;
    }

    public JSONObject getLastResponseAsJsonObject (){
        try {
            return new JSONObject(String.valueOf(lastResponse));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray getLastResponseAsJsonArray (){
        try {
            return new JSONArray(String.valueOf(lastResponse));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getPayLoadAsString (){
        StringBuilder stringBuffer = new StringBuilder();
        Iterator it = parameters.entrySet().iterator();
        int count = 0;
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            if (count > 0) {
                stringBuffer.append("&");
            }
            stringBuffer.append(pair.getKey()).append("=").append(pair.getValue());

            it.remove();
            count ++;
        }
        return stringBuffer.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)

    public String execute(){
        String line;
        StringBuilder outputStringBuilder = new StringBuilder();

        try {
            StringBuilder urlString = new StringBuilder(baseUrl + urlResource);

            if (!urlPath.equals("")){
                urlString.append("/" + urlPath);
            }

            if (parameters.size()>0 && httpMethod.equals("GET")){
                payLoad = getPayLoadAsString();
                urlString.append("?" + payLoad);
            }

            URL url = new URL (urlString.toString());

            String encoding = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(httpMethod);
            connection.setRequestProperty("Authorization", "Basic" + encoding);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "text/plain");

            if (httpMethod.equals("POST")||httpMethod.equals("PUT")) {

                payLoad = getPayLoadAsString();

                connection.setDoInput(true);
                connection.setDoOutput(true);

                try {
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
                    writer.write(payLoad);

                    headerFields = connection.getHeaderFields();

                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        outputStringBuilder.append(line);
                    }
                } catch (Exception ex) {
                    connection.disconnect();
                }
            } else {
                    InputStream content = (InputStream) connection.getInputStream();

                    headerFields = connection.getHeaderFields();

                    BufferedReader in =  new BufferedReader(new InputStreamReader(content));

                    while ((line = in.readLine()) != null) {
                        outputStringBuilder.append(line);
                    }
                }

        } catch (Exception e){
            e.printStackTrace();
        }

        if (!outputStringBuilder.toString().equals("")){
            lastResponse = outputStringBuilder.toString();
        }

        return outputStringBuilder.toString();
    }
}
