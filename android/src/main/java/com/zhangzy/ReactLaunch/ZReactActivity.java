/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.zhangzy.ReactLaunch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.ViewGroup;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;

import javax.annotation.Nullable;


/**
 * Base Activity for React Native applications.
 */
public abstract class ZReactActivity extends Activity implements DefaultHardwareBackBtnHandler, PermissionAwareActivity {

    private final ZReactActivityDelegate mDelegate;

    protected ZReactActivity() {
        mDelegate = createReactActivityDelegate();
    }

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     * e.g. "MoviesApp"
     */
    protected @Nullable
    String getMainComponentName() {
        return null;
    }

    /**
     * Called at construction time, override if you have a custom delegate implementation.
     */
    protected ZReactActivityDelegate createReactActivityDelegate() {
        return new ZReactActivityDelegate(this, getMainComponentName());
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(this.getLayout());

        final Activity context = this;
        RNCacheViewManager.init(context, getMainComponentName(), ((ViewGroup) findViewById(android.R.id.content)));

        if (getGuideActivity() != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getGuideActivity() != null) {
                        Intent intent = new Intent();
                        intent.setClass(context, getGuideActivity());
                        startActivity(intent);
                    }
                }
            }, getWaitTime() * 1000 - 100);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mDelegate.onCreate(savedInstanceState);
            }
        }, getWaitTime() * 1000);
    }

    abstract public int getLayout();

    abstract public int getWaitTime();

    abstract public Class<? extends Activity> getGuideActivity();

    @Override
    protected void onPause() {
        super.onPause();
        mDelegate.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDelegate.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelegate.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mDelegate.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mDelegate.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (!mDelegate.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (!mDelegate.onNewIntent(intent)) {
            super.onNewIntent(intent);
        }
    }

    @Override
    public void requestPermissions(
        String[] permissions,
        int requestCode,
        PermissionListener listener) {
        mDelegate.requestPermissions(permissions, requestCode, listener);
    }

    @Override
    public void onRequestPermissionsResult(
        int requestCode,
        String[] permissions,
        int[] grantResults) {
        mDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected final ReactNativeHost getReactNativeHost() {
        return mDelegate.getReactNativeHost();
    }

    protected final ReactInstanceManager getReactInstanceManager() {
        return mDelegate.getReactInstanceManager();
    }

    protected final void loadApp(String appKey) {
        mDelegate.loadApp(appKey);
    }

}
