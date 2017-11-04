package vn.edu.hust.set.tung.rikkei_assignment.custom;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import vn.edu.hust.set.tung.rikkei_assignment.R;
import vn.edu.hust.set.tung.rikkei_assignment.activity.ChangeNoteActivity;

/**
 * Created by tungt on 11/04/17.
 */

public class ChooseDateDialog extends Dialog implements View.OnClickListener{

    DatePicker datePicker;
    Button btnOK;
    Button btnCancel;

    OnPickTimeOK onPickTimeOK;

    public ChooseDateDialog(ChangeNoteActivity changeNoteActivity) {
        super(changeNoteActivity);
        this.onPickTimeOK = changeNoteActivity;
        setContentView(R.layout.dialog_choose_date);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        btnOK = (Button) findViewById(R.id.btnDateOK);
        btnCancel = (Button) findViewById(R.id.btnDateCancel);

        btnCancel.setOnClickListener(this);
        btnOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDateOK:
                onPickTimeOK.onOKDate(datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
                dismiss();
                break;
            case R.id.btnDateCancel:
                dismiss();
                break;
            default:
                dismiss();
        }
    }
}
