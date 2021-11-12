package com.example.polyclinic20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogActivity extends AppCompatActivity {

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor, idCursor;
    EditText email_box, pass_box;
    TextView reg_txt;
    String email, pass;

    //for intent
    int[] user_id;
    String[] users;
    int columnIndex_id, columnIndex_name;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_box = (EditText) findViewById(R.id.email_log);
        pass_box = (EditText) findViewById(R.id.pass_log);
        reg_txt = (TextView) findViewById(R.id.textview_reg);

        sqlHelper = new DatabaseHelper(getApplicationContext());

        sqlHelper.create_db();

        reg_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = sqlHelper.open();
    }

    public void Login(View view) {

        email = email_box.getText().toString();
        pass = pass_box.getText().toString();

        if (!email.isEmpty() || !pass.isEmpty()) {
            if (email.length() >= 5 || pass.length() >= 5) {
                idCursor = db.rawQuery(" select * from " + DatabaseHelper.TABLE_U + " where " +
                        DatabaseHelper.COLUMN_EMAIL_U + " = " + "'" + email + "'", null);

                columnIndex_id = idCursor.getColumnIndex("_id");
                user_id = new int[idCursor.getCount()];

                if (idCursor.moveToFirst()) {
                    for (int i = 0; i < idCursor.getCount(); i++) {
                        user_id[i] = idCursor.getInt(columnIndex_id);
                        idCursor.moveToNext();
                    }
                }
                userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_U + " where " +
                        DatabaseHelper.COLUMN_EMAIL_U + " = " + "'" + email + "'" + " and " +
                        DatabaseHelper.COLUMN_PASS_U + " = " + "'" + pass + "'", null);

                int count = userCursor.getCount();

                //user name
                userCursor = db.rawQuery(" select * from " + "'" + DatabaseHelper.TABLE_U + "'" + " where " +
                        DatabaseHelper.COLUMN_EMAIL_U + " = " + "'" + email + "'", null);

                users = new String[userCursor.getCount()];
                columnIndex_name = userCursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_U);

                if (count == 0) {
                    Toast.makeText(getApplicationContext(), "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
                } else {
                    if (userCursor.moveToFirst()) {
                        for (int i = 0; i < userCursor.getCount(); i++) {
                            users[i] = userCursor.getString(columnIndex_name);
                            userCursor.moveToNext();
                        }
                    }
                    user = users[0];
                }

                if (count > 0) {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    intent.putExtra("user_id", String.valueOf(user_id[0]));
                    intent.putExtra("user_name", user);
                    startActivity(intent);
                } else {
                    if (count == 0) {
                        Toast.makeText(getApplicationContext(), "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Почта и пароль должны быть не меньше 5 символов", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
        }
    }
}