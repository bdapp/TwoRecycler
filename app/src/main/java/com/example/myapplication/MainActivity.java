package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    private RecyclerView _cateRecyclerView, _goodsRecyclerView;
    private ArrayList<CateData> cateList = new ArrayList<>();
    private ArrayList<GoodsData> goodsList = new ArrayList<>();
    private CateAdapter cateAdapter;
    private GoodsAdapter goodsAdapter;

    private int goodsVisiableCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;


        for (int i = 1; i <= 15; i++) {
            CateData data = new CateData();
            data.setId(i);
            data.setTitle("分类" + i);

            for (int j = 1; j <= 20; j++) {
                GoodsData d = new GoodsData();
                d.setId(i*100 + j);
                d.setTitle("商品名称" + i*100 + j);
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

                goodsVisiableCount = lastItem - firstItem + 1;

                Log.e(TAG,
                        "count: " + goodsVisiableCount +", "+goodsList.get(firstItem).getTitle() + ", " + goodsList.get(lastItem).getTitle());

                // 取出第一个item对应的分类id，然后更新分类adapter
                int id = goodsList.get(firstItem).getCate();
                for (CateData d: cateList){
                    if (d.getId() == id){
                        d.setSelect(true);
                    } else {
                        d.setSelect(false);
                    }
                }
                cateAdapter.notifyDataSetChanged();

                _cateRecyclerView.smoothScrollToPosition(id-1);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    ScrollHandler mHandler = new ScrollHandler();
    class ScrollHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    int catePosition = (int) msg.obj;
                    for (int i=0; i<goodsList.size(); i++){
                        if (goodsList.get(i).getCate() == catePosition){
//                            _goodsRecyclerView.scrollToPosition(i);
                            smoothMoveToPosition(_goodsRecyclerView, i);
                            return;
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));

        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.scrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.scrollBy(0, top);
            }
        } else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.scrollToPosition(position);
        }
    }
}
