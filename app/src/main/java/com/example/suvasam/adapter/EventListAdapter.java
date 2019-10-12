package com.example.suvasam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suvasam.R;

import java.util.LinkedList;

public class EventListAdapter extends
        RecyclerView.Adapter<EventListAdapter.EventViewHolder>
{
    private final LinkedList<String> mEventList;
    private LayoutInflater mInflater;
    class EventViewHolder extends RecyclerView.ViewHolder {

        public final TextView eventName;
        final EventListAdapter mAdapter;

        public EventViewHolder(@NonNull View itemView, EventListAdapter adapter) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventName);
            this.mAdapter = adapter;
        }
    }

    public EventListAdapter(Context context , LinkedList<String> mEventList) {
        mInflater = LayoutInflater.from(context);
        this.mEventList = mEventList;
    }

    @NonNull
    @Override
    public EventListAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mIemView = mInflater.inflate(R.layout.eventlist_item, parent, false);
        return new EventViewHolder(mIemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull EventListAdapter.EventViewHolder holder, int position) {
        String mCurrent = mEventList.get(position);
        holder.eventName.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }
}
