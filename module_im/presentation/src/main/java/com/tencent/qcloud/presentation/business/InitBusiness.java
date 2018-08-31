package com.tencent.qcloud.presentation.business;

import android.content.Context;
import android.util.Log;

import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMLogListener;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.qcloud.sdk.Constant;


/**
 * 初始化
 * 包括imsdk等
 */
public class InitBusiness {

    private static final String TAG = InitBusiness.class.getSimpleName();

    private InitBusiness(){}

    public static void start(Context context){
        initImsdk(context, 0);
    }

    public static void start(Context context, int logLevel){
        initImsdk(context, logLevel);
    }


    /**
     * 初始化imsdk
     */
    private static void initImsdk(Context context, int logLevel){
        TIMSdkConfig config = new TIMSdkConfig(Constant.SDK_APPID).enableLogPrint(true)
                .enableCrashReport(true)
                .setLogLevel(TIMLogLevel.values()[logLevel])
                .setLogListener(new TIMLogListener() {
                    @Override
                    public void log(int level, String tag, String msg) {
                        //可以通过此回调将 sdk 的 log 输出到自己的日志系统中
                    }
                });
        //初始化imsdk
        TIMManager.getInstance().init(context, config);
        //禁止服务器自动代替上报已读
        Log.e(TAG, "=============initIMsdk");

    }





}
