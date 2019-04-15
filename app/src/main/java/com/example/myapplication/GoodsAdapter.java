package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @Info
 * @Auth Bello
 * @Time 2019/4/15 23:39
 * @Ver
 */
public class GoodsAdapter extends RecyclerView.Adapter {
    private ArrayList<GoodsData> mList;

    public GoodsAdapter(ArrayList<GoodsData> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_goods, viewGroup, false);
        return new GoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        GoodsViewHolder holder = (GoodsViewHolder) viewHolder;
        holder.goodsText.setText(mList.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder{

        TextView goodsText;

        public GoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsText = itemView.findViewById(R.id.goods_text);
        }
    }
}
