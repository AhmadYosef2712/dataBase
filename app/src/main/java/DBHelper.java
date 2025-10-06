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
    // ?
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_amount = "amount";
    private static final String COLUMN_RATING = "rating";

    private static final String[] allColumns = {COLUMN_ID, COLUMN_NAME, COLUMN_amount};

    private static final String CREATE_TABLE_Client = "CREATE TABLE IF NOT EXISTS " +
            TABLE_RECORD + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " TEXT," +
            COLUMN_amount + " INTEGER );";

    private SQLiteDatabase database; // access to table

    public DBHelper(@Nullable Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }


    // creating the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(CREATE_TABLE_Client);
    }

    // in case of version upgrade -> new schema
    // database version
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);
        onCreate(sqLiteDatabase);
    }


    // get the Client back with the id
    // also possible to return only the id
    public ModelClient insert(ModelClient client)
    {
        database = getWritableDatabase(); // get access to write the database
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, client.getName());
        values.put(COLUMN_amount, client.getamount());
        long id = database.insert(TABLE_RECORD, null, values);
        client.setId(id);
        database.close();
        return client;
    }

    // remove a specific client from the table
    public void deleteClient(ModelClient client)
    {

    }

    public void deleteById(long id )
    {
        database = getWritableDatabase(); // get access to write e data
        database.delete(TABLE_RECORD, COLUMN_ID + " = " + id, null);
        database.close(); // close the database

    }


    // update a specific client
    public void update(ModelClient client)
    {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, client.getId());
        values.put(COLUMN_NAME, client.getName());
        values.put(COLUMN_amount, client.getamount());
        database.update(TABLE_RECORD, values, COLUMN_ID + "=" + client.getId(), null);
        database.close();

    }

    // return all rows in table
    public ArrayList<ModelClient> selectAll()
    {
        database = getReadableDatabase(); // get access to read the database
        ArrayList<ModelClient> Clients = new ArrayList<>();
        String sortOrder = COLUMN_amount + " DESC"; // sorting by amount
        Cursor cursor = database.query(TABLE_RECORD, allColumns, null, null, null, null, sortOrder); // cursor points at a certain row
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = String.valueOf(cursor.getColumnIndex(COLUMN_NAME));
                int amount =cursor.getColumnIndex(COLUMN_amount);
                long id = cursor.getColumnIndex(COLUMN_ID);
                ModelClient client= new ModelClient(name, amount, id);
                Clients.add(client);
            }
        }
        database.close();
        return Clients;
    }

    public ModelClient selectById(long id)
    {
        database = getReadableDatabase();
        ModelClient client = null;
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{ String.valueOf(id) };
        Cursor cursor = database.query(TABLE_RECORD, allColumns, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            String name = String.valueOf(cursor.getColumnIndex(COLUMN_NAME));
            int amount = cursor.getColumnIndex(COLUMN_amount);
            long foundId = cursor.getColumnIndex(COLUMN_ID);
            client = new ModelClient(name, amount, foundId);
        }
        database.close();
        return client;
    }

    //
    // I prefer using this one...
    //
    public ArrayList<ModelClient> genericSelectByClientName(String ClientName, String name)
    {
        String[] vals = { ClientName };
        // if using the rawQuery
        // String query = "SELECT * FROM " + TABLE_RECORD + " WHERE " + COLUMN_NAME + " = ?";
        String column = COLUMN_NAME;
        return select(column,vals,name);
    }


    // INPUT: notice two options rawQuery should look like
    // rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});
    // OUTPUT: arraylist - number of elements accordingly
    public ArrayList<ModelClient> select(String column, String[] values, String name)
    {
        database = getReadableDatabase(); // get access to read the database
        ArrayList<ModelClient> Clients = new ArrayList<>();
        // Two options,
        // since query cannot be created in compile time there is no difference
        //Cursor cursor = database.rawQuery(query, values);
        Cursor cursor= database.query(TABLE_RECORD, allColumns, COLUMN_NAME +" = ? ", values, null, null, null); // cursor points at a certain row
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int amount = cursor.getColumnIndex(COLUMN_amount);
                long id = cursor.getColumnIndex(COLUMN_ID);
                ModelClient client= new ModelClient(name, amount, id);
                Clients.add(client);
            }// end while
        } // end if
        database.close();
        return Clients;
    }

}