package com.example.polyclinic20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    String user_id, user_name, user;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        
        txt = (TextView) findViewById(R.id.textView4); 

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        user_name = intent.getStringExtra("user_name");
        user = String.format("Добро пожаловать, %s", user_name);

        txt.setText(user);
    }
    public void timetable(View view){
        Intent intent = new Intent(getApplicationContext(), TimetableActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("user_name", user_name);
        startActivity(intent);
    }
    public void order(View view){
        Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("user_name", user_name);
        startActivity(intent);
    }
    public void pricelist(View view){
        Intent intent = new Intent(getApplicationContext(), PriceActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("user_name", user_name);
        startActivity(intent);
    }
    public void appointment(View view){
        Intent intent = new Intent(getApplicationContext(), AppointmentActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("user_name", user_name);
        startActivity(intent);
    }
    public void workers(View view){
        Intent intent = new Intent(getApplicationContext(), WorkersActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("user_name", user_name);
        startActivity(intent);
    }
}