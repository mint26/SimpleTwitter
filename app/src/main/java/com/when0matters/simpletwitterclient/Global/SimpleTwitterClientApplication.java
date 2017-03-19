package com.when0matters.simpletwitterclient.Global;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.when0matters.simpletwitterclient.RestApi.SimpleTwitterRestClient;

/**
 * Created by dongdong on 3/13/2017.
 */

public class SimpleTwitterClientApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        // This instantiates DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
        // add for verbose logging
        // FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
    }

    public static SimpleTwitterRestClient getRestClient() {
        return (SimpleTwitterRestClient) SimpleTwitterRestClient.getInstance(SimpleTwitterRestClient.class, SimpleTwitterClientApplication.context);
    }
}
