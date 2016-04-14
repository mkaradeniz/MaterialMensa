package de.prttstft.materialmensa;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;


public class MyApplication extends Application {
    private static MyApplication sInstance;

    public static MyApplication getsInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        // Initialize JodaTime
        JodaTimeAndroid.init(this);

    }
}