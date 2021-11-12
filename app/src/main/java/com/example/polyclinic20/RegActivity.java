package com.example.polyclinic20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegActivity extends AppCompatActivity {

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    EditText email_reg, pass_reg, conf_pass_reg, name_reg, sur_reg, patr_reg, pnumber_reg;
    String email, pass, pass_conf, name, sur, patr, pnumber, unique_email, unique_number;
    Cursor numberCursor, emailCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        email_reg = (EditText) findViewById(R.id.edittext_mail_reg);
        pass_reg = (EditText) findViewById(R.id.edittext_pass_reg);
        conf_pass_reg = (EditText) findViewById(R.id.edittext_pass_conf_reg);
        name_reg = (EditText) findViewById(R.id.edittext_name_reg);
        sur_reg = (EditText) findViewById(R.id.edittext_sur_reg);
        patr_reg = (EditText) findViewById(R.id.edittext_patr_reg);
        pnumber_reg = (EditText) findViewById(R.id.edittext_phone_reg);

        sqlHelper = new DatabaseHelper(getApplicationContext());

        sqlHelper.create_db();
    }

    public void reg(View view) {

        db = sqlHelper.open();

        email = email_reg.getText().toString();
        pass = pass_reg.getText().toString();
        pass_conf = conf_pass_reg.getText().toString();
        name = name_reg.getText().toString();
        sur = sur_reg.getText().toString();
        patr = patr_reg.getText().toString();
        pnumber = pnumber_reg.getText().toString();

        if (pass.equals(pass_conf)) {
            if (email.isEmpty() || pass.isEmpty() || name.isEmpty()
                    || sur.isEmpty() || patr.isEmpty() || pnumber.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
            } else {
                if (email.length() < 5 || pass.length() < 5) {
                    Toast.makeText(getApplicationContext(), "Почта и пароль должны быть не меньше 5 символов", Toast.LENGTH_SHORT).show();
                } else {
                    numberCursor = db.rawQuery(" select * from " + "'" + DatabaseHelper.TABLE_U + "'" + " where " +
                            DatabaseHelper.COLUMN_PNUMBER_U + " = " + "'" + pnumber + "'", null);

                    int numberCount = numberCursor.getCount();

                    emailCursor = db.rawQuery(" select * from " + "'" + DatabaseHelper.TABLE_U + "'" + " where " +
                            DatabaseHelper.COLUMN_EMAIL_U + " = " + "'" + email + "'", null);

                    int emailCount = emailCursor.getCount();
                    if (emailCount > 0) {
                        Toast.makeText(getApplicationContext(),
                                "Пользователь с такой электронной почтой уже существует", Toast.LENGTH_SHORT).show();
                    } else {
                        if (numberCount > 0) {
                            Toast.makeText(getApplicationContext(),
                                    "Пользователь с таким номером телефона уже существует", Toast.LENGTH_SHORT).show();
                        } else {
                            db.execSQL(" insert into " + DatabaseHelper.TABLE_U + " ( " + DatabaseHelper.COLUMN_EMAIL_U
                                    + " , " + DatabaseHelper.COLUMN_PASS_U + " , " + DatabaseHelper.COLUMN_NAME_U
                                    + " , " + DatabaseHelper.COLUMN_SURNAME_U + " , " + DatabaseHelper.COLUMN_PATRONYMIC_U
                                    + " , " + DatabaseHelper.COLUMN_PNUMBER_U + ")" + " values " + " ( " + "'" + email
                                    + "'" + " , " + "'" + pass + "'" + " , " + "'" + name + "'" + " , " + "'"
                                    + sur + "'" + " , " + "'" + patr + "'" + " , " + "'" + pnumber + "'" + " ) ");
                            Toast.makeText(getApplicationContext(), "Вы успешно зарегестрировались", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(this, LogActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Пароли не сопадают", Toast.LENGTH_SHORT).show();
        }
    }
}