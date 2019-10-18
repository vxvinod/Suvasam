package com.example.suvasam.widgets;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.suvasam.R;
import com.example.suvasam.database.EventFirebase;
import com.example.suvasam.model.Events;

import java.util.ArrayList;

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    Intent intent;
    ArrayList<Events> mInterestedEvents = new ArrayList<>();
    public WidgetDataProvider(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        this.intent = intent;
    }

    @Override
    public void onCreate() {
        Log.e("WIDGET VIEW", "CREATE");
       // mInterestedEvents = EventFirebase.fetchDataFromFirebase();
    }

    @Override
    public void onDataSetChanged() {
        Log.e("WIDGET VIEW", "ON DATA SET CHANGED");
        mInterestedEvents = EventFirebase.fetchDataFromFirebase();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mInterestedEvents == null) return 0;
        return mInterestedEvents.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(mInterestedEvents == null) { return null; }
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.collection_widget_list_item);
        String events = mInterestedEvents.get(position).getName()+" "+mInterestedEvents.get(position).getDate();
        rv.setTextViewText(R.id.widgetEventList, events);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
