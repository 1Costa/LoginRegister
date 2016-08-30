package com.konstantin_morozov.loginregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    EditText username = null ;
    EditText password= null ;
    TextView registerLink = null ;
    Button login = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button)findViewById(R.id.buttonLogin);
        registerLink = (TextView) findViewById(R.id.registerLink) ;

        Button connect = (Button) findViewById(R.id.connect);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncHttpClient client = new AsyncHttpClient();


                client.get("http://konstantinmorozov.pe.hu/connect.php", new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                                Toast.makeText(getApplicationContext(), "jsonobject", Toast.LENGTH_SHORT).show();

                                //super.onSuccess(statusCode, headers, response);
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                Toast.makeText(getApplicationContext(), "jsonarray", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                                Toast.makeText(getApplicationContext(), "somethings wrong", Toast.LENGTH_SHORT).show();

                                //super.onFailure(statusCode, headers, throwable, errorResponse);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                //Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
                                Log.d("onFailure", responseString);

                            }
                        }
                );
            }
        });



        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startregistration = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(startregistration);

            }
        });


    }
}