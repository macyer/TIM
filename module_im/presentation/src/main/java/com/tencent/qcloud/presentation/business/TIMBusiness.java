package com.tencent.qcloud.presentation.business;

import android.content.Context;
import android.util.Log;

import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.event.RefreshEvent;

import java.util.List;


/**
 * 初始化
 * 包括imsdk等
 */
public class TIMBusiness {

    private static final String TAG = TIMBusiness.class.getSimpleName();

    private TIMBusiness() {
    }

    public static void init() {
        //登录之前要初始化群和好友关系链缓存
        TIMUserConfig userConfig = new TIMUserConfig()
                //设置群组资料拉取字段
//                .setGroupSettings(initGroupSettings())
                //设置资料关系链拉取字段
//                .setFriendshipSettings(initFriendshipSettings())
                //设置用户状态变更事件监听器
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //被其他终端踢下线
                        Log.e(TAG, "===setUserStatusListener===" + "onForceOffline");
                    }

                    @Override
                    public void onUserSigExpired() {
                        //用户签名过期了，需要刷新 userSig 重新登录 SDK
                        Log.e(TAG, "===setUserStatusListener===" + "onUserSigExpired");
                    }
                })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        Log.e(TAG, "===setConnectionListener===" + "onConnected");
                    }

                    @Override
                    public void onDisconnected(int i, String s) {
                        Log.e(TAG, "===setConnectionListener===" + "onDisconnected" + i + "==" + s);
                    }

                    @Override
                    public void onWifiNeedAuth(String s) {
                        Log.e(TAG, "===setConnectionListener===" + "onWifiNeedAuth" + s);
                    }
                })
                //设置群组事件监听器
                .setGroupEventListener(new TIMGroupEventListener() {
                    @Override
                    public void onGroupTipsEvent(TIMGroupTipsElem elem) {
                        Log.e(TAG, "===setGroupEventListener===" + "onGroupTipsEvent, type: " + elem.getTipsType());
                    }
                })
                //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.e(TAG, "===setRefreshListener===" + "onRefresh");
                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> conversations) {
                        Log.e(TAG, "===setRefreshListener===" + "onRefreshConversation, conversation size: " + conversations.size());
                    }
                });

        //设置刷新监听
        RefreshEvent.getInstance().init(userConfig);
        //消息扩展用户配置   //开启消息已读回执   禁用消息存储
        //将用户配置与通讯管理器进行绑定
        userConfig = MessageEvent.getInstance().init(userConfig);
        //资料关系链扩展用户配置  
        userConfig = FriendshipEvent.getInstance().init(userConfig);
        //群组管理扩展用户配置
        userConfig = GroupEvent.getInstance().init(userConfig);
        //设置配置
        TIMManager.getInstance().setUserConfig(userConfig);
    }


}
