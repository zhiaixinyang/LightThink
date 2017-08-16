package com.example.greatbook.middle.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.LazyFragment;
import com.example.greatbook.middle.activity.AddLocalRecordActivity;
import com.example.greatbook.ui.main.fragment.MyPrivateFragment;
import com.example.greatbook.utils.anim.SpringAnimationInterpolar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MDove on 2017/8/11.
 */

public class MiddleMainFragment extends LazyFragment {
    @BindView(R.id.tv_discovery)
    TextView tvDiscovery;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_talk_about)
    TextView tvTalkAbout;
    @BindView(R.id.content)
    ViewPager viewPager;
    @BindView(R.id.menu_discovery)
    ImageView menuDiscovery;
    @BindView(R.id.menu_add)
    ImageView menuAdd;
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.menu_my)
    ImageView menuTalk;
    private Context context;

    public static MiddleMainFragment newInstance() {
        Bundle args = new Bundle();

        MiddleMainFragment fragment = new MiddleMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private MiddleLocalAddFragment localAddFragment;
    private MiddleDiscoveryFragment discoveryFragment;
    private MyPrivateFragment myPrivateFragment;

    @Override
    protected void onVisible() {
        viewPager.setCurrentItem(1);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_middle_main;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        context=App.getInstance().getContext();
        localAddFragment = MiddleLocalAddFragment.newInstance();
        discoveryFragment = MiddleDiscoveryFragment.newInstance();
        myPrivateFragment = MyPrivateFragment.newInstance();
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
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
    }

    @OnClick({R.id.menu_discovery, R.id.menu_add, R.id.btn_add, R.id.menu_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu_discovery:
                selectTab1();
                break;
            case R.id.menu_add:
                selectTab2();
                break;
            case R.id.menu_my:
                selectTab3();
                break;
            case R.id.btn_add:
                Intent toAdd=new Intent(App.getInstance().getContext(), AddLocalRecordActivity.class);
                startActivity(toAdd);
                break;
        }
    }

    private void selectTab3() {
        menuTalk.setImageResource(R.drawable.menu_talk_about_on);
        menuAdd.setImageResource(R.drawable.menu_add_off);
        menuDiscovery.setImageResource(R.drawable.menu_discovery_off);
        tvAdd.setTextColor(ContextCompat.getColor(context,R.color.gray));
        tvTalkAbout.setTextColor(ContextCompat.getColor(context,R.color.blue));
        tvDiscovery.setTextColor(ContextCompat.getColor(context,R.color.gray));

        menuAdd.setVisibility(View.VISIBLE);
        tvAdd.setVisibility(View.VISIBLE);
        btnAdd.setVisibility(View.GONE);
        viewPager.setCurrentItem(2);
    }

    private void selectTab2() {
        menuDiscovery.setImageResource(R.drawable.menu_discovery_off);
        menuTalk.setImageResource(R.drawable.menu_talk_about_off);
        menuAdd.setImageResource(R.drawable.menu_add_off);
        tvDiscovery.setTextColor(ContextCompat.getColor(context,R.color.gray));
        tvAdd.setTextColor(ContextCompat.getColor(context,R.color.gray));
        tvTalkAbout.setTextColor(ContextCompat.getColor(context,R.color.gray));


        menuAdd.setVisibility(View.GONE);
        tvAdd.setVisibility(View.GONE);
        btnAdd.setVisibility(View.VISIBLE);

        ScaleAnimation sa = new ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(250);
        sa.setInterpolator(new SpringAnimationInterpolar());
        btnAdd.startAnimation(sa);

        viewPager.setCurrentItem(1);
    }

    private void selectTab1() {
        menuDiscovery.setImageResource(R.drawable.menu_discovery_on);
        tvDiscovery.setTextColor(ContextCompat.getColor(context,R.color.blue));
        menuAdd.setImageResource(R.drawable.menu_add_off);
        tvAdd.setTextColor(ContextCompat.getColor(context,R.color.gray));
        menuTalk.setImageResource(R.drawable.menu_talk_about_off);
        tvTalkAbout.setTextColor(ContextCompat.getColor(context,R.color.gray));

        menuAdd.setVisibility(View.VISIBLE);
        tvAdd.setVisibility(View.VISIBLE);
        btnAdd.setVisibility(View.GONE);
        viewPager.setCurrentItem(0);
    }

}
