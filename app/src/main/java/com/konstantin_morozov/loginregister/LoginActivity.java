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
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    EditText username = null ;
    EditText password = null ;
    TextView registerLink = null ;
    Button loginButton = null;
    Button connect = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.buttonLogin);
        registerLink = (TextView) findViewById(R.id.registerLink) ;

        connect = (Button) findViewById(R.id.connect);

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



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = username.getText().toString();
                String p = password.getText().toString();
                if (validate(u) && validate(p)) {
                    login(u,p);
                }
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

    public void login (String username, final String pass){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", pass);

        client.post("http://konstantinmorozov.pe.hu/login.php", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                try {
                    String valid = response.getString("result");
                    String username = response.getString("username");
                    //String userID = response.getString("user_id") ;
                    if (valid.equals("1")){
                        Toast.makeText(LoginActivity.this, "You are logged as: "+ username,Toast.LENGTH_LONG).show();
                    }
                    if (valid.equals("0")){
                        Toast.makeText(LoginActivity.this,"Incorrect username/password entered",Toast.LENGTH_LONG).show();
                        password.setText("");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public boolean validate(String text){
        if(text.isEmpty()){
            Toast.makeText(LoginActivity.this, "Please enter a Username and/or Password", Toast.LENGTH_SHORT).show() ;
            return false ;
        }else if(!text.matches("[a-zA-Z0-9_]*")){
            Toast.makeText(LoginActivity.this, "Letters, numbers and underscore only", Toast.LENGTH_SHORT).show() ;
            return false ;
        }
        //else if(text.length() < 6){
       //     Toast.makeText(LoginActivity.this, "Username/Password must be at least 6 characters", Toast.LENGTH_SHORT).show() ;
        //    return false ;
       // }
        else{
            return true ;
        }
    }
}