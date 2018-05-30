package ch.bbcag.findyourway.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import ch.bbcag.findyourway.helper.FavouriteDbHelper;
import ch.bbcag.findyourway.model.Location;

public class FavouriteDataSource {
    private static final String LOG_TAG = FavouriteDataSource.class.getSimpleName();
    private String[] columns = {
      FavouriteDbHelper.COLUMN_ID,
      FavouriteDbHelper.COLUMN_TYP,
      FavouriteDbHelper.COLUMN_NAME
    };

    private SQLiteDatabase database;
    private FavouriteDbHelper dbHelper;

    public FavouriteDataSource(Context context) {
        dbHelper = new FavouriteDbHelper(context);
    }

    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void createFavouriteLocation(int id, int type, String name){
        ContentValues values = new ContentValues();
        values.put(FavouriteDbHelper.COLUMN_ID, id);
        values.put(FavouriteDbHelper.COLUMN_TYP, type);
        values.put(FavouriteDbHelper.COLUMN_NAME, name);

        long insertId = database.insert(FavouriteDbHelper.TABLE_NAME, null, values);
    }

    private Location cursorToFavouriteLocation(Cursor cursor) {
        int _id = cursor.getColumnIndex(FavouriteDbHelper.COLUMN_ID);
        int _type = cursor.getColumnIndex(FavouriteDbHelper.COLUMN_TYP);
        int _name = cursor.getColumnIndex(FavouriteDbHelper.COLUMN_NAME);

        int id = cursor.getInt(_id);
        int type = cursor.getInt(_type);
        String name = cursor.getString(_name);

        Location location = new Location(id, type, name);
        return  location;
    }

    public List<Location> getAllFavouriteLocations() {
        List<Location> locationList = new ArrayList<>();

        Cursor cursor = database.query(FavouriteDbHelper.TABLE_NAME, columns, null, null, null,null, null);
        cursor.moveToFirst();
        Location location;

        while(!cursor.isAfterLast()){
            location = cursorToFavouriteLocation(cursor);
            locationList.add(location);
            cursor.moveToNext();
        }

        cursor.close();
        return locationList;
    }

}
