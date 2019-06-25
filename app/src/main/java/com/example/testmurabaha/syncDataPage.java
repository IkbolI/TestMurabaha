package com.example.testmurabaha;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class syncDataPage extends AppCompatActivity {

   ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data_page);

         listView = (ListView) findViewById(R.id.listView);

        Api client = ServiceGenerator.createService(Api.class, "ikbol", "12345678");
        Call<List<syncData>> call = client.getSyncData();
        call.enqueue(new Callback<List<syncData>>() {

            @Override
            public void onResponse(Call<List<syncData>> call, Response<List<syncData>> response) {
                if (response.isSuccessful()){

                     if (!response.body().equals(null)) {

                         Toast.makeText(syncDataPage.this, "We got it", Toast.LENGTH_SHORT).show();


                         List<syncData> syncDatas = response.body();

                         String[] syncDataNames = new String[syncDatas.size()];

                         for (int z = 0; z < 5; z++) {
                             syncDataNames[z] = syncDatas.get(z).getName();
                         }

                         listView.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, syncDataNames));
                     }
                }
                else {
                    Toast.makeText(syncDataPage.this,"Error from RESPONSE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<syncData>> call, Throwable t) {
                Toast.makeText(syncDataPage.this, "Message from FAILURE", Toast.LENGTH_SHORT).show();
            }
        });
    }
}