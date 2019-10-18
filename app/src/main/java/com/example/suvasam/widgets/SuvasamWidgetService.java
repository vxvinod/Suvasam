package com.example.suvasam.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class SuvasamWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this, intent);
    }
}
