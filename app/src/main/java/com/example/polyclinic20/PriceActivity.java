package com.example.polyclinic20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class PriceActivity extends AppCompatActivity {

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    ListView pricelist;
    Cursor userCursor;
    SimpleCursorAdapter adapter;
    String user_name, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        pricelist = (ListView) findViewById(R.id.price_list);

        sqlHelper = new DatabaseHelper(getApplicationContext());

        sqlHelper.create_db();

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        user_id = intent.getStringExtra("user_id");
    }

    @Override
    protected void onResume() {
        super.onResume();

        db = sqlHelper.open();

        userCursor = db.rawQuery(" select * from " + DatabaseHelper.TABLE_S, null);

        String[] services = new String[]{DatabaseHelper.COLUMN_NAME_S, DatabaseHelper.COLUMN_PRICE_S};

        adapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.two_line_list_item, userCursor, services,
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
        pricelist.setAdapter(adapter);
    }

    public void Return(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("user_name", user_name);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
    }
}