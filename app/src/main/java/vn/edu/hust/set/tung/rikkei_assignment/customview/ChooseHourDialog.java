package vn.edu.hust.set.tung.rikkei_assignment.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import vn.edu.hust.set.tung.rikkei_assignment.R;
import vn.edu.hust.set.tung.rikkei_assignment.activity.ChangeNoteActivity;
import vn.edu.hust.set.tung.rikkei_assignment.util.Echo;

/**
 * Created by tungt on 11/04/17.
 */

public class ChooseHourDialog extends Dialog implements View.OnClickListener{

    TimePicker timePicker;
    Button btnOK;
    Button btnCancel;

    OnPickTimeOK onPickTimeOK;

    public ChooseHourDialog(ChangeNoteActivity changeNoteActivity) {
        super(changeNoteActivity);
        this.onPickTimeOK = changeNoteActivity;
        setContentView(R.layout.dialog_choose_hour);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        btnOK = (Button) findViewById(R.id.btnHourOK);
        btnCancel = (Button) findViewById(R.id.btnHourCancel);

        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHourOK:
                onPickTimeOK.onOKHour(timePicker.getHour(), timePicker.getMinute());
                dismiss();
                break;
            case R.id.btnHourCancel:
                dismiss();
                break;
            default:
                dismiss();
        }
    }
}
