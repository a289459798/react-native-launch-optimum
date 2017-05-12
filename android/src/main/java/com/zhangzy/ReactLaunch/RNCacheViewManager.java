package com.zhangzy.ReactLaunch;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactRootView;

/**
 * Created by zzy on 2017/4/27.
 * Date : 2017/4/27 11:31
 */
public class RNCacheViewManager {
    private static ReactRootView mRootView = null;
    private static ReactInstanceManager mManager = null;

    public static ReactRootView getmRootView() {

        if (mRootView == null) {
            return null;
        }
        ViewParent viewParent = mRootView.getParent();
        if (viewParent != null) {
            ViewGroup vp = (ViewGroup) viewParent;
            vp.removeView(mRootView);
        }
        return mRootView;
    }

    protected static ReactNativeHost getReactNativeHost(Activity activity) {
        return ((ReactApplication) activity.getApplication()).getReactNativeHost();
    }

    public static void init(Activity act, String appkey, ViewGroup view) {
        if (mManager == null) {
            mManager = getReactNativeHost(act).getReactInstanceManager();
        }
        mRootView = new ReactRootView(act);
        mRootView.startReactApplication(mManager, appkey, null);

        if (mRootView == null || view == null)
            return;
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(1, 1);
        view.addView(mRootView, params);
    }

    public static void onDestroy() {
        try {
            ViewParent parent = getmRootView().getParent();
            if (parent != null)
                ((ViewGroup) parent).removeView(getmRootView());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
