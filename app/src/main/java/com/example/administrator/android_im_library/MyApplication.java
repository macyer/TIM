package com.example.administrator.android_im_library;

import android.app.Application;
import android.os.StrictMode;

import com.tencent.qcloud.timchat.MyApp;


/**
 * 全局Application
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        MyApp.init(this);
    }


}
