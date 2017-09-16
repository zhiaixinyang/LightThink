package com.example.ywb.recyclerviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/25.
 */
public class WaterFallActivity extends Activity {

    private RecyclerView recyclerView;
    private List<String> datas;
    private WaterFallAdapter waterFallAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_fall);
        initDatas();
        initView();
    }

    private void initDatas(){
        datas = new ArrayList<>();
        for (int i = 'A';i<'z';i++){
            datas.add(""+(char)i);
        }
    }

    private void initView(){
        recyclerView = (RecyclerView) findViewById(R.id.waterfall_recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        waterFallAdapter = new WaterFallAdapter(this,datas);
        recyclerView.setAdapter(waterFallAdapter);
    }
}
