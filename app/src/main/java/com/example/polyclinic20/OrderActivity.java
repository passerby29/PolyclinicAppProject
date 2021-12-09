package com.example.polyclinic20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class OrderActivity extends AppCompatActivity {

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    ListView dc_list, sc_list;
    Cursor doctorCursor, servicesCursor, timeCursor, noteCheckCursor;
    SimpleCursorAdapter doctorAdapter, servicesAdapter;
    String doctor_spec, selected_service, date;
    public TextView textView, choices;
    Button return_btn, next_btn, return_btn2;
    public View.OnClickListener ReturnToDoctorChoice = this::ReturnToDoctorChoice;
    CalendarView calendarView;
    LinearLayout cal;
    TextView time;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour, currentMinute, worktime_from, worktime_to;
    int[] time_from, time_to;

    //for intent
    String worker_name, service_name, user_id, price, user_name;
    String consume_time, consume_date;
    String[] service_names, prices, worker_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        user_id = intent.getStringExtra("user_id");

        dc_list = (ListView) findViewById(R.id.workers_list);
        sc_list = (ListView) findViewById(R.id.service_choose_list);
        choices = (TextView) findViewById(R.id.textView_choices);
        return_btn = (Button) findViewById(R.id.button_return);
        return_btn2 = (Button) findViewById(R.id.button_return2);
        next_btn = (Button) findViewById(R.id.button_next);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        cal = (LinearLayout) findViewById(R.id.calendar);
        time = (TextView) findViewById(R.id.time_choose);

        sqlHelper = new DatabaseHelper(getApplicationContext());
        sqlHelper.create_db();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = sqlHelper.open();

        doctorCursor = db.rawQuery(" select * from " + DatabaseHelper.TABLE_D, null);

        String[] doctors = new String[]{DatabaseHelper.COLUMN_SPEC_D};

        doctorAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.doctor_list, doctorCursor, doctors,
                new int[]{android.R.id.text1}, 0);
        dc_list.setAdapter(doctorAdapter);

        dc_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView = (TextView) view.findViewById(android.R.id.text1);
                doctor_spec = textView.getText().toString();

                sc_list.setVisibility(View.VISIBLE);
                dc_list.setVisibility(View.GONE);

                doctorCursor = db.rawQuery(" select " + DatabaseHelper.COLUMN_NAME_D +
                        " from " + DatabaseHelper.TABLE_D + " where " +
                        DatabaseHelper.COLUMN_SPEC_D + " = " + "'" + doctor_spec + "'", null);

                worker_names = new String[doctorCursor.getCount()];
                int workerColumn = doctorCursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_D);

                if (doctorCursor.moveToFirst()) {
                    for (int i = 0; i < doctorCursor.getCount(); i++) {
                        worker_names[i] = doctorCursor.getString(workerColumn);
                        doctorCursor.moveToNext();
                    }
                }
                servicesCursor = db.rawQuery(" select * from " + DatabaseHelper.TABLE_S + " where " +
                        DatabaseHelper.COLUMN_SPECIALITY_S + " = " + "'" + doctor_spec + "'", null);

                String[] services = new String[]{DatabaseHelper.COLUMN_NAME_S};

                servicesAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.doctor_list, servicesCursor, services,
                        new int[]{android.R.id.text1}, 0);

                sc_list.setAdapter(servicesAdapter);

                choices.setText("Выберите услугу");

                return_btn.setText("Вернуться");
                return_btn.setOnClickListener(OrderActivity.this::ReturnToDoctorChoice);
            }
        });

        sc_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cal.setVisibility(View.VISIBLE);
                sc_list.setVisibility(View.GONE);
                choices.setText("Выберите дату и время");
                next_btn.setVisibility(View.VISIBLE);
                return_btn2.setVisibility(View.VISIBLE);
                return_btn.setVisibility(View.GONE);
                time.setVisibility(View.VISIBLE);

                textView = (TextView) view.findViewById(android.R.id.text1);
                selected_service = textView.getText().toString();

                timeCursor = db.rawQuery(" select * from " + DatabaseHelper.TABLE_D +
                        " where " + DatabaseHelper.COLUMN_SPEC_D + " = " + "'" + doctor_spec + "'", null);

                time_from = new int[timeCursor.getCount()];
                int timeFromColumn = timeCursor.getColumnIndex(DatabaseHelper.COLUMN_WORKTIME_FROM);

                if (timeCursor.moveToFirst()) {
                    for (int i = 0; i < timeCursor.getCount(); i++) {
                        time_from[i] = timeCursor.getInt(timeFromColumn);
                        timeCursor.moveToNext();
                    }
                }

                time_to = new int[timeCursor.getCount()];
                int timeToColumn = timeCursor.getColumnIndex(DatabaseHelper.COLUMN_WORKTIME_TO);

                if (timeCursor.moveToFirst()) {
                    for (int i = 0; i < timeCursor.getCount(); i++) {
                        time_to[i] = timeCursor.getInt(timeToColumn);
                        timeCursor.moveToNext();
                    }
                }

                servicesCursor = db.rawQuery(" select " + DatabaseHelper.COLUMN_NAME_S +
                        " from " + DatabaseHelper.TABLE_S + " where " +
                        DatabaseHelper.COLUMN_NAME_S + " = " + "'" + selected_service + "'", null);

                service_names = new String[servicesCursor.getCount()];
                int serviceColumn = servicesCursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_S);

                if (servicesCursor.moveToFirst()) {
                    for (int i = 0; i < servicesCursor.getCount(); i++) {
                        service_names[i] = servicesCursor.getString(serviceColumn);
                        servicesCursor.moveToNext();
                    }
                }

                servicesCursor = db.rawQuery(" select " + DatabaseHelper.COLUMN_PRICE_S +
                        " from " + DatabaseHelper.TABLE_S + " where " +
                        DatabaseHelper.COLUMN_NAME_S + " = " + "'" + selected_service + "'", null);

                prices = new String[servicesCursor.getCount()];
                int priceColumn = servicesCursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE_S);

                if (servicesCursor.moveToFirst()) {
                    for (int i = 0; i < servicesCursor.getCount(); i++) {
                        prices[i] = servicesCursor.getString(priceColumn);
                        servicesCursor.moveToNext();
                    }
                }

                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendar = Calendar.getInstance();
                        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                        currentMinute = calendar.get(Calendar.MINUTE);

                        timePickerDialog = new TimePickerDialog(OrderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                if (time_from[0] < hourOfDay && time_to[0] > hourOfDay) {
                                    time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Выберите правильное время записи", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, currentHour, currentMinute, true);
                        timePickerDialog.show();
                    }
                });

                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                        date = dayOfMonth + "/" + (month + 1) + "/" + year;
                    }
                });
            }
        });
    }

    public void ReturnToMenu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("user_name", user_name);
        startActivity(intent);
    }

    public void ReturnToDoctorChoice(View view) {
        dc_list.setVisibility(View.VISIBLE);
        sc_list.setVisibility(View.GONE);
        doctorCursor = db.rawQuery(" select * from " + DatabaseHelper.TABLE_D, null);

        String[] doctors = new String[]{DatabaseHelper.COLUMN_SPEC_D};

        doctorAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.doctor_list, doctorCursor, doctors,
                new int[]{android.R.id.text1}, 0);
        dc_list.setAdapter(doctorAdapter);

        choices.setText("Выберите врача");

        return_btn.setText("Вернуться в меню");
        return_btn.setOnClickListener(this::ReturnToMenu);
    }

    public void ReturnToServiceChoice(View view) {
        cal.setVisibility(View.GONE);
        next_btn.setVisibility(View.GONE);
        return_btn2.setVisibility(View.GONE);
        return_btn.setVisibility(View.VISIBLE);
        sc_list.setVisibility(View.VISIBLE);
        time.setVisibility(View.GONE);

        servicesCursor = db.rawQuery(" select * from " + DatabaseHelper.TABLE_S + " where " +
                DatabaseHelper.COLUMN_SPECIALITY_S + " = " + "'" + doctor_spec + "'", null);

        String[] services = new String[]{DatabaseHelper.COLUMN_NAME_S};

        servicesAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.doctor_list, servicesCursor, services,
                new int[]{android.R.id.text1}, 0);

        dc_list.setAdapter(servicesAdapter);

        choices.setText("Выберите услугу");

        return_btn.setText("Вернуться");
        return_btn.setOnClickListener(ReturnToDoctorChoice);
    }

    public void CreateOrder(View view) {
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        user_name = intent.getStringExtra("user_name");
        service_name = service_names[0];
        worker_name = worker_names[0];
        price = String.valueOf(prices[0]);
        consume_time = time.getText().toString();
        consume_date = date;

        noteCheckCursor = db.rawQuery(" select * from " + "'" + DatabaseHelper.TABLE_O + "'" + " where " +
                DatabaseHelper.COLUMN_WORKER_NAME_O + " = " + "'" + worker_name + "'" + " and " +
                DatabaseHelper.COLUMN_CONSUME_TIME_O + " = " + "'" + consume_time + "'" + " and " +
                DatabaseHelper.COLUMN_CONSUME_DATE_O + " = " + "'" + consume_date + "'", null);

        int noteCount = noteCheckCursor.getCount();

        if (noteCount > 0) {
            Toast.makeText(getApplicationContext(), "Выберите дргую дату или время", Toast.LENGTH_SHORT).show();
        } else {
            if (time.getText().toString().equals("В ы б р а т ь  в р е м я")) {
                Toast.makeText(getApplicationContext(), "Вы забыли указать время", Toast.LENGTH_SHORT).show();
            } else {
                //save to db
                db.execSQL(" insert into " + "'" + DatabaseHelper.TABLE_O + "'" + " ( " + DatabaseHelper.COLUMN_USER_ID_O +
                        " , " + DatabaseHelper.COLUMN_WORKER_NAME_O + " , " + DatabaseHelper.COLUMN_SERVICE_NAME_O +
                        " , " + DatabaseHelper.COLUMN_PRICE_O + " , " + DatabaseHelper.COLUMN_CONSUME_DATE_O +
                        " , " + DatabaseHelper.COLUMN_CONSUME_TIME_O + " , " + DatabaseHelper.COLUMN_SPECIALITY_O +
                        " ) " + " values " + " ( " + "'" + user_id + "'" + " , " + "'" + worker_name + "'" + " , " + "'" +
                        service_name + "'" + " , " + "'" + price + "'" + " , " + "'" + consume_date + "'"
                        + " , " + "'" + consume_time + "'" + " , " + "'" + doctor_spec + "'" + " ) ");

                Intent order_intent = new Intent(getApplicationContext(), NoteActivity.class);
                order_intent.putExtra("user_id", String.valueOf(user_id));
                order_intent.putExtra("user_name", user_name);
                order_intent.putExtra("worker_name", worker_name);
                order_intent.putExtra("worker_spec", doctor_spec);
                order_intent.putExtra("service_name", String.valueOf(service_name));
                order_intent.putExtra("price", price);
                order_intent.putExtra("consume_time", consume_time);
                order_intent.putExtra("consume_date", consume_date);
                startActivity(order_intent);
            }
        }
    }
}