package com.example.suvasam.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.suvasam.R;
import com.example.suvasam.model.Awareness;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class AwarenessViewPagerAdapter extends RecyclerView.Adapter<AwarenessViewPagerAdapter.ViewHolder> {
    private ArrayList<Awareness> mData;
    private LayoutInflater mInflater;
    private ViewPager2 viewPager2;
    private Context mContext;


    private int[] colorArray = new int[]{android.R.color.black, android.R.color.holo_blue_dark, android.R.color.holo_green_dark, android.R.color.holo_red_dark};


    public AwarenessViewPagerAdapter(Context context, ArrayList<Awareness> data, ViewPager2 viewPager2) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_viewpager , parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Awareness awarenessData = mData.get(position);
        holder.title.setTitle(awarenessData.getTitle());
        holder.desc.setText(awarenessData.getDescription());

        Picasso.get().load(awarenessData.getImageUrl()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.awarenessIv.setBackground(new BitmapDrawable(mContext.getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        holder.awarenessIv.setContentDescription(awarenessData.getTitle()+" ImageUrl "+
                                                                    awarenessData.getImageUrl());
      //  holder.relativeLayout.setBackgroundResource(awarenessData.get);

        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, awarenessData.getDescription());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, awarenessData.getTitle());
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CollapsingToolbarLayout title;
        ImageView awarenessIv;
        TextView desc;
        RelativeLayout relativeLayout;
        FloatingActionButton shareBtn;

        ViewHolder(View itemView) {
            super(itemView);
           // myTextView = itemView.findViewById(R.id.tvTitle);
            relativeLayout = itemView.findViewById(R.id.container);
            title = itemView.findViewById(R.id.collapsingToolbar);
            awarenessIv = itemView.findViewById(R.id.toolbarImage);
            desc = itemView.findViewById(R.id.awarenessDesc);
            shareBtn = itemView.findViewById(R.id.fabShare);

        }
    }
}
