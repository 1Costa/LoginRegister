package com.konstantin_morozov.loginregister;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    EditText name = null ;
    EditText username = null;
    EditText password= null ;
    Button buttonRegister = null ;
    String uName = "";
    String uUsername = "";
    String uPass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.name);
        username = (EditText)findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);


        ActionBar toolbar = getSupportActionBar();
        //toolbar.setDisplayShowHomeEnabled(true);
        toolbar.setDisplayHomeAsUpEnabled(true);

        Snackbar.make(findViewById(android.R.id.content), "Registration Activity Started", Snackbar.LENGTH_LONG).show();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uName = name.getText().toString();
                uUsername = username.getText().toString();
                uPass = password.getText().toString();

                if(uName.isEmpty()|| uUsername.isEmpty()|| uPass.isEmpty()){
                    Snackbar.make(findViewById(android.R.id.content), "Complete the fields", Snackbar.LENGTH_LONG).show();
                }else{
                    register(uName, uUsername, uPass);
                }


            }
        });
    }


    public void register (final String user, final String username, String pass){

        System.out.println(user +'_'+ username);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("user", user);
        params.put("username", username);
        params.put("password", pass);

        client.post("http://konstantinmorozov.pe.hu/register.php", params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("onFailure", responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String valid = response.getString("result");
                    //System.out.println(valid);
                    if(valid.equals("0")){
                        Snackbar.make(findViewById(android.R.id.content), "Username already exists", Snackbar.LENGTH_LONG).show();
                        uUsername = "" ;
                        uPass = "" ;

                    }else{
                        Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show() ;

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class) ;
                        startActivity(intent) ;
                        finish() ;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });







    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}

