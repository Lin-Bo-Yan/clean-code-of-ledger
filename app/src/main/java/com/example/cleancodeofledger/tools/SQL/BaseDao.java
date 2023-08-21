package com.example.cleancodeofledger.tools.SQL;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.cleancodeofledger.tools.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BaseDao<T> extends TableAccess {
    public String tableName = "data";
    T t;
    Field primaryKey;
    private SQLiteDatabase m_database;
    String primaryKeyString;
    private String sortOrder = null;
    String[] createIndexes = new String[0];

    public void setCreateIndexes(String[] createIndexes) {
        this.createIndexes = createIndexes;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    String CREATE_TABLE() {
        String data = "CREATE TABLE IF NOT EXISTS " + tableName + "(";
        Field[] fields = t.getClass().getFields();
        for (int i = 0; i < fields.length; i++) {
            String type = " text";
            if (fields[i].getType().equals(String.class))
                type = " text";
            if (fields[i].getType().equals(boolean.class) || fields[i].getType().equals(Boolean.class))
                type = " flag";
            if (fields[i].getType().equals(int.class) || fields[i].getType().equals(Integer.class))
                type = " integer";
            if (fields[i].getType().equals(float.class))
                type = " float";
            if (fields[i].getType().equals(long.class) || fields[i].getType().equals(Long.class))
                type = " integer";

            data += fields[i].getName() + type + (fields[i].getName().equals(primaryKey.getName()) ? " primary key" : "") + ((i != fields.length - 1) ? ", " : "");
        }
        data += ");";
        return data;
    }

    public BaseDao(T t, String primaryKey, String tableName, SQLiteDatabase database) {
        this.tableName = tableName;
        this.t = t;
        try {
            this.primaryKeyString = primaryKey;
            this.primaryKey = t.getClass().getDeclaredField(primaryKey);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        m_database = database;

    }

    public void setDB(SQLiteDatabase database) {
        m_database = database;
    }

    public void createTable(SQLiteDatabase db) {

        StringUtils.HaoLog("Create table command : " + CREATE_TABLE());
        db.execSQL(CREATE_TABLE());
    }

    @Override
    public void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }

    @Override
    public void clearTable(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + tableName);
    }

    @Override
    public void createIndex(SQLiteDatabase db) {

    }

    @Override
    public void updateDatabase(SQLiteDatabase db) {
        m_database = db;
    }

    public boolean insert(T data) {
        if (isExistdata(data))
            return update(data);
        ContentValues values = new ContentValues();
        Field[] fields = t.getClass().getFields();
        for (int i = 0; i < fields.length; i++) {

            try {
                if (fields[i].getType().equals(String.class))
                    values.put(fields[i].getName(), (String) fields[i].get(data));
                if (fields[i].getType().equals(long.class) || fields[i].getType().equals(Long.class))
                    values.put(fields[i].getName(), (Long) fields[i].get(data));
                if (fields[i].getType().equals(int.class) || fields[i].getType().equals(Integer.class))
                    values.put(fields[i].getName(), (Integer) fields[i].get(data));
                if (fields[i].getType().equals(boolean.class) || fields[i].getType().equals(Boolean.class))
                    values.put(fields[i].getName(), ((Boolean) fields[i].get(data)) ? 1 : 0);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        StringUtils.HaoLog("insert= "+ values);
        StringUtils.HaoLog(values.toString());
        long insert = m_database.insert(tableName, null, values);
        if (insert == -1) {
            StringUtils.HaoLog("insertdata() but failed to insert data!");
            return false;
        }
        return true;
    }

    public boolean isExistdata(T data) {
        long count = 0;
        try {
            Object key;
            key = data.getClass().getDeclaredField(primaryKeyString).get(data);
            count = DatabaseUtils.queryNumEntries(m_database, tableName, primaryKey.getName() + "= '" + key + "'");
        } catch (Exception e) {
            StringUtils.HaoLog("isExistdata error = " + e.getMessage());
        }
        return count != 0;
    }

    public boolean update(T data) {
        if (!isExistdata(data)) {
            return insertNull(data);
        }
        ContentValues values = new ContentValues();
        Field[] fields = data.getClass().getFields();
        for (int i = 0; i < fields.length; i++) {

            try {
                if (fields[i].getType().equals(String.class))
                    values.put(fields[i].getName(), (String) fields[i].get(data));
                if (fields[i].getType().equals(long.class) || fields[i].getType().equals(Long.class))
                    values.put(fields[i].getName(), (Long) fields[i].get(data));
                if (fields[i].getType().equals(int.class) || fields[i].getType().equals(Integer.class))
                    values.put(fields[i].getName(), (Integer) fields[i].get(data));
                if (fields[i].getType().equals(boolean.class) || fields[i].getType().equals(Boolean.class))
                    values.put(fields[i].getName(), ((Boolean) fields[i].get(data)) ? 1 : 0);
            } catch (IllegalAccessException e) {

                e.printStackTrace();
            }

        }
        long update = 0;
        try {
            Object key;
            key = data.getClass().getDeclaredField(primaryKeyString).get(data);

            update = m_database.update(tableName, values, primaryKey.getName() + "= '" + key + "'", null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            StringUtils.HaoLog(e.toString());
            e.printStackTrace();
        }
        if (update == 0) {
            StringUtils.HaoLog("updatedata() 但有重複的條目!");
            return false;
        }

        return true;
    }

    public boolean insertNull(T data) {
        ContentValues values = new ContentValues();
        Field[] fields = t.getClass().getFields();
        Field[] t_fields = data.getClass().getFields();
        Map<String, Field> t_field_map = new HashMap();

        for (int i = 0; i < t_fields.length; i++) {
            t_field_map.put(t_fields[i].getName(), t_fields[i]);
        }
        for (int i = 0; i < fields.length; i++) {
            Field th = fields[i];
            Object value;
            try {
                if (t_field_map.keySet().contains(fields[i].getName())) {
                    th = t_field_map.get(fields[i].getName());
                    value = th.get(data);
                } else {
                    StringUtils.HaoLog("insertNull= " + th.getName());
                    value = th.get(t);
                }

                if (th.getType().equals(String.class)){
                    values.put(fields[i].getName(), (String) value);
                }
                if (th.getType().equals(long.class) || th.getType().equals(Long.class)){
                    values.put(fields[i].getName(), (Long) value);
                }
                if (th.getType().equals(int.class) || th.getType().equals(Integer.class)){
                    values.put(fields[i].getName(), (Integer) value);
                }
                if (th.getType().equals(boolean.class) || th.getType().equals(Boolean.class)){
                    values.put(fields[i].getName(), ((Boolean) value) ? 1 : 0);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        long insert = m_database.insert(tableName, null, values);
        if (insert == -1) {
            StringUtils.HaoLog("insertdata() but failed to insert data!");
            return false;
        }
        return true;
    }

}
