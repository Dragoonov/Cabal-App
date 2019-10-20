package com.hobbymeetingapp.app.navigation_bar.search_events;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hobbymeetingapp.app.R;

public class EventCardsAdapter extends RecyclerView.Adapter<EventCardsAdapter.MyViewHolder> {
    private String[] mDataset;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public EventCardsAdapter(String[] myDataset) {
        mDataset = myDataset;
    }


    @NonNull
    @Override
    public EventCardsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {

        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(mDataset[position]);

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}