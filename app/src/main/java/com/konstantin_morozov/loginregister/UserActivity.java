package com.konstantin_morozov.loginregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserActivity extends AppCompatActivity {

    Button buttonLogout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final EditText etUser_id = (EditText)findViewById(R.id.etUser_id);

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String user_id = intent.getStringExtra("user_id");

        etUsername.setText(username);
        etUser_id.setText(user_id);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
