package com.alerts.pj.trafficalerts;

import android.app.IntentService;
import android.content.Intent;

public class RSSPullService extends IntentService {
    public RSSPullService() {
        super(RSSPullService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
      System.out.println("in back");
    }
}

