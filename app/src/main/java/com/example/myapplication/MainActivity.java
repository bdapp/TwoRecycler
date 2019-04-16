package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    private RecyclerView _cateRecyclerView, _goodsRecyclerView;
    private ArrayList<CateData> cateList = new ArrayList<>();
    private ArrayList<GoodsData> goodsList = new ArrayList<>();
    private CateAdapter cateAdapter;
    private GoodsAdapter goodsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;

        // 模拟数据
        for (int i = 1; i <= 15; i++) {
            CateData data = new CateData();
            data.setId(i);
            data.setTitle("分类" + i);
            if (i == 1) {
                data.setSelect(true);
            }

            for (int j = 1; j <= 12; j++) {
                GoodsData d = new GoodsData();
                d.setId(i * 100 + j);
                d.setTitle("分类" + i+" - 名称" + i * 100 + j);
                d.setCate(i);
                goodsList.add(d);
            }

            cateList.add(data);
        }


        _cateRecyclerView = findViewById(R.id.cate_recycler_view);
        LinearLayoutManager manager1 = new LinearLayoutManager(mContext);
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        _cateRecyclerView.setLayoutManager(manager1);

        cateAdapter = new CateAdapter(cateList, mHandler);
        _cateRecyclerView.setAdapter(cateAdapter);


        _goodsRecyclerView = findViewById(R.id.goods_recycler_view);
        LinearLayoutManager manager2 = new LinearLayoutManager(mContext);
        manager2.setOrientation(LinearLayoutManager.VERTICAL);
        _goodsRecyclerView.setLayoutManager(manager2);

        goodsAdapter = new GoodsAdapter(goodsList);
        _goodsRecyclerView.setAdapter(goodsAdapter);


        _goodsRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 获取第一个可见item的位置
                int firstItem = manager.findFirstVisibleItemPosition();
                // 获取最后一个可见item的位置
                int lastItem = manager.findLastVisibleItemPosition();

                // 取出第一个item对应的分类id，然后更新分类adapter
                int id = goodsList.get(firstItem).getCate();
                for (CateData d : cateList) {
                    if (d.getId() == id) {
                        d.setSelect(true);
                    } else {
                        d.setSelect(false);
                    }
                }
                cateAdapter.notifyDataSetChanged();

                _cateRecyclerView.smoothScrollToPosition(id - 1);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    ScrollHandler mHandler = new ScrollHandler();

    class ScrollHandler extends Handler {

        private int catePosition;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    // 获取需要置顶的大类ID
                    int position = (int) msg.obj;

                    LinearLayoutManager manager = (LinearLayoutManager) _goodsRecyclerView.getLayoutManager();
                    int firstItem = manager.findFirstVisibleItemPosition();
                    int lastItem = manager.findLastVisibleItemPosition();

                    int visibleItemCount = lastItem - firstItem;

                    for (int i = 0; i < goodsList.size(); i++) {
                        if (goodsList.get(i).getCate() == position) {

                            if (position > catePosition) {
                                _goodsRecyclerView.scrollToPosition(i + visibleItemCount - 1);
                            } else {
                                _goodsRecyclerView.scrollToPosition(i);
                            }
                            catePosition = position;

                            break;
                        }
                    }





                    break;
            }
        }
    }


}
