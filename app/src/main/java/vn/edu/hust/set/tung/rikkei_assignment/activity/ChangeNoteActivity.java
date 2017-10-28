package vn.edu.hust.set.tung.rikkei_assignment.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.edu.hust.set.tung.rikkei_assignment.R;
import vn.edu.hust.set.tung.rikkei_assignment.customview.ChooseColorDialog;
import vn.edu.hust.set.tung.rikkei_assignment.customview.ImageAdapter;
import vn.edu.hust.set.tung.rikkei_assignment.customview.ItemDecorationAlbumColumns;
import vn.edu.hust.set.tung.rikkei_assignment.customview.OnColorClicked;
import vn.edu.hust.set.tung.rikkei_assignment.customview.OnPhotoListener;
import vn.edu.hust.set.tung.rikkei_assignment.customview.PhotoDialog;
import vn.edu.hust.set.tung.rikkei_assignment.db.DBC;
import vn.edu.hust.set.tung.rikkei_assignment.model.Note;
import vn.edu.hust.set.tung.rikkei_assignment.util.Echo;

/**
 * Created by Administrator on 20/10/2017.
 */

public class ChangeNoteActivity extends AppCompatActivity implements OnColorClicked, OnPhotoListener {

    public static final String KEY_DIR = "/TungvdNote/";
    public static final int KEY_CAMERA = 321;
    public static final int KEY_PERMISSION_CAMERA = 1;
    public static final int KEY_GRID_SIZE = 3;
    public static final int KEY_GRID_PADDING = 10;

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


    DBC dbc;
    int indexNote;
    ArrayList<Note> listNote;
    ArrayList<String> listImage;
    ImageAdapter imageAdapter;
    GridLayoutManager gridLayoutManager;
    ItemDecorationAlbumColumns gridDecorator;

    boolean isChange = false;

    int mColor;

    final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + KEY_DIR;

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

        listImage = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, listImage);
        gridLayoutManager = new GridLayoutManager(this, KEY_GRID_SIZE, LinearLayoutManager.VERTICAL, false);
        gridDecorator = new ItemDecorationAlbumColumns(KEY_GRID_PADDING, KEY_GRID_SIZE);

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
                indexNote --;
                setEditConfig();
            }
        });

        rlNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote();
                indexNote ++;
                setEditConfig();
            }
        });
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
            isChange = false;
        }
    }

    @Override
    public void onTakePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] { Manifest.permission.CAMERA,
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
            listImage.add(imagePath);
            imageAdapter.setListImage(listImage);
            imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    )
    {
        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED){
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
}
