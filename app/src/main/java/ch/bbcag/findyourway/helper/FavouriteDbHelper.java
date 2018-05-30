package ch.bbcag.findyourway.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavouriteDbHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "favourite_location.db";
    public static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "favourite_location";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TYP = "type";
    private static final String COLUMN_NAME = "name";

    private static final String LOG_TAG = FavouriteDbHelper.class.getSimpleName();
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_TYP + " INTEGER NOT NULL, " +
                    COLUMN_NAME + " TEXT NOT NULL);";

    public FavouriteDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE);
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
