package com.example.greatbook.utils;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greatbook.App;
import com.example.greatbook.R;


/**
 * Created by MDove on 2016/2/17.
 */
public class ToastUtil {
    private static Toast toast;
    private static Context context = App.getInstance().getContext();
    private String msg;
    private static ToastUtil toastUtil;


    public ToastUtil(Context context) {
        this.context = context;
    }

    public Toast createLong() {
        View contentView = View.inflate(context, R.layout.view_dialog_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        tvMsg.setText(msg);
        return toast;
    }

    public Toast createShort() {
        View contentView = View.inflate(context, R.layout.view_dialog_toast, null);
        contentView.getLayoutParams();
        TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(Gravity.BOTTOM, 0, DpUtils.dp2px(20));
        toast.setDuration(Toast.LENGTH_SHORT);
        tvMsg.setText(msg);
        return toast;
    }

    //获得全局Context;
    public static void toastShort(String content) {
        if (toastUtil == null) {
            toastUtil = new ToastUtil(context);
        }
        toastUtil.setText(content);
        toastUtil.createShort().show();
    }

    public static void toastLong(String content) {
        if (toastUtil == null) {
            toastUtil = new ToastUtil(context);
        }
        toastUtil.setText(content);
        toastUtil.createShort().show();
    }

    //非UI线程使用Toast
    public static void noUIToastShort(String content) {
        Looper.prepare();
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
        Looper.loop();
    }

    public static void noUIToastLong(String content) {
        Looper.prepare();
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        } else {
            toast.setText(content);
        }
        toast.show();
        Looper.loop();
    }

    public void show() {
        if (toast != null) {
            toast.show();
        }
    }

    public void setText(String text) {
        msg = text;
    }
}
