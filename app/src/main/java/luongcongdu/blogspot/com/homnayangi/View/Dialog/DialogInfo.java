package luongcongdu.blogspot.com.homnayangi.View.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.view.Window;

import luongcongdu.blogspot.com.homnayangi.R;

/**
 * Created by Admin on 3/1/2018.
 */

public class DialogInfo {
    Activity activity;
    Dialog dialog;

    public DialogInfo(Activity activity) {
        this.activity = activity;
        initView();
    }

    public void showDialog() {
        dialog.show();
    }

    public void initView() {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info);
        dialog.setCanceledOnTouchOutside(false);
    }
}
