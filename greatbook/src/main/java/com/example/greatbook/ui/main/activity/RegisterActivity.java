package com.example.greatbook.ui.main.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.bumptech.glide.Glide;
import com.example.greatbook.App;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.base.ClipImageActivity;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.AlbumUtils;
import com.example.greatbook.utils.FileAndImageUtils;
import com.example.greatbook.utils.FileUtils;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.utils.TransWindowUtils;
import com.example.greatbook.utils.WaitNetPopupWindowUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * Created by MDove on 2016/10/20.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener, PopupWindow.OnDismissListener {
    @BindView(R.id.btn_login)
    TextView btnLogin;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassWord;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.et_name)
    EditText etName;

    private String imagePath;
    private File fileA;
    private File file;

    private WaitNetPopupWindowUtils waitNetPopupWindowUtils = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void init() {
        waitNetPopupWindowUtils = new WaitNetPopupWindowUtils();
        TransWindowUtils.setTransWindow(this);
        btnLogin.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.iv_avatar:
                if (ActivityCompat.checkSelfPermission(RegisterActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RegisterActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
                } else {
                    AlbumUtils.startAlbumToClip(this);
                }
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AlbumUtils.startAlbumToClip(this);
                } else {
                    ToastUtil.toastShort("请给我们操作权限呐");
                }
                break;
            case 112:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AlbumUtils.startAlbumToClip(this);
                } else {
                    ToastUtil.toastShort("请给我们操作权限呐");
                }
                break;
        }
    }


    private void compressWithRx(final File file) {
        Observable.just(file)
                .observeOn(Schedulers.io())
                .map(new Func1<File, File>() {
                    @Override
                    public File call(File file) {
                        try {
                            return Luban.with(RegisterActivity.this).load(file).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {

                    @Override
                    public void call(File o) {
                        fileA=o;
                        Glide.with(RegisterActivity.this).load(o).into(ivAvatar);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 123:
                String path = data.getStringExtra(Constants.RETURN_CLIP_PHOTO);
//                bmp = BitmapFactory.decodeFile(path);
//                GlideUtils.load(imagePath,ivAvatar);
                compressWithRx(new File(path));
                break;
            case Constants.START_ALBUM_REQUESTCODE:
                toClip(data.getData());
                break;
            case Constants.CAMERA_WITH_DATA:

                // 照相机程序返回的,再次调用图片剪辑程序去修剪图片
                if (imagePath != null) {
                    toClip(data.getData());
                }
                break;
        }
    }

    private void toClip(Uri data) {
        imagePath = FileUtils.getRealPathFromUri(RegisterActivity.this, data);
        Intent toClip = new Intent(this, ClipImageActivity.class);
        toClip.putExtra(Constants.TO_CLIPACTIVITY, imagePath);
        startActivityForResult(toClip, 123);
    }

    private void login() {
        //先判断网络问题
        if (NetUtil.isNetworkAvailable()) {
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
                AVFile avFile = new AVFile(FileAndImageUtils.getFileName(imagePath), FileUtils.getBytesFromFile(fileA));
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
                                                Intent intent = new Intent(App.getInstance().getContext(), MainNewActivity.class);
                                                startActivity(intent);
                                                finish();
                                                overridePendingTransition(R.anim.login_in, R.anim.login_out);
                                            } else {
                                                waitNetPopupWindowUtils.hideWaitNetPopupWindow(RegisterActivity.this);
                                                ToastUtil.toastShort("注册失败，请保证网络连接并重试。");
                                            }
                                        }
                                    });
                        } else {
                            waitNetPopupWindowUtils.hideWaitNetPopupWindow(RegisterActivity.this);
                            ToastUtil.toastShort("账号重复/网络连接有问题");
                        }
                    }
                });
            } else {
                ToastUtil.toastShort("请完成相关信息填写。");
            }
        } else {
            ToastUtil.toastShort("未连接网络！");
        }
    }

    @Override
    public void onDismiss() {
        TransWindowUtils.setBackgroundAlpha(this, 1f);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    private void initDB() {
        //第一次登陆往本地数据库初始化一些数据
        LocalGroupDao localGroupDao = App.getDaoSession().getLocalGroupDao();
        LocalRecordDao localRecordDao = App.getDaoSession().getLocalRecordDao();

        localGroupDao.deleteAll();
        localRecordDao.deleteAll();

        LocalGroup localGroupJok = new LocalGroup();
        localGroupJok.setTitle("我的本地段子集");
        localGroupJok.setTime(new Date());
        localGroupJok.setUserd(true);
        localGroupJok.setGroupPhotoPath("");
        localGroupJok.setBgColor(ContextCompat.getColor(this,R.color.blue)+"");
        localGroupJok.setGroupLocalPhotoPath(R.drawable.icon_default_group_jok);
        localGroupJok.setBelongId(AVUser.getCurrentUser().getObjectId());
        localGroupJok.setContent("随手记录让我一笑的段子。");
        localGroupDao.insert(localGroupJok);

        LocalGroup localGroupEncourage = new LocalGroup();
        localGroupEncourage.setTitle("我的本地鸡汤集");
        localGroupEncourage.setTime(new Date());
        localGroupEncourage.setUserd(true);
        localGroupEncourage.setGroupPhotoPath("");
        localGroupEncourage.setBgColor(ContextCompat.getColor(this,R.color.blue)+"");
        localGroupEncourage.setGroupLocalPhotoPath(R.drawable.icon_default_group_encourage);
        localGroupEncourage.setBelongId(AVUser.getCurrentUser().getObjectId());
        localGroupEncourage.setContent("随手记录让我燃起来的鸡汤。");
        localGroupDao.insert(localGroupEncourage);

        LocalGroup localGroupShortEssay = new LocalGroup();
        localGroupShortEssay.setTitle("我的本地清新集");
        localGroupShortEssay.setTime(new Date());
        localGroupShortEssay.setUserd(true);
        localGroupShortEssay.setBgColor(ContextCompat.getColor(this,R.color.blue)+"");
        localGroupShortEssay.setGroupLocalPhotoPath(R.drawable.icon_default_group_short_eassy);
        localGroupShortEssay.setBelongId(AVUser.getCurrentUser().getObjectId());
        localGroupShortEssay.setBelongId(AVUser.getCurrentUser().getObjectId());
        localGroupShortEssay.setContent("随手记录让我静心的短句。");
        localGroupDao.insert(localGroupShortEssay);
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        Intent toLogin=new Intent(this,LoginActivity.class);
        startActivity(toLogin);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        App.toIntent(this,LoginActivity.class);
    }
}
