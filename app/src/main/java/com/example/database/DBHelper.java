package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASENAME = "result.db";
    private static final String TABLE_RECORD = "tblresult";
    private static final int DATABASEVERSION = 1;

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AMOUNT = "amount";

    private static final String[] allColumns = {COLUMN_ID, COLUMN_NAME, COLUMN_AMOUNT};

    private static final String CREATE_TABLE_Client = "CREATE TABLE IF NOT EXISTS " +
            TABLE_RECORD + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " TEXT," +
            COLUMN_AMOUNT + " INTEGER );";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_Client);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);
        onCreate(sqLiteDatabase);
    }

    // Insert a new client
    public ModelClient insert(ModelClient client) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, client.getName());
        values.put(COLUMN_AMOUNT, client.getAmount());
        long id = db.insert(TABLE_RECORD, null, values);
        client.setId(id);
        db.close();
        return client;
    }

    // Delete a client by object
    public void deleteClient(ModelClient client) {
        deleteById(client.getId());
    }

    // Delete by ID (safe parameterized)
    public void deleteById(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_RECORD, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Update client (safe parameterized)
    public void update(ModelClient client) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, client.getName());
        values.put(COLUMN_AMOUNT, client.getAmount());
        db.update(TABLE_RECORD, values, COLUMN_ID + " = ?", new String[]{String.valueOf(client.getId())});
        db.close();
    }

    // Select all rows, sorted by amount descending
    public ArrayList<ModelClient> selectAll() {
        ArrayList<ModelClient> clients = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_RECORD, allColumns, null, null, null, null, COLUMN_AMOUNT + " DESC");

            if (cursor != null && cursor.moveToFirst()) {
                int idxName = cursor.getColumnIndexOrThrow(COLUMN_NAME);
                int idxAmount = cursor.getColumnIndexOrThrow(COLUMN_AMOUNT);
                int idxId = cursor.getColumnIndexOrThrow(COLUMN_ID);

                do {
                    String name = cursor.getString(idxName);
                    int amount = cursor.getInt(idxAmount);
                    long id = cursor.getLong(idxId);
                    clients.add(new ModelClient(name, amount, id));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return clients;
    }

    // Select by ID
    public ModelClient selectById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        ModelClient client = null;
        Cursor cursor = null;

        try {
            String selection = COLUMN_ID + " = ?";
            String[] selectionArgs = {String.valueOf(id)};
            cursor = db.query(TABLE_RECORD, allColumns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int idxName = cursor.getColumnIndexOrThrow(COLUMN_NAME);
                int idxAmount = cursor.getColumnIndexOrThrow(COLUMN_AMOUNT);
                int idxId = cursor.getColumnIndexOrThrow(COLUMN_ID);

                String name = cursor.getString(idxName);
                int amount = cursor.getInt(idxAmount);
                long foundId = cursor.getLong(idxId);
                client = new ModelClient(name, amount, foundId);
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return client;
    }

    // Select by client name
    public ArrayList<ModelClient> genericSelectByClientName(String clientName) {
        String[] vals = {clientName};
        return select(COLUMN_NAME, vals);
    }

    // Generic select
    public ArrayList<ModelClient> select(String column, String[] values) {
        ArrayList<ModelClient> clients = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_RECORD, allColumns, column + " = ?", values, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int idxName = cursor.getColumnIndexOrThrow(COLUMN_NAME);
                int idxAmount = cursor.getColumnIndexOrThrow(COLUMN_AMOUNT);
                int idxId = cursor.getColumnIndexOrThrow(COLUMN_ID);

                do {
                    String name = cursor.getString(idxName);
                    int amount = cursor.getInt(idxAmount);
                    long id = cursor.getLong(idxId);
                    clients.add(new ModelClient(name, amount, id));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return clients;
    }
}
