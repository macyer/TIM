package com.tencent.qcloud.tlslibrary.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendAllowType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushSettings;
import com.tencent.qcloud.tlslibrary.activity.ImgCodeActivity;
import com.tencent.qcloud.tlslibrary.helper.Util;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSPwdLoginListener;
import tencent.tls.platform.TLSUserInfo;

/**
 * Created by dgy on 15/8/12.
 */
public class AccountLoginServiceString {

    private final static String TAG = "AccountLoginString";

    private Context context;

    private TLSService tlsService;
    public static PwdLoginListener pwdLoginListener;


    public AccountLoginServiceString(Context context,
                                     final String txt_username,
                                     final String txt_password) {
        this.context = context;

        tlsService = TLSService.getInstance();
        pwdLoginListener = new PwdLoginListener();
        // 验证用户名和密码的有效性
        if (txt_username.length() == 0 || txt_password.length() == 0) {
            Util.showToast(AccountLoginServiceString.this.context, "用户名密码不能为空");
            return;
        }
        tlsService.TLSPwdLogin(txt_username, txt_password, pwdLoginListener);
    }

    class PwdLoginListener implements TLSPwdLoginListener {
        @Override
        public void OnPwdLoginSuccess(TLSUserInfo userInfo) {
            Util.showToast(context, "登录成功");
            Log.e(TAG, "=======登录成功");
            initIM(context);
//            TLSService.getInstance().setLastErrno(0);
//            AccountLoginServiceTXT.this.jumpToSuccActivity();
        }

        @Override
        public void OnPwdLoginReaskImgcodeSuccess(byte[] picData) {
            ImgCodeActivity.fillImageview(picData);
        }

        @Override
        public void OnPwdLoginNeedImgcode(byte[] picData, TLSErrInfo errInfo) {
            Intent intent = new Intent(context, ImgCodeActivity.class);
            intent.putExtra(Constants.EXTRA_IMG_CHECKCODE, picData);
            intent.putExtra(Constants.EXTRA_LOGIN_WAY, Constants.USRPWD_LOGIN);
            context.startActivity(intent);
        }

        @Override
        public void OnPwdLoginFail(TLSErrInfo errInfo) {
            TLSService.getInstance().setLastErrno(-1);
            Util.notOK(context, errInfo);
        }

        @Override
        public void OnPwdLoginTimeout(TLSErrInfo errInfo) {
            TLSService.getInstance().setLastErrno(-1);
            Util.notOK(context, errInfo);
        }
    }

    void jumpToSuccActivity() {
        Log.d(TAG, "jumpToSuccActivity");
        String thirdappPackageNameSucc = Constants.thirdappPackageNameSucc;
        String thirdappClassNameSucc = Constants.thirdappClassNameSucc;

        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_LOGIN_WAY, Constants.USRPWD_LOGIN);
        intent.putExtra(Constants.EXTRA_USRPWD_LOGIN, Constants.USRPWD_LOGIN_SUCCESS);
        if (thirdappPackageNameSucc != null && thirdappClassNameSucc != null) {
            intent.setClassName(thirdappPackageNameSucc, thirdappClassNameSucc);
            context.startActivity(intent);
        } else {
            Log.d(TAG, "finish current activity");
            ((Activity) context).setResult(Activity.RESULT_OK, intent);
            ((Activity) context).finish();
        }
    }


    public static void initIM(Context context) {
//        //初始化程序后台后消息推送
//        PushUtil.getInstance();
//        //初始化消息监听
//        MessageEvent.getInstance();
//        ToastUtils.showMessage(context,"TIM login success");
//        String deviceMan = android.os.Build.MANUFACTURER;
        //注册小米和华为推送 //
      /*  if (deviceMan.equals("Xiaomi") && shouldMiInit()){
            MiPushClient.registerPush(getApplicationContext(), Constants.MI_APP_ID, Constants.MI_APP_KEY);
        }else if (deviceMan.equals("HUAWEI")){
            PushManager.requestToken(this);
        }*/
        Log.e(TAG, "onSuccess: " + TIMManager.getInstance().getLoginUser());
        //设置自己的好友验证方式为需要验证
        TIMFriendshipManager.ModifyUserProfileParam param = new TIMFriendshipManager.ModifyUserProfileParam();
        param.setAllowType(TIMFriendAllowType.TIM_FRIEND_NEED_CONFIRM);
        TIMFriendshipManager.getInstance().modifyProfile(param, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Log.e(TAG, "modifyProfile failed: " + code + " desc" + desc);
            }

            @Override
            public void onSuccess() {
                Log.e(TAG, "设置好友验证 succ");
            }
        });
        TIMOfflinePushSettings settings = new TIMOfflinePushSettings();
//开启离线推送
        settings.setEnabled(true);
//设置收到C2C离线消息时的提示声音，这里把声音文件放到了res/raw文件夹下
        settings.setC2cMsgRemindSound(null);

        TIMManager.getInstance().setOfflinePushSettings(settings);


    }
}
