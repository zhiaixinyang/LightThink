package com.example.greatbook.ui.main.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.App;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseLazyFragment;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.ui.main.activity.FeedBackActivity;
import com.example.greatbook.ui.main.activity.LoginActivity;
import com.example.greatbook.ui.main.activity.MyPrivateAdjustActivity;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.CircleImageView;
import com.example.greatbook.widght.switchbutton.IconSwitch;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by MDove on 2016/11/1.
 */

public class MyPrivateFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.srf_myprivate)
    SwipeRefreshLayout srfMyPrivate;
    private User user;

    // 语音合成对象
    private SpeechSynthesizer mTts;

    // 默认发音人
    private String voicer = "xiaoyan";

    private String[] mCloudVoicersEntries;
    private String[] mCloudVoicersValue;

    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;

    // 云端/本地单选按钮
    private RadioGroup mRadioGroup;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    public static MyPrivateFragment newInstance() {
        Bundle args = new Bundle();
        MyPrivateFragment fragment = new MyPrivateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_myprivate;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        LogUtils.d("initViewsAndEvents");

        srfMyPrivate.setOnRefreshListener(this);
    }

    @Override
    protected void onFirstUserVisible() {
        LogUtils.d("onFirstUserVisible");

        initAvatar();
        onRefresh();
    }

    @Override
    protected void onUserVisible() {
        LogUtils.d("onUserVisible");

        initAvatar();
        onRefresh();
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(App.getInstance().getContext(), new InitListener() {
            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                    ToastUtil.toastShort("初始化失败,错误码：" + code);
                } else {
                    // 初始化成功，之后可以调用startSpeaking方法
                    // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                    // 正确的做法是将onCreate中的startSpeaking调用移至这里
                    mTts.startSpeaking("是雪地中樱花飘落的浪漫，还是夜空下的灯火恢宏。" +
                                    "是四季轮转的天地，还是东方风雅于西方恢宏演奏的乐章，没错。我最傻了",
                            new SynthesizerListener() {

                                @Override
                                public void onSpeakBegin() {
                                    ToastUtil.toastShort("开始播放");
                                }

                                @Override
                                public void onSpeakPaused() {
                                    ToastUtil.toastShort("暂停播放");
                                }

                                @Override
                                public void onSpeakResumed() {
                                    ToastUtil.toastShort("继续播放");
                                }

                                @Override
                                public void onBufferProgress(int percent, int beginPos, int endPos,
                                                             String info) {
                                    // 合成进度
                                    mPercentForBuffering = percent;
                                    ToastUtil.toastShort(String.format(getString(R.string.tts_toast_format),
                                            mPercentForBuffering, mPercentForPlaying));
                                }

                                @Override
                                public void onSpeakProgress(int percent, int beginPos, int endPos) {
                                    // 播放进度
                                    mPercentForPlaying = percent;
                                    ToastUtil.toastShort(String.format(getString(R.string.tts_toast_format),
                                            mPercentForBuffering, mPercentForPlaying));
                                }

                                @Override
                                public void onCompleted(SpeechError speechError) {

                                }

                                @Override
                                public void onEvent(int i, int i1, int i2, Bundle bundle) {

                                }

                            });
                }
            }
        });

    }

    @Override
    protected void onUserInvisible() {
    }



    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void onRefresh() {
        tvName.setText(user.getName());
        GlideUtils.load(user.getAvatar().getUrl(), ivAvatar);
        srfMyPrivate.setRefreshing(false);
    }

    @OnClick({R.id.btn_adjust, R.id.btn_feedback, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_adjust:
                adjust();
                break;
            case R.id.btn_feedback:
                Intent toFeedBack=new Intent(App.getInstance().getContext(),FeedBackActivity.class);
                startActivity(toFeedBack);
                break;
            case R.id.btn_exit:
                exitAccount();
                break;
        }
    }
    private void initAvatar() {
        if (AVUser.getCurrentUser(User.class) != null) {
            user = AVUser.getCurrentUser(User.class);
            if (!StringUtils.isEmpty(user.getName())) {
                tvName.setText(user.getName());
                GlideUtils.load(user.getAvatar().getUrl(), ivAvatar);
            } else {
                tvName.setText("书心用户");
                GlideUtils.load(user.getAvatar().getUrl(), ivAvatar);
            }
        }
    }

    private void adjust() {
        Intent toAdjust=new Intent(App.getInstance().getContext(), MyPrivateAdjustActivity.class);
        startActivity(toAdjust);
    }

    private void exitAccount() {
        //退出账号时清空本地数据
        clearDB();
        SharedPreferences sharedPreferences = MySharedPreferences.getFristActivityInstance();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("count", 0);
        editor.commit();
        //登出账号
        AVUser.getCurrentUser(User.class).logOut();
        Intent intent = new Intent(App.getInstance().getContext(), LoginActivity.class);
        getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_out);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    //退出账号时清空本地数据
    private void clearDB() {
        App.getDaoSession().deleteAll(LocalRecord.class);
        App.getDaoSession().deleteAll(LocalGroup.class);
    }

}

