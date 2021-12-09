package com.example.polyclinic20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AppointmentActivity extends AppCompatActivity {

    String user_name, user_id;
    ListView appointments;
    ArrayList<Timetable> appointment = new ArrayList<>();
    Adapter_Timetable adapter;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor appointmentCursor;
    int count;

    String[] doctor_names, prices, times, service_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        user_id = intent.getStringExtra("user_id");

        appointments = (ListView) findViewById(R.id.listView_appointment);

        sqlHelper = new DatabaseHelper(this);
        sqlHelper.create_db();

        db = sqlHelper.open();

        appointmentCursor = db.rawQuery(" select * from " + "'" + DatabaseHelper.TABLE_O + "'"
                + " where " + DatabaseHelper.COLUMN_USER_ID_O + " = " + "'" + user_id + "'", null);

        count = appointmentCursor.getCount();

        int columnDoctorName = appointmentCursor.getColumnIndex(DatabaseHelper.COLUMN_WORKER_NAME_O);
        doctor_names = new String[count];

        if (appointmentCursor.moveToFirst()) {
            for (int i = 0; i < count; i++) {
                doctor_names[i] = appointmentCursor.getString(columnDoctorName);
                appointmentCursor.moveToNext();
            }
        }

        int columnServiceName = appointmentCursor.getColumnIndex(DatabaseHelper.COLUMN_SERVICE_NAME_O);
        service_names = new String[count];

        if (appointmentCursor.moveToFirst()) {
            for (int i = 0; i < count; i++) {
                service_names[i] = appointmentCursor.getString(columnServiceName);
                appointmentCursor.moveToNext();
            }
        }

        int columnDate = appointmentCursor.getColumnIndex(DatabaseHelper.COLUMN_CONSUME_DATE_O);
        prices = new String[count];

        if (appointmentCursor.moveToFirst()) {
            for (int i = 0; i < count; i++) {
                prices[i] = appointmentCursor.getString(columnDate);
                appointmentCursor.moveToNext();
            }
        }

        int columnTime = appointmentCursor.getColumnIndex(DatabaseHelper.COLUMN_CONSUME_TIME_O);
        times = new String[count];

        if (appointmentCursor.moveToFirst()) {
            for (int i = 0; i < count; i++) {
                times[i] = appointmentCursor.getString(columnTime);
                appointmentCursor.moveToNext();
            }
        }


        for (int i = 0; i < count; i++) {
            appointment.add(new Timetable(service_names[i], doctor_names[i], prices[i], times[i]));
        }

        adapter = new Adapter_Timetable
                (this, R.layout.appointment_list, appointment);
        appointments.setAdapter(adapter);
    }

    public void ReturnToMenu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("user_name", user_name);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
    }
}