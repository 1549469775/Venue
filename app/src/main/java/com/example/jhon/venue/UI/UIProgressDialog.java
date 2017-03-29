package com.example.jhon.venue.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

/**
 * Created by John on 2017/3/14.
 */

public class UIProgressDialog {

    private static ProgressDialog progressDialog;

    public static void showProgress(Context context,String message){
        if (progressDialog==null){
            progressDialog=new ProgressDialog(context);
        }
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static void closeProgress(){
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
            progressDialog=null;
        }
    }

}
