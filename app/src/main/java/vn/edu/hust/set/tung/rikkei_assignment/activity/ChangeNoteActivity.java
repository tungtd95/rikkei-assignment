package vn.edu.hust.set.tung.rikkei_assignment.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.edu.hust.set.tung.rikkei_assignment.R;
import vn.edu.hust.set.tung.rikkei_assignment.customview.ChooseColorDialog;
import vn.edu.hust.set.tung.rikkei_assignment.customview.OnColorClicked;
import vn.edu.hust.set.tung.rikkei_assignment.db.DBC;
import vn.edu.hust.set.tung.rikkei_assignment.model.Note;

/**
 * Created by Administrator on 20/10/2017.
 */

public class ChangeNoteActivity extends AppCompatActivity implements OnColorClicked {

    EditText etTitle;
    EditText etContent;
    TextView tvNewNoteClock;
    RelativeLayout rlNewNote;
    LinearLayout lnNavigate;
    RelativeLayout rlBack;
    RelativeLayout rlNext;
    RelativeLayout rlDelete;
    RelativeLayout rlShare;

    DBC dbc;
    int indexNote;
    ArrayList<Note> listNote;

    boolean isChange = false;

    int mColor;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_note);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        tvNewNoteClock = (TextView) findViewById(R.id.tvNewNoteClock);
        rlNewNote = (RelativeLayout) findViewById(R.id.rlNewNote);
        lnNavigate = (LinearLayout) findViewById(R.id.lnNavigate);
        rlBack = (RelativeLayout) findViewById(R.id.rlBack);
        rlNext = (RelativeLayout) findViewById(R.id.rlNext);
        rlShare = (RelativeLayout) findViewById(R.id.rlShare);
        rlDelete = (RelativeLayout) findViewById(R.id.rlDelete);

        indexNote = getIntent().getIntExtra(MainActivity.KEY_NOTE, MainActivity.KEY_ADD);
        dbc = DBC.getInstance(this);

        if (indexNote == MainActivity.KEY_ADD) {
            setAddConfig();
        } else {
            setEditConfig();
        }

        rlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbc.removeNote(listNote.get(indexNote));
                gotoMain();
            }
        });
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
                return true;
            case R.id.itemColor:
                ChooseColorDialog colorDialog = new ChooseColorDialog(ChangeNoteActivity.this);
                colorDialog.show();
                return true;
            case R.id.itemSave:
                if (indexNote == MainActivity.KEY_ADD) {
                    addNote();
                } else {
                    editNote();
                }
                gotoMain();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClickedColor(int color) {
        try {
            mColor = color;
            rlNewNote.setBackgroundColor(mColor);
        } catch (Exception e) {
            Log.i("main", e.toString());
        }
    }

    public void gotoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void setEditConfig() {
        listNote = dbc.getListNote();
        mColor = listNote.get(indexNote).getColor();
        etTitle.setText(listNote.get(indexNote).getName());
        etContent.setText(listNote.get(indexNote).getContent());
        tvNewNoteClock.setText(listNote.get(indexNote).getTime());
        rlNewNote.setBackgroundColor(mColor);
        lnNavigate.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAddConfig() {
        mColor = getResources().getColor(R.color.btnWhite, null);
        tvNewNoteClock.setText(getTime());
        rlNewNote.setBackgroundColor(mColor);
        lnNavigate.setVisibility(View.GONE);
    }

    public String getTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return df.format(c.getTime());
    }

    public void addNote() {
        String name = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        Note note = new Note(name, content);
        note.setTime(tvNewNoteClock.getText().toString());
        note.setColor(mColor);
        dbc.addNote(note);
    }

    public void editNote() {
        String name = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        Note oldNote = listNote.get(indexNote);

        if (!oldNote.getName().equals(name)) {
            isChange = true;
        }
        if (!oldNote.getContent().equals(content)) {
            isChange = true;
        }
        if (oldNote.getColor() != mColor) {
            isChange = true;
        }

        Note newNote = new Note(name, content);
        newNote.setColor(mColor);
        newNote.setTime(getTime());
        newNote.setId(oldNote.getId());

        if (isChange) {
            dbc.editNote(newNote);
        }
    }
}
