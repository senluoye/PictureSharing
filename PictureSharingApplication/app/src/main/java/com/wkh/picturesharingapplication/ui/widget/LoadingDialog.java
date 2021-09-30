package com.wkh.picturesharingapplication.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.wkh.picturesharingapplication.R;

/**
 * 对话框
 */
public class LoadingDialog extends Dialog {

    private AVLoadingIndicatorView avi;
    private TextView messagetv;
    private RelativeLayout loadingbg;

    public LoadingDialog(Context context, int theme) {
        super(context, theme);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.loading);
        loadingbg = (RelativeLayout) findViewById(R.id.loadingbg);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        messagetv = (TextView) findViewById(R.id.message);
    }

    public LoadingDialog setMessage(String message) {
        messagetv.setText(message);
        return this;
    }

    @Override
    public void show() {
        super.show();
        avi.smoothToShow();
    }


    public LoadingDialog setLoadingBg(int Colorbg) {
        loadingbg.setBackgroundColor(Colorbg);
        return this;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        avi.smoothToHide();
    }
}