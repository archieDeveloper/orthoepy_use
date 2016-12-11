package com.shahbazly_dev.orthoepy_use.Activies;

import com.facebook.stetho.Stetho;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
