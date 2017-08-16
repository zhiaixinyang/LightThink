package com.example.greatbook.middle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.base.ClipImageActivity;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.middle.model.event.SetGroupEvent;
import com.example.greatbook.middle.presenter.AllLocalGroupPresenter;
import com.example.greatbook.middle.presenter.contract.AllLocalGroupContract;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.AlbumUtils;
import com.example.greatbook.utils.BitmapCompressUtils;
import com.example.greatbook.utils.FileUtils;
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
    private Bitmap bmp;
    private String imagePath;

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void addLocalGroupSuc(String suc) {
        ToastUtil.toastLong(suc);
        SetGroupEvent event=new SetGroupEvent();
        event.event=Constants.ADD_GROUP_TO_RECORD_ABANDON;
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
        presenter=new AllLocalGroupPresenter(this);
        new DefaultNavigationBar.Builder(this, null)
                .setTitleText("添加文集")
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
                imagePath= data.getStringExtra(Constants.RETURN_CLIP_PHOTO);
                Bitmap a = BitmapCompressUtils.ratio(imagePath, 200, 200);
                Bitmap b = BitmapFactory.decodeFile(imagePath);
                LogUtils.d(a.getByteCount() / 1024 + "!" + b.getByteCount() / 1024);
                bmp = BitmapFactory.decodeFile(imagePath);
                ivSelect.setImageBitmap(bmp);
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
        toClip.putExtra(Constants.TO_CLIPACTIVITY_NO_CLIP,true);
        startActivityForResult(toClip, Constants.CROP_RESULT_CODE);
    }

    @OnClick({R.id.btn_cancel, R.id.btn_ok, R.id.btn_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                SetGroupEvent event=new SetGroupEvent();
                event.event=Constants.ADD_GROUP_TO_RECORD_ABANDON;
                EventBus.getDefault().post(event);
                finish();
                break;
            case R.id.btn_ok:
                String name=etGroupName.getText().toString();
                String content=etGroupContent.getText().toString();
                User user= AVUser.getCurrentUser(User.class);
                if (user!=null) {
                    if (StringUtils.isEmpty(name)) {
                        ToastUtil.toastShort("给文集设置一个名字");
                    } else {
                        LocalGroup localGroup = new LocalGroup();
                        localGroup.setUserd(false);
                        localGroup.setTime(new Date());
                        localGroup.setBelongId(user.getObjectId());
                        localGroup.setContent(StringUtils.isEmpty(content)?"未设置文集描述":content);
                        localGroup.setGroupPhotoPath(imagePath);
                        localGroup.setTitle(name);
                        presenter.addLocalGroup(localGroup);
                    }
                }else{
                    ToastUtil.toastShort("请登录");
                }
                break;
            case R.id.btn_photo:
                AlbumUtils.startAlbumToClip(this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SetGroupEvent event=new SetGroupEvent();
        event.event=Constants.ADD_GROUP_TO_RECORD_ABANDON;
        EventBus.getDefault().post(event);
        finish();
    }
}
