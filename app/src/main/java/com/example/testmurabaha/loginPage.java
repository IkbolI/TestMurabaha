package com.example.testmurabaha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginPage extends AppCompatActivity {

    private Button button_login_login;
    private EditText editText_login_username;
    private EditText editText_login_password;
    private String username;
    private String password;
    private String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        baseUrl = "http://176.126.167.172:81/testikbol/hs/client";

        editText_login_username = (EditText) findViewById(R.id.editText_login_username);
        editText_login_password = (EditText) findViewById(R.id.editText2_login_password);

        button_login_login = (Button) findViewById(R.id.button_login_login);

        button_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    username = editText_login_username.getText().toString();
                    password = editText_login_password.getText().toString();

                    ApiAuthentificationClient apiAuthentificationClient = new ApiAuthentificationClient(baseUrl, username, password);

                    AsyncTask<Void, Void, String> execute = new ExecuteNetworkOperation(apiAuthentificationClient);
                    execute.execute();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String>{

        private ApiAuthentificationClient apiAuthentificationClient;
        private String isValidCredentials;

        public ExecuteNetworkOperation (ApiAuthentificationClient apiAuthentificationClient) {
            this.apiAuthentificationClient = apiAuthentificationClient;
        }

        @Override
        protected void onPreExecute (){
            super.onPreExecute();
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground (Void... params) {
            try {
                isValidCredentials = apiAuthentificationClient.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute (String result) {
            super.onPostExecute(result);

            findViewById(R.id.loadingPanel).setVisibility(View.GONE);

            if (isValidCredentials.equals("true")){
                goToSecondActivity();
            } else{
                Toast.makeText(getApplicationContext(), "LOGIN FAILED", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void goToSecondActivity(){
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        bundle.putString("baseUrl", baseUrl);

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
