package de.prttstft.materialmensa;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.Locale;

import de.prttstft.materialmensa.extras.UserSettings;

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

        JodaTimeAndroid.init(this);

        sInstance = this;

        setLanguage();
    }

    private void setLanguage() {
        Locale locale = new Locale(UserSettings.getLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, null);
    }
}