package com.tencent.qcloud.presentation.event;


import android.util.Log;

import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.ext.message.TIMMessageLocator;
import com.tencent.imsdk.ext.message.TIMMessageRevokedListener;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;

import java.util.List;
import java.util.Observable;

/**
 * 消息通知事件，上层界面可以订阅此事件
 */
public class MessageEvent extends Observable implements TIMMessageListener, TIMMessageRevokedListener {


    private volatile static MessageEvent instance;

    private MessageEvent() {
        //注册消息监听器
        TIMManager.getInstance().addMessageListener(this);


    }

    public TIMUserConfig init(TIMUserConfig config) {
        TIMUserConfigMsgExt ext = new TIMUserConfigMsgExt(config);
        ext.enableAutoReport(false)
                //禁用消息存储
                .enableStorage(false)
                //开启消息已读回执
                .enableReadReceipt(true)
                .setMessageRevokedListener(this);
        return ext;
    }

    public static MessageEvent getInstance() {
        if (instance == null) {
            synchronized (MessageEvent.class) {
                if (instance == null) {
                    instance = new MessageEvent();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean onNewMessages(List<TIMMessage> list) {
        for (TIMMessage item : list) {
            setChanged();
            notifyObservers(item);
            processMessage(item);
        }
        return false;
    }

    private void processMessage(TIMMessage message) {
        Log.e("===", "===addMessageListener===" + message.getElement(0).getType() + "==" + message.getElement(0).toString());
        switch (message.getElement(0).getType()) {
            case Text:
            case Face:
//                TIMTextElem timTextElem = (TIMTextElem) message.getElement(0);
//                Log.e("===", "====" + timTextElem.getText());
                break;
            case Image:
                break;
            case Sound:
                break;
            case Video:
                break;
            case GroupTips:
                break;
            case File:
                break;
            case Custom:
//                TIMCustomElem timCustomElem = (TIMCustomElem) message.getElement(0);
//                Log.e("===", "====" + timCustomElem.getDesc() + new String(timCustomElem.getData()) + new String(timCustomElem.getExt()));
                break;
            case UGC:
                break;
        }
    }

    /**
     * 主动通知新消息
     */
    public void onNewMessage(TIMMessage message) {
        setChanged();
        notifyObservers(message);
    }

    /**
     * 清理消息监听
     */
    public void clear() {
        instance = null;
    }

    @Override
    public void onMessageRevoked(TIMMessageLocator timMessageLocator) {
        setChanged();
        notifyObservers(timMessageLocator);
    }
}
