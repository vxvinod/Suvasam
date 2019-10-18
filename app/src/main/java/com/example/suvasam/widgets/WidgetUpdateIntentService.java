package com.example.suvasam.widgets;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.example.suvasam.model.Events;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;

public class WidgetUpdateIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public static final String ACTION_UPDATE_WIDGET = "com.example.suvasam.action.update_widget";
    public WidgetUpdateIntentService(String name) {
        super(name);
    }

    public static void startAddWidgetData(Context context, ArrayList<Events> events) {
        try {
            Intent intent = new Intent(context, SuvasamWidgetService.class);
            intent.setAction(ACTION_UPDATE_WIDGET);
            intent.putParcelableArrayListExtra("ingredients", events);
            context.startService(intent);
        } catch (Exception e) {
            Log.e(TAG, "Exception starting service", e);
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            final String action = intent.getAction();
            if(ACTION_UPDATE_WIDGET.equals(action)) {
                ArrayList<Events> interestedEvents = intent.getParcelableArrayListExtra("interestedEvents");
                handleClick(interestedEvents);
            }
        }
    }

    private void handleClick(ArrayList<Events> interestedEvents) {
        if(interestedEvents != null) {
            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putParcelableArrayListExtra("interestedEvents", interestedEvents);
            sendBroadcast(intent);
        }
    }
}
