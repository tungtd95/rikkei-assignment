package vn.edu.hust.set.tung.rikkei_assignment.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tungt on 10/17/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    private static final String DB_CREATE_TABLE_NOTE =
            "CREATE TABLE if not exists " + Util.DB_TABLE_NOTE + " (" +
                    Util.DB_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Util.DB_NOTE_NAME + " TEXT, " +
                    Util.DB_NOTE_CONTENT + " TEXT, " +
                    Util.DB_NOTE_TIME_CREATE + " TEXT, " +
                    Util.DB_NOTE_TIME_REMIND + " TEXT, " +
                    Util.DB_NOTE_IS_ALARM + " INTEGER);";

    private static final String DB_CREATE_TABLE_IMAGE =
            "create table if not exists " + Util.DB_TABLE_IMAGE + " (" +
                    Util.DB_IMAGE_ID + " integer primary key autoincrement not null, " +
                    Util.DB_IMAGE_LINK + " text, " +
                    Util.DB_NOTE_ID + " integer);";


    public DBHelper(Context context) {
        super(context, Util.DB_SCHEMA, null, DB_VERSION);
    }

    /**
     * run imediatly after app started
     *
     * @param sqLiteDatabase
     */

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(DB_CREATE_TABLE_NOTE);
            sqLiteDatabase.execSQL(DB_CREATE_TABLE_IMAGE);
        } catch (SQLiteException e) {
            Log.i("main", e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}