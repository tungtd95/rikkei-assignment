package vn.edu.hust.set.tung.rikkei_assignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import vn.edu.hust.set.tung.rikkei_assignment.R;
import vn.edu.hust.set.tung.rikkei_assignment.db.DBC;
import vn.edu.hust.set.tung.rikkei_assignment.model.Note;
import vn.edu.hust.set.tung.rikkei_assignment.util.Echo;

/**
 * Created by Administrator on 20/10/2017.
 */

public class NewNoteActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etContent;
    TextView tvNewNoteClock;
    DBC dbc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        tvNewNoteClock = (TextView) findViewById(R.id.tvNewNoteClock);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = df.format(c.getTime());
        tvNewNoteClock.setText(formattedDate);

        dbc = DBC.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_new_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemCamera:
                Echo.echo("camera");
                return true;
            case R.id.itemColor:
                Echo.echo("color");
                return true;
            case R.id.itemSave:
                Echo.echo("save");
                String name = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                if (name.length() > 0 || content.length() > 0) {
                    Note note = new Note(name, content);
                    note.setTime(tvNewNoteClock.getText().toString().trim());
                    dbc.addNote(note);
                }
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }
}
