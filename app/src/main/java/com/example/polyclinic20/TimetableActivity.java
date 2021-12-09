package com.example.polyclinic20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;

public class TimetableActivity extends AppCompatActivity {
    ArrayList<Timetable> timetable = new ArrayList<>();
    ListView timetable_list;
    String user_name, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        timetable_list = (ListView) findViewById(R.id.price_list);
        setTimetable();

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        user_id = intent.getStringExtra("user_id");

        Adapter_Timetable adapter = new Adapter_Timetable(getApplicationContext(), R.layout.timetable_layout, timetable);
        timetable_list.setAdapter(adapter);
    }

    public void setTimetable() {
        timetable.add(new Timetable("Стоматолог", "Маслова Оноприя Егоровна", "9:00", "17:00"));
        timetable.add(new Timetable("Офтальмолог", "Константинов Автаидил Эльдарович", "10:00", "14:00"));
        timetable.add(new Timetable("Психолог", "Леонтьева Хурийа Александровна", "9:00", "20:00"));
        timetable.add(new Timetable("Педиатр", "Наварская Амели Григорьевна", "8:00", "17:00"));
        timetable.add(new Timetable("Хирург", "Куликовская Бригид Владиславовна", "10:00", "16:00"));
        timetable.add(new Timetable("Травматолог", "Вещая Зарин Викторовна", "9:00", "17:00"));
        timetable.add(new Timetable("Отоларинголог", "Сысолятин Зале Закирович", "10:00", "18:00"));
    }

    public void Return(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("user_name", user_name);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
    }
}
