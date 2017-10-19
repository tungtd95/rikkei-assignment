package vn.edu.hust.set.tung.rikkei_assignment.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.edu.hust.set.tung.rikkei_assignment.model.Note;

/**
 * Created by tungt on 10/17/17.
 */

public class DBC {

    SQLiteDatabase dbRead;
    SQLiteDatabase dbWrite;
    DBHelper dbHelper;
    private static DBC dbc;

    private DBC(Context context) {
        dbHelper = new DBHelper(context);
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
    }

    public static DBC getInstance(Context context) {
        if (dbc == null) {
            dbc = new DBC(context);
        }
        return dbc;
    }

    public ArrayList<Note> getListNote() {
        ArrayList<Note> list = new ArrayList<>();
        return list;
    }

    public long addNote(Note note) {
        return 0;
    }

    public void removeNote(int userID) {

    }

    public void editNote(Note newNote) {

    }

    public void finish() {
        dbWrite.close();
        dbRead.close();
        dbHelper.close();
    }
}
