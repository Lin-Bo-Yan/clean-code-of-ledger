package com.example.cleancodeofledger.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.cleancodeofledger.R;

public class DialogUtils {

    static public AlertDialog showDialogMessage(Context context, String title, String text, CallbackUtils.noReturn callback) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton(context.getString(R.string.sure_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callback.Callback();
                    }
                })
                .create();
        alertDialog.show();
        return alertDialog;
    }
}
