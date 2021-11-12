package com.example.polyclinic20;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH;
    private static String DB_NAME = "polyclinic9.db";
    private static final int SCHEMA = 1;
    static final String TABLE_U = "users";
    static final String TABLE_D = "workers";
    static final String TABLE_S = "services"; 
    static final String TABLE_O = "order";

    //table users
    static final String COLUMN_ID_U = "_id";
    static final String COLUMN_EMAIL_U = "email";
    static final String COLUMN_PASS_U = "password";
    static final String COLUMN_NAME_U = "name";
    static final String COLUMN_SURNAME_U = "surname";
    static final String COLUMN_PATRONYMIC_U = "patronymic";
    static final String COLUMN_PNUMBER_U = "pnumber";

    //table workers
    static final String COLUMN_ID_D = "_id";
    static final String COLUMN_NAME_D = "name";
    static final String COLUMN_PNUMBER_D = "pnumber";
    static final String COLUMN_SPEC_D = "speciality";
    static final String COLUMN_WORKTIME_FROM = "worktime_from";
    static final String COLUMN_WORKTIME_TO = "worktime_to";

    //table services
    static final String COLUMN_ID_S = "_id";
    static final String COLUMN_NAME_S = "name";
    static final String COLUMN_SPECIALITY_S = "speciality";
    static final String COLUMN_PRICE_S = "price";

    //table orders
    static final String COLUMN_ID_O = "id";
    static final String COLUMN_USER_ID_O = "user_id";
    static final String COLUMN_WORKER_NAME_O = "worker_name";
    static final String COLUMN_SERVICE_NAME_O = "service_name";
    static final String COLUMN_PRICE_O = "price";
    static final String COLUMN_CONSUME_TIME_O = "consume_time";
    static final String COLUMN_CONSUME_DATE_O = "consume_date";
    static final String COLUMN_SPECIALITY_O = "speciality";
    private Context myContext;

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext = context;
        DB_PATH = context.getFilesDir().getPath() + DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    void create_db() {
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                myInput = myContext.getAssets().open(DB_NAME);

                String outFileName = DB_PATH;

                myOutput = new FileOutputStream(outFileName);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            }
        } catch (Exception ex) {
            Log.d("DatabaseHelper", ex.getMessage());
        } finally {
            try {
                if (myOutput != null) myOutput.close();
                if (myInput != null) myInput.close();
            } catch (Exception ex) {
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }

    public SQLiteDatabase open() throws SQLException {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
