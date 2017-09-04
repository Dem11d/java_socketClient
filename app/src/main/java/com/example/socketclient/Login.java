package com.example.socketclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button)findViewById(R.id.nextLogin);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        EditText et = (EditText) findViewById(R.id.name);
        String value = et.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name",value);
        startActivity(intent);
    }
}
