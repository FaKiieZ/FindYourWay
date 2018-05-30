package ch.bbcag.findyourway.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ch.bbcag.findyourway.helper.FavouriteDbHelper;

public class FavouriteDataSource {
    private static final String LOG_TAG = FavouriteDataSource.class.getSimpleName();

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
}
