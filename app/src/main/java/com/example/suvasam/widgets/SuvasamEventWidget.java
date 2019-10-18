package com.example.suvasam.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.suvasam.MainActivity;
import com.example.suvasam.R;
import com.example.suvasam.model.Events;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class SuvasamEventWidget extends AppWidgetProvider {

    public static ArrayList<Events> mInterestedEvents;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//        CharSequence widgetText = context.getString(R.string.appwidget_text);
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.suvasam_event_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
        Intent intent = new Intent(context, SuvasamWidgetService.class);
        intent.setAction(WidgetUpdateIntentService.ACTION_UPDATE_WIDGET);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.suvasam_event_widget);

        remoteViews.setRemoteAdapter(R.id.widgetListView, intent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetListView);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, SuvasamEventWidget.class));

        if(intent.getAction() == AppWidgetManager.ACTION_APPWIDGET_UPDATE){
            mInterestedEvents = intent.getParcelableArrayListExtra("interestedEvents");
            if(mInterestedEvents!=null) {
                for (int appWidgetId : appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId);
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetListView);
                }
            }
        }
        super.onReceive(context, intent);
    }
}

