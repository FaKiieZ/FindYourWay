package ch.bbcag.findyourway.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavouriteDbHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "favourite_location.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "favourite_location";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYP = "type";
    public static final String COLUMN_LOCATIONID = "locationId";

    private static final String LOG_TAG = FavouriteDbHelper.class.getSimpleName();
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TYP + " INTEGER NOT NULL, " +
                    COLUMN_LOCATIONID + " INTEGER NOT NULL);";

    private Context context;

    public FavouriteDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
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

    public void dropDatabase(){
        context.deleteDatabase(DB_NAME);
    }
}
