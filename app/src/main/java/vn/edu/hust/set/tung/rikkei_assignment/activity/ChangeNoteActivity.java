package vn.edu.hust.set.tung.rikkei_assignment.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import vn.edu.hust.set.tung.rikkei_assignment.R;
import vn.edu.hust.set.tung.rikkei_assignment.customview.ChooseColorDialog;
import vn.edu.hust.set.tung.rikkei_assignment.customview.ChooseDateDialog;
import vn.edu.hust.set.tung.rikkei_assignment.customview.ChooseHourDialog;
import vn.edu.hust.set.tung.rikkei_assignment.customview.ImageAdapter;
import vn.edu.hust.set.tung.rikkei_assignment.customview.ItemDecorationAlbumColumns;
import vn.edu.hust.set.tung.rikkei_assignment.customview.OnColorClicked;
import vn.edu.hust.set.tung.rikkei_assignment.customview.OnImageRemove;
import vn.edu.hust.set.tung.rikkei_assignment.customview.OnPhotoListener;
import vn.edu.hust.set.tung.rikkei_assignment.customview.OnPickTimeOK;
import vn.edu.hust.set.tung.rikkei_assignment.customview.PhotoDialog;
import vn.edu.hust.set.tung.rikkei_assignment.db.DBC;
import vn.edu.hust.set.tung.rikkei_assignment.model.Image;
import vn.edu.hust.set.tung.rikkei_assignment.model.Note;
import vn.edu.hust.set.tung.rikkei_assignment.util.Echo;

/**
 * Created by Administrator on 20/10/2017.
 */

public class ChangeNoteActivity extends AppCompatActivity implements OnColorClicked, OnPhotoListener, OnImageRemove, OnPickTimeOK {

    public static final String KEY_DIR = "/TungvdNote/";
    public static final int KEY_CAMERA = 321;
    public static final int KEY_PERMISSION_CAMERA = 1;
    public static final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + KEY_DIR;
    public static final String KEY_LAST_INDEX = "last index";

    EditText etTitle;
    EditText etContent;
    TextView tvNewNoteClock;
    RelativeLayout rlNewNote;
    LinearLayout lnNavigate;
    RelativeLayout rlBack;
    RelativeLayout rlNext;
    RelativeLayout rlDelete;
    RelativeLayout rlShare;
    ImageView ivBack;
    ImageView ivNext;
    RecyclerView rvImage;
    ScrollView svMain;
    LinearLayout lnSetAlarm;
    TextView tvPickHour;
    TextView tvPickDate;
    ImageView ivClearAlarm;
    LinearLayout lnDetailAlarm;

    DBC dbc;
    int indexNote;
    ArrayList<Note> listNote;
    ArrayList<Image> listImage;
    ImageAdapter imageAdapter;
    GridLayoutManager gridLayoutManager;
    ItemDecorationAlbumColumns gridDecorator;

    boolean isChange = false;
    boolean isAlarm = false;
    int gridSize = 3;
    int gridPadding = 10;
    int mColor;
    int remindHour;
    int remindMinute;
    int remindDay;
    int remindMonth;
    int remindYear;

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
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        rvImage = (RecyclerView) findViewById(R.id.rvImage);
        svMain = (ScrollView) findViewById(R.id.svMain);
        lnSetAlarm = (LinearLayout) findViewById(R.id.lnSetAlarm);
        tvPickDate = (TextView) findViewById(R.id.tvPickDate);
        tvPickHour = (TextView) findViewById(R.id.tvPickHour);
        ivClearAlarm = (ImageView) findViewById(R.id.ivClearAlarm);
        lnDetailAlarm = (LinearLayout) findViewById(R.id.lnDetailAlarm);

        lnDetailAlarm.setVisibility(View.INVISIBLE);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridSize = 5;
            gridPadding = 15;
        }
        listImage = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, listImage);
        gridLayoutManager = new GridLayoutManager(this, gridSize, LinearLayoutManager.VERTICAL, false);
        gridDecorator = new ItemDecorationAlbumColumns(gridPadding, gridSize);

        rvImage.setLayoutManager(gridLayoutManager);
        rvImage.addItemDecoration(gridDecorator);
        rvImage.setAdapter(imageAdapter);

        indexNote = getIntent().getIntExtra(MainActivity.KEY_NOTE, MainActivity.KEY_ADD);
        dbc = DBC.getInstance(this);

        if (indexNote == MainActivity.KEY_ADD) {
            setAddConfig();
        } else {
            setEditConfig();
        }

        tvPickHour.setText(remindHour + ":" + remindMinute);
        tvPickDate.setText(remindDay + "/" + remindMonth + "/" + remindYear);
        
        tvPickHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ChooseHourDialog(ChangeNoteActivity.this).show();
            }
        });

        tvPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ChooseDateDialog(ChangeNoteActivity.this).show();
            }
        });

        ivClearAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnDetailAlarm.setVisibility(View.GONE);
                isAlarm = false;
                isChange = true;
            }
        });

        lnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnDetailAlarm.setVisibility(View.VISIBLE);
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
                new PhotoDialog(ChangeNoteActivity.this).show();
                return true;
            case R.id.itemColor:
                new ChooseColorDialog(ChangeNoteActivity.this).show();
                return true;
            case R.id.itemSave:
                save();
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

    public void save() {
        if (indexNote == MainActivity.KEY_ADD) {
            addNote();
        } else {
            editNote();
        }
        gotoMain();
    }

    public void gotoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void setEditConfig() {
        listNote = dbc.getListNote();
        mColor = listNote.get(indexNote).getColor();
        listImage = listNote.get(indexNote).getListImage();
        imageAdapter.setListImage(listImage);
        imageAdapter.notifyDataSetChanged();

        etTitle.setText(listNote.get(indexNote).getName());
        etContent.setText(listNote.get(indexNote).getContent());
        tvNewNoteClock.setText(listNote.get(indexNote).getTime());
        rlNewNote.setBackgroundColor(mColor);
        lnNavigate.setVisibility(View.VISIBLE);

        rlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbc.removeNote(listNote.get(indexNote));
                gotoMain();
            }
        });

        if (indexNote == 0) {
            rlBack.setEnabled(false);
            ivBack.setBackground(getDrawable(R.drawable.ic_navigate_back_unable));
        } else {
            rlBack.setEnabled(true);
            ivBack.setBackground(getDrawable(R.drawable.ic_navigate_before_black_24dp));
        }

        if (indexNote == listNote.size() - 1) {
            rlNext.setEnabled(false);
            ivNext.setBackground(getDrawable(R.drawable.ic_navigate_next_unable));
        } else {
            rlNext.setEnabled(true);
            ivNext.setBackground(getDrawable(R.drawable.ic_navigate_next_black_24dp));
        }

        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote();
                indexNote--;
                isChange = false;
                isAlarm = false;
                lnDetailAlarm.setVisibility(View.INVISIBLE);
                setEditConfig();
            }
        });

        rlNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote();
                indexNote++;
                isChange = false;
                isAlarm = false;
                lnDetailAlarm.setVisibility(View.INVISIBLE);
                setEditConfig();
            }
        });

        rlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote();
                Note note = listNote.get(indexNote);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, note.getName() + "\n" + note.getContent());
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });

        if (listNote.get(indexNote).getTimeRemind() != null && listNote.get(indexNote).getTimeRemind().length() > 0) {
            setTimeDetail(listNote.get(indexNote));
            lnDetailAlarm.setVisibility(View.VISIBLE);
            isAlarm = true;
        } else {
            getTimeDetail();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAddConfig() {
        listImage = new ArrayList<>();
        mColor = getResources().getColor(R.color.btnWhite, null);
        tvNewNoteClock.setText(getTime());
        rlNewNote.setBackgroundColor(mColor);
        lnNavigate.setVisibility(View.GONE);
        getTimeDetail();
    }

    public String getTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return df.format(c.getTime());
    }

    public void getTimeDetail() {
        Calendar c = Calendar.getInstance(Locale.getDefault());
        remindHour = c.get(Calendar.HOUR_OF_DAY);
        remindMinute = c.get(Calendar.MINUTE);
        remindDay = c.get(Calendar.DAY_OF_MONTH);
        remindMonth = c.get(Calendar.MONTH);
        remindYear = c.get(Calendar.YEAR);
    }

    public void setTimeDetail(Note c) {
        remindHour = c.getRemindHour();
        remindMinute = c.getRemindMinute();
        remindDay = c.getRemindDay();
        remindMonth = c.getRemindMonth();
        remindYear = c.getRemindYear();
        tvPickHour.setText(remindHour + ":" + remindMinute);
        tvPickDate.setText(remindDay + "/" + remindMonth + "/" + remindYear);
    }

    public void addNote() {
        String name = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        Note note = new Note(name, content);
        note.setTime(tvNewNoteClock.getText().toString());
        note.setColor(mColor);
        note.setListImage(listImage);
        addAlarm(note);
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
        newNote.setListImage(listImage);

        if (isChange) {
            addAlarm(newNote);
            dbc.editNote(newNote);
            isChange = false;
        }
        listNote = dbc.getListNote();
    }

    @Override
    public void onTakePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    KEY_PERMISSION_CAMERA
            );
        } else requestCamera();
    }

    @Override
    public void onChoosePhoto() {
        Echo.echo("choosing photo");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == KEY_CAMERA && resultCode == RESULT_OK) {
            listImage.add(new Image(imagePath));
            imageAdapter.setListImage(listImage);
            imageAdapter.notifyDataSetChanged();
            isChange = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            requestCamera();
        }
    }

    String imagePath = "";

    public void requestCamera() {

        new File(dir).mkdirs();
        long time = System.currentTimeMillis();
        String file = dir + "IMG_" + time + ".jpg";
        imagePath = file;
        File newFile = new File(file);
        try {
            newFile.createNewFile();
        } catch (Exception e) {
            Echo.echo(e.toString());
        }

        Uri newFileUri = Uri.fromFile(newFile);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, newFileUri);
        startActivityForResult(cameraIntent, KEY_CAMERA);
    }

    @Override
    public void onRemove(int position) {
        new File(listImage.get(position).getLink()).delete();
        listImage.remove(position);
        imageAdapter.setListImage(listImage);
        imageAdapter.setListImage(listImage);
        imageAdapter.notifyDataSetChanged();
        isChange = true;
    }

    @Override
    public void onBackPressed() {
        save();
    }


    @Override
    public void onOKHour(int hour, int minute) {
        remindHour = hour;
        remindMinute = minute;
        isChange = true;
        isAlarm = true;
        tvPickHour.setText(hour + ":" + minute);
    }

    @Override
    public void onOKDate(int day, int month, int year) {
        remindMonth = month;
        remindDay = day;
        remindYear = year;
        isChange = true;
        isAlarm = true;
        tvPickDate.setText(day + "/" + month + "/" + year);
    }

    public void addAlarm(Note note) {
        if (isAlarm) {
            note.setTimeRemind(remindHour + " " + remindMinute + " " + remindDay + " " + remindMonth + " " + remindYear);
        } else {
            note.setTimeRemind("");
        }
    }
}
