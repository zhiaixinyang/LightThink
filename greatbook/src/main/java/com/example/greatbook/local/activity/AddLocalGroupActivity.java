package com.example.greatbook.local.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.base.ClipImageActivity;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.local.model.event.SetGroupEvent;
import com.example.greatbook.local.presenter.AllLocalGroupPresenter;
import com.example.greatbook.local.presenter.contract.AllLocalGroupContract;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.AlbumUtils;
import com.example.greatbook.utils.FileUtils;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.DefaultNavigationBar;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MDove on 2017/8/16.
 */

public class AddLocalGroupActivity extends BaseActivity<AllLocalGroupPresenter> implements AllLocalGroupContract.View {
    @BindView(R.id.et_group_name)
    EditText etGroupName;
    @BindView(R.id.et_group_content)
    EditText etGroupContent;
    @BindView(R.id.iv_select)
    ImageView ivSelect;
    @BindView(R.id.btn_off_photo)
    ImageView btnOffPhoto;
    private Bitmap bmp;
    private String imagePath;

    public static final String IS_SHOW_ONE_GROUP_TAG="is_show_one_group_tag";
    public static final String IS_SHOW_ONE_GROUP="is_show_one_group";

    public static final String IS_ALTER="is_alter";

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void addLocalGroupSuc(String suc) {
        ToastUtil.toastLong(suc);
        SetGroupEvent event = new SetGroupEvent();
        event.event = Constants.ADD_GROUP_TO_RECORD_ABANDON;
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public void addLocalGroupErr(String err) {
        LogUtils.d(err);
    }

    @Override
    public void addLocalGroupToNetReturn(String returnStr) {
        LogUtils.d(returnStr);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_group;
    }

    @Override
    public void init() {
        String title=getIntent().getStringExtra(IS_SHOW_ONE_GROUP_TAG);
        if (StringUtils.isEmpty(title)){
            title="添加文集";
        }
        LocalGroup localGroup=getIntent().getParcelableExtra(IS_ALTER);
        if (localGroup!=null){
            showAlter(localGroup);
        }

        presenter = new AllLocalGroupPresenter(this);
        new DefaultNavigationBar.Builder(this, null)
                .setTitleText(title)
                .setLeftResId(R.drawable.btn_back_)
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).builder();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constants.CROP_RESULT_CODE:
                imagePath = data.getStringExtra(Constants.RETURN_CLIP_PHOTO);
                Glide.with(AddLocalGroupActivity.this).load(imagePath).into(ivSelect);
                btnOffPhoto.setVisibility(View.VISIBLE);
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
        imagePath = FileUtils.getRealPathFromUri(this, data);
        Intent toClip = new Intent(this, ClipImageActivity.class);
        toClip.putExtra(Constants.TO_CLIPACTIVITY, imagePath);
        toClip.putExtra(Constants.TO_CLIPACTIVITY_NO_CLIP, true);
        startActivityForResult(toClip, Constants.CROP_RESULT_CODE);
    }

    @OnClick({R.id.btn_cancel, R.id.btn_ok, R.id.btn_photo, R.id.btn_off_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                SetGroupEvent event = new SetGroupEvent();
                event.event = Constants.ADD_GROUP_TO_RECORD_ABANDON;
                EventBus.getDefault().post(event);
                finish();
                break;
            case R.id.btn_ok:
                String name = etGroupName.getText().toString();
                String content = etGroupContent.getText().toString();
                User user = AVUser.getCurrentUser(User.class);
                if (user != null) {
                    if (StringUtils.isEmpty(name)) {
                        ToastUtil.toastShort("给文集设置一个名字");
                    } else {
                        //此情况为选中图片
                        LocalGroup localGroup = new LocalGroup();
                        localGroup.isUserd=false;
                        localGroup.time=new Date();
                        localGroup.belongId=user.getObjectId();
                        localGroup.groupPhotoPath=imagePath;
                        localGroup.content=StringUtils.isEmpty(content) ? "未设置文集描述" : content;
                        localGroup.groupLocalPhotoPath=0;
                        localGroup.bgColor=ContextCompat.getColor(this, R.color.blue) + "";
                        localGroup.title=name;
                        presenter.addLocalGroup(localGroup);
                    }
                } else {
                    ToastUtil.toastShort("请登录");
                }
                break;
            case R.id.btn_photo:
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
                } else {
                    AlbumUtils.startAlbumToClip(this);
                }
                break;
            case R.id.btn_off_photo:
                //清除选中照片，选择默认照片
                if (!StringUtils.isEmpty(imagePath)) {
                    imagePath = null;
                    ivSelect.setImageBitmap(null);
                    btnOffPhoto.setVisibility(View.GONE);
                    ivSelect.setImageResource(R.drawable.btn_photo);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SetGroupEvent event = new SetGroupEvent();
        event.event = Constants.ADD_GROUP_TO_RECORD_ABANDON;
        EventBus.getDefault().post(event);
        finish();
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
        }
    }

    private void showAlter(LocalGroup group){
        etGroupName.setText(StringUtils.isEmpty(group.title)?"":group.title);
        etGroupContent.setText(group.content);
        imagePath=group.groupPhotoPath;
        if (StringUtils.isEmpty(imagePath)){
            ivSelect.setImageResource(group.groupLocalPhotoPath);
        }else{
            GlideUtils.loadSmallIv(group.groupPhotoPath,ivSelect);
        }
        btnOffPhoto.setVisibility(View.VISIBLE);
    }
}
