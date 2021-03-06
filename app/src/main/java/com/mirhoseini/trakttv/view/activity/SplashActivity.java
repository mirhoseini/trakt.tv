package com.mirhoseini.trakttv.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.di.component.ApplicationComponent;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Mohsen on 19/07/16.
 */

public class SplashActivity extends BaseActivity {

    public static final int SPLASH_TIMEOUT = 5000;

    @Inject
    Context context;

    // The thread to process splash screen events
    private Thread splashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // The Theme's windowBackground is masked by the opaque background of the activity, and
        // the windowBackground causes an unnecessary overdraw. Nullifying the windowBackground
        // removes that overdraw.
        getWindow().setBackgroundDrawable(null);

        // The thread to wait for splash screen events
        splashThread = new Thread() {
            @Override
            public void run() {

                try {
                    synchronized (this) {
                        // Wait given period of time or exit on touch
                        wait(SPLASH_TIMEOUT);
                    }
                } catch (InterruptedException ex) {
                    Timber.e(ex, "Splash Interrupted");
                }

                finish();

                // Open MainActivity
                Intent mainActivityIntent = new Intent();
                mainActivityIntent.setClass(context, MainActivity.class);
                startActivity(mainActivityIntent);
            }
        };

        splashThread.start();
    }

    // Listening whole activity touch events
    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        if (evt.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (splashThread) {
                splashThread.notifyAll();
            }
        }

        return true;
    }

    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component.inject(this);
    }
}
