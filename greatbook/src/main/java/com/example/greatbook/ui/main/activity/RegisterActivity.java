package com.example.greatbook.ui.main.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.example.greatbook.App;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.utils.BitmapCompressUtils;
import com.example.greatbook.utils.FileAndImageUtils;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.utils.TransWindowUtils;
import com.example.greatbook.utils.WaitNetPopupWindowUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by MDove on 2016/10/20.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener,PopupWindow.OnDismissListener{
    @BindView(R.id.btn_login) TextView btnLogin;
    @BindView(R.id.et_account) EditText etAccount;
    @BindView(R.id.et_password) EditText etPassWord;
    @BindView(R.id.iv_avatar) ImageView ivAvatar;
    @BindView(R.id.et_name) EditText etName;

    private String imagePath;
    private Bitmap bmp;

    private WaitNetPopupWindowUtils waitNetPopupWindowUtils=null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void init() {
        waitNetPopupWindowUtils=new WaitNetPopupWindowUtils();
        TransWindowUtils.setTransWindow(this);
        btnLogin.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.iv_avatar:
                if (ActivityCompat.checkSelfPermission(RegisterActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
                        ActivityCompat.checkSelfPermission(RegisterActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RegisterActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},111);
                }else{
                    openAlbum();
                }
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 111:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED&&
                        grantResults[1]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    ToastUtil.toastShort("请给我们操作权限呐");
                }
                break;
        }
    }

    private void openAlbum(){
        Intent openImage=new Intent(Intent.ACTION_PICK,null);
        openImage.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(openImage, IntentConstants.OPEN_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK&&requestCode==IntentConstants.OPEN_IMAGE){
            if (data!=null){
                Uri selectImageUri=data.getData();
                Bitmap bitmap=FileAndImageUtils.getBitmapFromUri(this,selectImageUri);
                bmp= BitmapCompressUtils.ratio(bitmap,120,120);
                imagePath = FileAndImageUtils.getPathUrlFromUri(App.getInstance().getContext(),selectImageUri);
                if (selectImageUri!=null&&bmp!=null){
                    GlideUtils.load(FileAndImageUtils.getByteFromBitmap(bmp),ivAvatar);
                }
            }
        }
    }

    private void login() {
        //先判断网路问题
        if (NetUtil.isNetworkAvailable()){
            if (!StringUtils.isEmpty(imagePath)
                    && !StringUtils.isEmpty(etAccount.getText().toString())
                    && !StringUtils.isEmpty(etPassWord.getText().toString())
                    && !StringUtils.isEmpty(etName.getText().toString())) {
                waitNetPopupWindowUtils.showWaitNetPopupWindow(this);
                final User user = new User();
                user.setUsername(etAccount.getText().toString());
                user.setPassword(etPassWord.getText().toString());
                user.setName(etName.getText().toString());
                user.setCharm(0);
                user.setFans(0);
                user.setMoney(0);
                user.setSignature("本人太懒...没有设置签名");
                AVFile avFile = new AVFile(FileAndImageUtils.getFileName(imagePath), FileAndImageUtils.getByteFromBitmap(bmp));
                user.setAvatar(avFile);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            user.logInInBackground(etAccount.getText().toString(),
                                    etPassWord.getText().toString(),
                                    new LogInCallback<AVUser>() {
                                        @Override
                                        public void done(AVUser avUser, AVException e) {
                                            if (e == null) {
                                                //首次登陆初始化本地数据库
                                                initDB();
                                                ToastUtil.toastShort("注册并登陆成功。");
                                                SharedPreferences sharedPreferences = MySharedPreferences.getFristActivityInstance();
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putInt("count", 1);
                                                editor.commit();
                                                Intent intent = new Intent(App.getInstance().getContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                                overridePendingTransition(R.anim.login_in, R.anim.login_out);
                                            }else{
                                                waitNetPopupWindowUtils.hideWaitNetPopupWindow(RegisterActivity.this);
                                                ToastUtil.toastShort("注册失败，请保证网络连接并重试。");
                                            }
                                        }
                                    });
                        }else{
                            waitNetPopupWindowUtils.hideWaitNetPopupWindow(RegisterActivity.this);
                            ToastUtil.toastShort("账号重复/网络连接有问题");
                        }
                    }
                });
            }else{
                ToastUtil.toastShort("请完成相关信息填写。");
            }
        } else{
            ToastUtil.toastShort("未连接网络！");
        }
    }

    @Override
    public void onDismiss() {
        TransWindowUtils.setBackgroundAlpha(this,1f);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    private void initDB() {
        //第一次登陆往本地数据库初始化一些数据
        LocalGroupDao localGroupDao=App.getDaoSession().getLocalGroupDao();
        LocalRecordDao localRecordDao=App.getDaoSession().getLocalRecordDao();

        localGroupDao.deleteAll();
        localRecordDao.deleteAll();

        LocalGroup localGroupJok=new LocalGroup();
        localGroupJok.setTitle("我的本地段子集");
        localGroupJok.setTime(new Date());
        localGroupJok.setUserd(true);
        localGroupJok.setBelongId(AVUser.getCurrentUser().getObjectId());
        localGroupJok.setContent("随手记录让我一笑的段子。");
        localGroupDao.insert(localGroupJok);

        LocalGroup localGroupEncourage=new LocalGroup();
        localGroupEncourage.setTitle("我的本地鸡汤集");
        localGroupEncourage.setTime(new Date());
        localGroupEncourage.setUserd(true);
        localGroupEncourage.setBelongId(AVUser.getCurrentUser().getObjectId());
        localGroupEncourage.setContent("随手记录让我燃起来的鸡汤。");
        localGroupDao.insert(localGroupEncourage);

        LocalGroup localGroupShortEssay =new LocalGroup();
        localGroupShortEssay.setTitle("我的本地清新集");
        localGroupShortEssay.setTime(new Date());
        localGroupShortEssay.setUserd(true);
        localGroupShortEssay.setBelongId(AVUser.getCurrentUser().getObjectId());
        localGroupShortEssay.setContent("随手记录让我静心的短句。");
        localGroupDao.insert(localGroupShortEssay);
    }

}
