package com.tencent.qcloud.timchat;

import android.app.Application;
import android.content.Context;

import com.tencent.imsdk.TIMGroupReceiveMessageOpt;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushListener;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.tencent.qcloud.timchat.utils.Foreground;


/**
 * 全局Application
 */
public class MyApp {

    private static Context mContext;

    public static void init(Context context) {
        Foreground.init((Application) context);
        mContext = context;
        if(MsfSdkUtils.isMainProcess(mContext)) {
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify){
                        //消息被设置为需要提醒
                        notification.doNotify(mContext, R.mipmap.ic_launcher);
                    }
                }
            });
        }
    }

    public static Context getContext() {
        return mContext;
    }



}
