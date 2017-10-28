package vn.edu.hust.set.tung.rikkei_assignment.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.edu.hust.set.tung.rikkei_assignment.model.Note;
import vn.edu.hust.set.tung.rikkei_assignment.util.Echo;

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
        Cursor cursor = dbRead.rawQuery("select * from " + Util.DB_TABLE_NOTE, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(Util.DB_NOTE_NAME));
            String content = cursor.getString(cursor.getColumnIndex(Util.DB_NOTE_CONTENT));
            String time = cursor.getString(cursor.getColumnIndex(Util.DB_NOTE_TIME_CREATE));
            int color = cursor.getInt(cursor.getColumnIndex(Util.DB_NOTE_COLOR));
            int id = cursor.getInt(cursor.getColumnIndex(Util.DB_NOTE_ID));

            Note note = new Note(name, content);
            note.setTime(time);
            note.setColor(color);
            note.setId(id);

            list.add(note);
        }
        return list;
    }

    public long addNote(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.DB_NOTE_NAME, note.getName());
        contentValues.put(Util.DB_NOTE_CONTENT, note.getContent());
        contentValues.put(Util.DB_NOTE_TIME_CREATE, note.getTime());
        contentValues.put(Util.DB_NOTE_COLOR, note.getColor());
        return dbWrite.insert(Util.DB_TABLE_NOTE, null, contentValues);
    }

    public void removeNote(Note note) {
        dbWrite.execSQL("delete from " + Util.DB_TABLE_NOTE + " where " +
                Util.DB_NOTE_ID + " = " + note.getId());
    }

    public void editNote(Note newNote) {
        String query = "update " + Util.DB_TABLE_NOTE + " set " +
                Util.DB_NOTE_NAME + " = '" + newNote.getName() + "', " +
                Util.DB_NOTE_TIME_CREATE + " = '" + newNote.getTime() + "', " +
                Util.DB_NOTE_CONTENT + " = '" + newNote.getContent() + "', " +
                Util.DB_NOTE_COLOR + " = '" + newNote.getColor() + "' where " +
                Util.DB_NOTE_ID + " = " + newNote.getId() + ";";
        dbWrite.execSQL(query);
    }

    public void finish() {
        dbWrite.close();
        dbRead.close();
        dbHelper.close();
    }
}
