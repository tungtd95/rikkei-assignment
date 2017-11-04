package vn.edu.hust.set.tung.rikkei_assignment.custom;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import vn.edu.hust.set.tung.rikkei_assignment.R;
import vn.edu.hust.set.tung.rikkei_assignment.activity.ChangeNoteActivity;

/**
 * Created by tungt on 10/28/17.
 */

public class PhotoDialog extends Dialog {

    LinearLayout lnTakePhoto;
    LinearLayout lnChoosePhoto;
    OnPhotoListener onPhotoListener;

    public PhotoDialog(ChangeNoteActivity context) {
        super(context);
        this.onPhotoListener = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo);
        lnTakePhoto = (LinearLayout) findViewById(R.id.lnTakePhoto);
        lnChoosePhoto = (LinearLayout) findViewById(R.id.lnChoosePhoto);

        lnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPhotoListener.onTakePhoto();
                dismiss();
            }
        });

        lnChoosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPhotoListener.onChoosePhoto();
                dismiss();
            }
        });
    }
}
