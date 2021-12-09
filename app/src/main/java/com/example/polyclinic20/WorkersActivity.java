package com.example.polyclinic20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class WorkersActivity extends AppCompatActivity {

    String user_id, user_name;
    ArrayList<Workers> workers = new ArrayList<>();
    ListView workers_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers);

        workers_list = (ListView) findViewById(R.id.workers_list);
        setWorkers();

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        user_id = intent.getStringExtra("user_id");

        Adapter_Workers adapter = new Adapter_Workers(getApplicationContext(), R.layout.workers_layout, workers);
        workers_list.setAdapter(adapter);
    }


    public void setWorkers(){
        workers.add(new Workers("Стоматолог", "Маслова Оноприя Егоровна", R.drawable.dantist));
        workers.add(new Workers("Офтальмолог", "Константинов Автаидил Эльдарович", R.drawable.ophtalmologist));
        workers.add(new Workers("Психолог", "Леонтьева Хурийа Александровна", R.drawable.phsychologist));
        workers.add(new Workers("Педиатр", "Наварская Амели Григорьевна", R.drawable.pediatrician));
        workers.add(new Workers("Хирург", "Куликовская Бригид Владиславовна", R.drawable.surgeon));
        workers.add(new Workers("Травматолог", "Вещая Зарин Викторовна", R.drawable.traumatologist));
        workers.add(new Workers("Отоларинголог", "Сысолятин Зале Закирович", R.drawable.otolaryngologist));
    }
    public void ReturnToMenu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("user_name", user_name);
        startActivity(intent);
    }
}