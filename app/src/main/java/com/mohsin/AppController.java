package com.mohsin;

/**
 * Created by Admin on 19-11-2014.
 */

import android.app.Application;
import android.content.Context;

public class AppController extends Application {

    public static int indexOfVideoToBePlayed = 0;
    public static final String TAG = AppController.class.getSimpleName();
    public static int currentlySelectedPopularCategoriesPage = 0;
    public static boolean pauseVideoAndShowImageOnlyOnce = false;
    public static boolean scrollStarted = false;
    public static boolean scrollEnded = true;

    private static AppController mInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }
}