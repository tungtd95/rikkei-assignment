package vn.edu.hust.set.tung.rikkei_assignment.customview;

import android.app.Dialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import vn.edu.hust.set.tung.rikkei_assignment.R;
import vn.edu.hust.set.tung.rikkei_assignment.activity.ChangeNoteActivity;

/**
 * Created by tungt on 10/20/17.
 */

public class ChooseColorDialog extends Dialog implements View.OnClickListener {

    Button btnWhite;
    Button btnYellow;
    Button btnGreen;
    Button btnBlue;
    OnColorClicked onColorClicked;

    public ChooseColorDialog(ChangeNoteActivity changeNoteActivity) {
        super(changeNoteActivity);
        setContentView(R.layout.dialog_choose_color);
        onColorClicked = changeNoteActivity;
        setTitle("Choose Color");
        btnWhite = (Button) findViewById(R.id.btnWhite);
        btnYellow = (Button) findViewById(R.id.btnYellow);
        btnGreen = (Button) findViewById(R.id.btnGreen);
        btnBlue = (Button) findViewById(R.id.btnBlue);

        btnWhite.setOnClickListener(this);
        btnYellow.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnWhite:
                onColorClicked.onClickedColor(getContext().getResources().getColor(R.color.btnWhite, null));
                dismiss();
                return;
            case R.id.btnYellow:
                onColorClicked.onClickedColor(getContext().getResources().getColor(R.color.btnYellow, null));
                dismiss();
                return;
            case R.id.btnGreen:
                onColorClicked.onClickedColor(getContext().getResources().getColor(R.color.btnGreen, null));
                dismiss();
                return;
            case R.id.btnBlue:
                onColorClicked.onClickedColor(getContext().getResources().getColor(R.color.btnBlue, null));
                dismiss();
                return;
            default:
                dismiss();
                return;
        }
    }
}
