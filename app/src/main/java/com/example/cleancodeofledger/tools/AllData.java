package com.example.cleancodeofledger.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.cleancodeofledger.tools.SQL.DBHelper;

import java.util.Objects;

public class AllData {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static SQLiteDatabase m_database;
    public static DBHelper dbHelper;
    public final static int DBVersion = 33;

    public static void init(Context AppContext) {
        context = AppContext;
    }

    public static void initSQL(String userId) {
        if (dbHelper != null && Objects.equals(dbHelper.UserId, userId)) {
            StringUtils.HaoLog("不重複建立SQL");
            return;
        }
        final String dbName = userId.replace("@", "_").replace(":", "_").replace(".", "_");
        if (m_database != null){
            m_database.close();
        }
        dbHelper = new DBHelper(context, dbName, DBVersion);
        m_database = dbHelper.getWritableDatabase();
        dbHelper.setDB(m_database);
    }

}
