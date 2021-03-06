package com.example.greatbook.main.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.App;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.local.activity.AddLocalRecordActivity;
import com.example.greatbook.local.fragment.MiddleMainFragment;
import com.example.greatbook.main.fragment.MyPrivateFragment;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.nethot.fragment.DiscoveryFragment;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.utils.anim.SpringAnimationInterpolar;
import com.example.greatbook.widght.DefaultNavigationBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MDve on 2017/8/15.
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_discovery)
    TextView tvDiscovery;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_talk_about)
    TextView tvTalkAbout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.menu_discovery)
    ImageView menuDiscovery;
    @BindView(R.id.menu_add)
    ImageView menuAdd;
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.menu_my)
    ImageView menuMy;
    private MiddleMainFragment localAddFragment;
    private DiscoveryFragment discoveryFragment;
    private MyPrivateFragment myPrivateFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_new;
    }

    @Override
    public void init() {
        new DefaultNavigationBar.Builder(this, null)
                .setTitleText("趣记")
                .setLeftResId(0)
                .builder();

        initActivity();
        localAddFragment = new MiddleMainFragment();
        discoveryFragment = new DiscoveryFragment();
        myPrivateFragment = new MyPrivateFragment();
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return discoveryFragment;
                } else if (position == 1) {
                    return localAddFragment;
                }
                return myPrivateFragment;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        selectTab1();
                        break;
                    case 1:
                        selectTab2();
                        break;
                    case 2:
                        selectTab3();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(1);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }


    @OnClick({R.id.menu_discovery, R.id.btn_add, R.id.menu_add, R.id.menu_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu_discovery:
                selectTab1();
                break;
            case R.id.btn_add:
                Intent toAdd = new Intent(App.getInstance().getContext(), AddLocalRecordActivity.class);
                startActivity(toAdd);
                break;
            case R.id.menu_add:
                selectTab2();
                break;
            case R.id.menu_my:
                selectTab3();
                break;
        }
    }

    private void selectTab3() {
        menuMy.setImageResource(R.drawable.menu_my_on);
        menuAdd.setImageResource(R.drawable.menu_add_off);
        menuDiscovery.setImageResource(R.drawable.menu_discovery_off);
        tvAdd.setTextColor(ContextCompat.getColor(this, R.color.gray));
        tvTalkAbout.setTextColor(ContextCompat.getColor(this, R.color.blue));
        tvDiscovery.setTextColor(ContextCompat.getColor(this, R.color.gray));

        menuAdd.setVisibility(View.VISIBLE);
        tvAdd.setVisibility(View.VISIBLE);
        btnAdd.setVisibility(View.GONE);

        viewPager.setCurrentItem(2);
    }

    private void selectTab2() {
        menuAdd.setVisibility(View.INVISIBLE);
        tvAdd.setVisibility(View.INVISIBLE);
        btnAdd.setVisibility(View.VISIBLE);

        menuDiscovery.setImageResource(R.drawable.menu_discovery_off);
        menuMy.setImageResource(R.drawable.menu_my_off);
        menuAdd.setImageResource(R.drawable.menu_add_off);
        tvDiscovery.setTextColor(ContextCompat.getColor(this, R.color.gray));
        tvAdd.setTextColor(ContextCompat.getColor(this, R.color.gray));
        tvTalkAbout.setTextColor(ContextCompat.getColor(this, R.color.gray));

        ScaleAnimation sa = new ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(250);
        sa.setInterpolator(new SpringAnimationInterpolar());
        btnAdd.startAnimation(sa);

        viewPager.setCurrentItem(1);
    }

    private void selectTab1() {
        menuDiscovery.setImageResource(R.drawable.menu_discovery_on);
        tvDiscovery.setTextColor(ContextCompat.getColor(this, R.color.blue));
        menuAdd.setImageResource(R.drawable.menu_add_off);
        tvAdd.setTextColor(ContextCompat.getColor(this, R.color.gray));
        menuMy.setImageResource(R.drawable.menu_my_off);
        tvTalkAbout.setTextColor(ContextCompat.getColor(this, R.color.gray));

        menuAdd.setVisibility(View.VISIBLE);
        tvAdd.setVisibility(View.VISIBLE);
        btnAdd.setVisibility(View.GONE);

        viewPager.setCurrentItem(0);
    }

    private void initActivity() {
        boolean isLogin = MySharedPreferences.isLogin();
        User user=AVUser.getCurrentUser(User.class);
        if (!isLogin || user == null) {
            Intent intent = new Intent(App.getInstance().getContext(), LoginActivity.class);
            overridePendingTransition(R.anim.login_in, R.anim.login_out);
            startActivity(intent);
            finish();
        }else{
            //上传一些数据到服务器
            user.setWords(MySharedPreferences.getCurWords());
            user.saveInBackground();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("主公，请留步:")
                .setMessage("您确定要退出么？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("再玩会", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
