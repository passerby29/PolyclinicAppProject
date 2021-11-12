package com.example.polyclinic20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NoteActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper sqlHelper;
    Cursor userCursor;

    String worker_name, service_name, price, user_id;
    String consume_time, consume_date, worker_spec, user_name;
    String[] worker_name_mas, service_name_mas, price_mas;

    TextView worker_name_txt, speciality, service_name_txt, prices, consumeDate, consumeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        worker_name_txt = (TextView) findViewById(R.id.textView_DoctorName);
        speciality = (TextView) findViewById(R.id.textView_DoctorSpec);
        service_name_txt = (TextView) findViewById(R.id.textView_Service);
        prices = (TextView) findViewById(R.id.textView_Price);
        consumeDate = (TextView) findViewById(R.id.textView_Date);
        consumeTime = (TextView) findViewById(R.id.textView_Time);

        sqlHelper = new DatabaseHelper(getApplicationContext());
        sqlHelper.create_db();


        db = sqlHelper.open();

        Intent intent = getIntent();
        worker_name = intent.getStringExtra("worker_name");
        service_name = intent.getStringExtra("service_name");
        price = intent.getStringExtra("price");
        worker_spec = intent.getStringExtra("worker_spec");
        consume_date = intent.getStringExtra("consume_date");
        consume_time = intent.getStringExtra("consume_time");
        user_id = intent.getStringExtra("user_id");
        user_name = intent.getStringExtra("user_name");

        //worker_name
        userCursor = db.rawQuery(" select * from " + DatabaseHelper.TABLE_D + " where " +
                DatabaseHelper.COLUMN_NAME_D + " = " + "'" + worker_name + "'", null);

        worker_name_mas = new String[userCursor.getCount()];
        int indexColumn_worker = userCursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_D);

        if (userCursor.moveToFirst()) {
            for (int i = 0; i < userCursor.getCount(); i++) {
                worker_name_mas[i] = userCursor.getString(indexColumn_worker);
                userCursor.moveToNext();
            }
        }

        worker_name_txt.setText(String.valueOf(worker_name_mas[0]));

        //service_spec
        speciality.setText(String.valueOf(worker_spec));

        //service_name
        userCursor = db.rawQuery(" select * from " + DatabaseHelper.TABLE_S + " where " +
                DatabaseHelper.COLUMN_NAME_S + " = " + "'" + service_name + "'", null);

        service_name_mas = new String[userCursor.getCount()];
        int indexColumn_service = userCursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_S);

        if (userCursor.moveToFirst()) {
            for (int i = 0; i < userCursor.getCount(); i++) {
                service_name_mas[i] = userCursor.getString(indexColumn_service);
                userCursor.moveToNext();
            }
        }

        service_name_txt.setText(String.valueOf(service_name_mas[0]));

        //price
        userCursor = db.rawQuery(" select * from " + DatabaseHelper.TABLE_S + " where " +
                DatabaseHelper.COLUMN_NAME_S + " = " + "'" + service_name_mas[0] + "'", null);

        price_mas = new String[userCursor.getCount()];
        int indexColumn_price = userCursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE_S);

        if (userCursor.moveToFirst()) {
            for (int i = 0; i < userCursor.getCount(); i++) {
                price_mas[i] = userCursor.getString(indexColumn_price);
                userCursor.moveToNext();
            }
        }

        prices.setText(String.valueOf(price_mas[0]));

        //consume_date
        consumeDate.setText(consume_date);

        //consume_time
        consumeTime.setText(consume_time);
    }

    public void ReturnToMenu(View view) {
        Intent menu_intent = new Intent(getApplicationContext(), MenuActivity.class);
        menu_intent.putExtra("user_id", user_id);
        menu_intent.putExtra("user_name", user_name);
        startActivity(menu_intent);
    }
}