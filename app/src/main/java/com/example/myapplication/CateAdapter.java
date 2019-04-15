package com.example.myapplication;

import android.os.Handler;
import android.os.Message;
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
public class CateAdapter extends RecyclerView.Adapter {
    private ArrayList<CateData> mList;
    private Handler mHandler;

    public CateAdapter(ArrayList<CateData> cateList, Handler mHandler) {
        mList = cateList;
        this.mHandler = mHandler;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cate, viewGroup, false);
        return new CateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        CateViewHolder holder = (CateViewHolder) viewHolder;

        final CateData data = mList.get(i);
        holder.cateText.setText(data.getTitle());

        if (data.isSelect()){
            holder.cateText.setBackgroundResource(R.color.colorAccent);
        } else {
            holder.cateText.setBackgroundResource(R.color.colorPrimary);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (CateData d: mList){
                    d.setSelect(false);
                }
                data.setSelect(true);
                notifyDataSetChanged();

                Message msg = new Message();
                msg.what = 1;
                msg.obj = data.getId();
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class CateViewHolder extends RecyclerView.ViewHolder{

        TextView cateText;

        public CateViewHolder(@NonNull View itemView) {
            super(itemView);
            cateText = itemView.findViewById(R.id.cate_text);
        }
    }
}
