package ch.bbcag.findyourway.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import ch.bbcag.findyourway.helper.FavouriteDbHelper;
import ch.bbcag.findyourway.model.Location;

/**
 * Diese Klasse dient zum Arbeiten mit der SQLite Datenbank
 */
public class FavouriteDataSource {

    private static final String LOG_TAG = FavouriteDataSource.class.getSimpleName();
    private String[] columns = {
            FavouriteDbHelper.COLUMN_TYP,
            FavouriteDbHelper.COLUMN_LOCATIONID,
            FavouriteDbHelper.COLUMN_NAME
    };

    private SQLiteDatabase database;
    private FavouriteDbHelper dbHelper;

    public FavouriteDataSource(Context context) {
        dbHelper = new FavouriteDbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Erstellt einen neuen Eintrag in der Datenbank
     * @param type Speichert den Typ der Station [0: Zug, 1: Bus, 2: Schiff]
     * @param locationId ID der Location, um Abfragen über die API zu tätigen
     * @param name Speichert den Displayname für diese Location
     */
    public void createFavouriteLocation(int type, int locationId, String name) {
        ContentValues values = new ContentValues();
        values.put(FavouriteDbHelper.COLUMN_LOCATIONID, locationId);
        values.put(FavouriteDbHelper.COLUMN_TYP, type);
        values.put(FavouriteDbHelper.COLUMN_NAME, name);

        long insertId = database.insert(dbHelper.TABLE_NAME, null, values);
    }

    public void deleteFavouriteLocation(Integer id) {
        database.delete(dbHelper.TABLE_NAME, dbHelper.COLUMN_LOCATIONID + " = ?", new String[] {
                id.toString()
        });
    }

    /**
     * Liest mit Hilfe des Cursor-Objektes die Datenbankabfrage
     * @param cursor
     * @return
     */
    private Location cursorToFavouriteLocation(Cursor cursor) {
        int _locationId = cursor.getColumnIndex(dbHelper.COLUMN_LOCATIONID);
        int _type = cursor.getColumnIndex(dbHelper.COLUMN_TYP);
        int _name = cursor.getColumnIndex(dbHelper.COLUMN_NAME);

        int locationId = cursor.getInt(_locationId);
        int type = cursor.getInt(_type);
        String name = cursor.getString(_name);

        Location location = new Location(locationId, type, name);
        return location;
    }

    /**
     * Lädt alle gespeicherten Locations aus der Datenbank
     * @return Liste mit allen Location-Objekten
     */
    public List < Location > getAllFavouriteLocations() {
        List < Location > locationList = new ArrayList < > ();

        Cursor cursor = database.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null);
        cursor.moveToFirst();
        Location location;

        while (!cursor.isAfterLast()) {
            location = cursorToFavouriteLocation(cursor);
            locationList.add(location);
            cursor.moveToNext();
        }

        cursor.close();
        return locationList;
    }

}