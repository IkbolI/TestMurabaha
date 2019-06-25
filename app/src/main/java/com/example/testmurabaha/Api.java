package com.example.testmurabaha;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

    @GET("/testikbol/hs/client")
    Call<List<syncData>> getSyncData();

}
