package com.atguigu.beijingnews.detailpager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.atguigu.baselibrary.Constants;
import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.adapter.PhotosMenuDetailPagerAdapter;
import com.atguigu.beijingnews.base.MenuDetailBasePager;
import com.atguigu.beijingnews.bean.NewsCenterBean;
import com.atguigu.beijingnews.bean.PhotosMenuDetailPagerBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by Baby on 2017/2/6.
 */

public class PhotosMenuDetailPager extends MenuDetailBasePager {

    private final NewsCenterBean.DataBean dataBean;
    private PhotosMenuDetailPagerAdapter adapter;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    private String url;

    public PhotosMenuDetailPager(Context context, NewsCenterBean.DataBean dataBean) {
        super(context);
        this.dataBean = dataBean;
    }

    @Override
    public View initView() {
        //图组详情页面的视图
        View view = View.inflate(mContext, R.layout.photos_menudetail_pager, null);
        ButterKnife.inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        url = Constants.BASE_URL+dataBean.getUrl();
        Log.e("TAG","图组的网络地址========"+url);
        getDataFromNet(url);
    }

    private void getDataFromNet(String url) {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG","图组数据请求成功=====");
                processData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG","图组数据请求失败====="+ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String json) {
        PhotosMenuDetailPagerBean bean = new Gson().fromJson(json,PhotosMenuDetailPagerBean.class);
        Log.e("TAG","数组解析数据成功==="+ bean.getData().getNews().get(0).getTitle());

        //设置RecycerView的适配器
        adapter = new PhotosMenuDetailPagerAdapter(mContext,bean.getData().getNews());
        recyclerview.setAdapter(adapter);

        //设置布局管理器
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));

    }
}
