package com.example.testmurabaha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Button search, message, info, sync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (Button) findViewById(R.id.search);
        message = (Button) findViewById(R.id.message);
        info = (Button) findViewById(R.id.info);
        sync = (Button) findViewById(R.id.sync);

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent move = new Intent(MainActivity.this, syncDataPage.class);
                startActivity(move);
            }
        });


    }
}
