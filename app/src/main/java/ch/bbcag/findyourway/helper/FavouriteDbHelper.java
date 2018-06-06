package ch.bbcag.findyourway.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Die Klasse ist für die Bearbeitung der Datenbank zuständing. (Nur die Struktur und keine Datenmanipulation
 */
public class FavouriteDbHelper extends SQLiteOpenHelper{
    // Instanzvariablen
    private static final String DB_NAME = "favourite_location.db";
    private static final int DB_VERSION = 2;
    public static final String TABLE_NAME = "favourite_location";
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_TYP = "type";
    public static final String COLUMN_LOCATIONID = "locationId";
    public static final String COLUMN_NAME = "name";
    private Context context;
    private static final String LOG_TAG = FavouriteDbHelper.class.getSimpleName();
    private static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TYP + " INTEGER NOT NULL, " +
                    COLUMN_LOCATIONID + " INTEGER NOT NULL, "+
                    COLUMN_NAME + " TEXT NOT NULL);";
    private static final String SQL_DROP =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

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
        db.execSQL(SQL_DROP);
        onCreate(db);
    }
}
