package vn.edu.hust.set.tung.rikkei_assignment.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import vn.edu.hust.set.tung.rikkei_assignment.R;
import vn.edu.hust.set.tung.rikkei_assignment.customview.ItemDecorationAlbumColumns;
import vn.edu.hust.set.tung.rikkei_assignment.customview.RecyclerItemClickListener;
import vn.edu.hust.set.tung.rikkei_assignment.customview.NoteAdapter;
import vn.edu.hust.set.tung.rikkei_assignment.db.DBC;

public class MainActivity extends AppCompatActivity {

    public static final int GRID_SIZE = 3;
    public static final int GRID_SPACE = 10;
    public static final String LAYOUT_MANAGER_SHARED = "my abcxyz layout manager";

    DBC dbc;
    RecyclerView rvUsers;
    NoteAdapter noteAdapter;

    RecyclerView.LayoutManager linearManager;
    GridLayoutManager gridManager;
    ItemDecorationAlbumColumns gridDecorator;
    DividerItemDecoration linerDecorator;

    int grilin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvUsers = (RecyclerView) findViewById(R.id.rvUsers);

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        grilin = sharedPreferences.getInt(LAYOUT_MANAGER_SHARED, 0);

        dbc = DBC.getInstance(this);
        noteAdapter = new NoteAdapter(dbc.getListNote());
        gridManager = new GridLayoutManager(this, GRID_SIZE, LinearLayoutManager.VERTICAL, false);
        linearManager = new LinearLayoutManager(getApplicationContext());
        gridDecorator = new ItemDecorationAlbumColumns(GRID_SPACE, GRID_SIZE);
        linerDecorator = new DividerItemDecoration(rvUsers.getContext(), DividerItemDecoration.VERTICAL);

        if (grilin == 0) {
            setLinearManager();
        } else {
            setGridManager();
        }
        rvUsers.setAdapter(noteAdapter);

        rvUsers.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this, rvUsers, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.i("main", "position = " + position);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                }));
    }

    public void setGridManager() {
        rvUsers.setLayoutManager(gridManager);
        rvUsers.addItemDecoration(gridDecorator);
    }

    public void setLinearManager() {
        rvUsers.setLayoutManager(linearManager);
        rvUsers.addItemDecoration(linerDecorator);
    }

    public void setPreference() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LAYOUT_MANAGER_SHARED, grilin);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAdd:
                Intent intent = new Intent(this, NewNoteActivity.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }
}
