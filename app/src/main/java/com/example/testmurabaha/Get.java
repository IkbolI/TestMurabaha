package com.example.testmurabaha;

import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class Get {
    private final OkHttpClient client = new OkHttpClient();

    public void run (String url, Callback callback){
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .addHeader("ikbol", "12345678")
                .build();

        client.newCall(request).enqueue(callback);
    }
}
