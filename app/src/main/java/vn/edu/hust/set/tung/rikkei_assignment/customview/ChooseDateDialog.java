package vn.edu.hust.set.tung.rikkei_assignment.customview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import vn.edu.hust.set.tung.rikkei_assignment.R;
import vn.edu.hust.set.tung.rikkei_assignment.util.Echo;

/**
 * Created by tungt on 11/04/17.
 */

public class ChooseDateDialog extends Dialog implements View.OnClickListener{

    DatePicker datePicker;
    Button btnOK;
    Button btnCancel;

    public ChooseDateDialog(@NonNull Context context) {
        super(context);
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
                Echo.echo(datePicker.getDayOfMonth() + " " + datePicker.getMonth() + " " + datePicker.getYear());
                break;
            case R.id.btnDateCancel:
                dismiss();
                break;
            default:
                dismiss();
        }
    }
}
