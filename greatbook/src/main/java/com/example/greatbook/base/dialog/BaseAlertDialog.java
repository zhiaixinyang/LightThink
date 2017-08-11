package com.example.greatbook.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.example.greatbook.R;

/**
 * Created by MDove on 2017/7/31.
 */

public class BaseAlertDialog extends Dialog {
    private AlertController alertController;
    private DialogViewHelper dialogViewHelper;



    public BaseAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        alertController=new AlertController(this,getWindow());
    }
    public void setDialogViewHelper(DialogViewHelper dialogViewHelper){
        this.dialogViewHelper=dialogViewHelper;
    }

    public void setViewText(int viewId, String text) {
        dialogViewHelper.setViewText(viewId,text);
    }

    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        dialogViewHelper.setOnClickListener(viewId,onClickListener);
    }

    public  <T extends View> T getView(int viewId){

        return dialogViewHelper.getView(viewId);
    }

    public static class Builder{
        private final AlertController.AlertParams P;
        public Builder(Context context){
            this(context, R.style.Default_Dialog);
        }
        public Builder(Context context,int themeResId){
            P =new AlertController.AlertParams(context,themeResId);
        }

        public Builder setContentView(View view){
            P.view=view;
            P.layoutId=0;
            return this;
        }


        public Builder setContentView(int layoutId){
            P.layoutId=layoutId;
            P.view=null;
            return this;
        }

        public Builder setFullWidth(){
            P.width= ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        public Builder setFromBottom(boolean isAnimation){
            if (isAnimation){
                P.animation=R.style.main_menu_animstyle;
            }
            P.gravity= Gravity.BOTTOM;
            return this;
        }
        public Builder setWidthAndHeight(int width,int height){
            P.width=width;
            P.height=height;
            return this;
        }

        public Builder addDefaultANimation(){
            P.animation=R.style.main_menu_animstyle;
            return this;
        }

        public Builder addAnimation(int animationStyle){
            P.animation=animationStyle;
            return this;
        }

        public Builder setCancelable(boolean cancelable){
            P.cancelable=cancelable;
            return this;
        }

        public Builder setViewText(int viewId,String text){
            P.viewIdArray.put(viewId,text);
            return this;
        }

        public Builder setOnClickListener(int viewId, View.OnClickListener onClickListener){
            P.listenerArray.put(viewId,onClickListener);
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener){
            P.onCancelListener=onCancelListener;
            return this;
        }
        public Builder setOnDismissListener(OnDismissListener onDismissListener){
            P.onDismissListener=onDismissListener;
            return this;
        }
        public Builder setOnKeyListener(OnKeyListener onKeyListener){
            P.onKeyListener=onKeyListener;
            return this;
        }

        public BaseAlertDialog create(){
            final BaseAlertDialog myAlertDialog=new BaseAlertDialog(P.context,
                    P.themeResId);
            P.apply(myAlertDialog.alertController);
            myAlertDialog.setCancelable(P.cancelable);
            if (P.cancelable){
                myAlertDialog.setCanceledOnTouchOutside(true);
            }
            myAlertDialog.setOnCancelListener(P.onCancelListener);
            return myAlertDialog;
        }
    }

}
