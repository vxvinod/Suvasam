package com.example.suvasam.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suvasam.R;
import com.example.suvasam.database.EventFirebase;
import com.example.suvasam.model.Events;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventListAdapter extends
        RecyclerView.Adapter<EventListAdapter.EventViewHolder>
{
    private final ArrayList<Events> mEventList;
    private LayoutInflater mInflater;
    class EventViewHolder extends RecyclerView.ViewHolder {

        public final TextView mEventName;
        public final ImageView mEventIv;
        public final TextView mEventDesc;
        public final TextView mEventDate;
        public final ImageView mFav;
        final EventListAdapter mAdapter;

        public EventViewHolder(@NonNull View itemView, EventListAdapter adapter) {
            super(itemView);
            mEventName = itemView.findViewById(R.id.eventName);
            mEventIv   = itemView.findViewById(R.id.eventImage);
            mEventDesc = itemView.findViewById(R.id.eventDesc);
            mEventDate = itemView.findViewById(R.id.eventDate);
            mFav = itemView.findViewById(R.id.eventIv);
            this.mAdapter = adapter;
        }
    }

    public EventListAdapter(Context context , ArrayList<Events> mEventList) {
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
    public void onBindViewHolder(@NonNull final EventListAdapter.EventViewHolder holder, final int position) {
        //String mCurrent = mEventList.get(position);
//        holder.itemView.setBackgroundColor();
        Log.d("POSITION", "-----"+position);
        final Events events = mEventList.get(position);
        Log.d("bindView", "Adapter --"+events.imageUrl);
        holder.mEventName.setText(events.name);
        holder.mEventDesc.setText(events.description);
        holder.mEventDate.setText(events.date);
        if(events.fav == true) {
            holder.mFav.setImageResource(R.drawable.ic_start_on);
        } else {
            holder.mFav.setImageResource(R.drawable.ic_start_off);
        }
        Picasso.get().load(events.imageUrl).resize(50,50).
                centerCrop().error(R.drawable.ic_launcher_background).into(holder.mEventIv);
        holder.mFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Fav Button Clicked", "favoutite added");
                boolean fav = (events.fav == true) ? false : true;
                updateFavInFirebase(position, fav);
                int imgSrc = (fav == true) ? R.drawable.ic_start_on : R.drawable.ic_start_off;
                holder.mFav.setImageResource(imgSrc);

            }
        });
    }

    public void updateFavInFirebase(int position, boolean fav) {
        EventFirebase.updateFavInEvents(position, fav);
        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }
}