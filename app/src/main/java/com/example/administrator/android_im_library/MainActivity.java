package com.example.administrator.android_im_library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMUser;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.business.TIMBusiness;
import com.tencent.qcloud.sdk.Constant;
import com.tencent.qcloud.timchat.ui.ChatActivity;
import com.tencent.qcloud.timchat.utils.PushUtil;
import com.tencent.qcloud.tlslibrary.service.TLSService;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSPwdLoginListener;
import tencent.tls.platform.TLSUserInfo;

public class MainActivity extends AppCompatActivity {

    private TLSService tlsService;

    @Override
    protected void onResume() {
        super.onResume();
        PushUtil.getInstance().reset();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.e("=========="+ TIMManager.getInstance().getVersion());
        //初始化IMSDK
        InitBusiness.start(getApplicationContext(), 1);
        //初始化TLS
        TlsBusiness.init(getApplicationContext());
        TIMBusiness.init();//初始化TIMUserConfig
//        if (UserInfo.getInstance().getId()!= null && (!TLSService.getInstance().needLogin(UserInfo.getInstance().getId()))) {
//            navToHome();//跳转到登录界面
//        } else {
        navToLogin();//跳转到登录界面
//        }
    }

    String messi1 = "messi1";
    String user = "messi";
    String password = "hifa1111";
    String userSig = "eJx1kM1Og0AURvc8xWS2GsuPU6i7gopYwGKtsasJZS52QqHTmWmKGN9dgiay8W7PSc6X*2kghPBLvLrKi*JwajTVHwIwukHYndn48g8LwRnNNXUkG7B1bfZnEccZWdAKLoHmpQY5WDaZ2b02UjiDRvOS-wo1KMWtEVesokPs-4ri7wNM7jZBlAUN2Z*9ped2aWBu148nBse0itXyArrGd0QbZdt9klVPOz*LdvO42EyeH*T9ItRJKF-D48GUt8Xc87solx5bgZsuJmX7Vuv1KKl5-fOVfgqZWlNCTGx8Gd-wSVfP";


    private void navToLogin() {
        tlsService = TLSService.getInstance();
        tlsService.TLSPwdLogin(messi1, password, new TLSPwdLoginListener() {
            @Override
            public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {
                LogUtil.e("===loginTLS===" + "loginTLS succ===" + tlsService.getUserSig(messi1));
                TIMUser user1 = new TIMUser();
                user1.setIdentifier(messi1);
                user1.setSdkAppid(Constant.SDK_APPID);
//                LoginBusiness.loginIm(messi1, userSig, this);
                navToHome();
            }

            @Override
            public void OnPwdLoginReaskImgcodeSuccess(byte[] bytes) {

            }

            @Override
            public void OnPwdLoginNeedImgcode(byte[] bytes, TLSErrInfo tlsErrInfo) {

            }

            @Override
            public void OnPwdLoginFail(TLSErrInfo tlsErrInfo) {
                LogUtil.e("===loginTLS===" + "loginTLS failed. code: " + tlsErrInfo.Title + tlsErrInfo.ExtraMsg + tlsErrInfo.Msg + tlsErrInfo.ErrCode);
            }

            @Override
            public void OnPwdLoginTimeout(TLSErrInfo tlsErrInfo) {

            }
        });
    }

    private void navToHome() {
//        LoginBusiness.loginIm(UserInfo.getInstance().getId(), UserInfo.getInstance().getUserSig(), this);
        LoginBusiness.loginIm(messi1, userSig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                LogUtil.e("===loginTLS===" + "loginTLS failed. code: " + code + " errmsg: " + desc);
                switch (code) {
                    case 6208:
                        //离线状态下被其他终端踢下线
//                        NotifyDialog dialog = new NotifyDialog();
//                        dialog.show(getString(R.string.kick_logout), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                navToHome();
//                            }
//                        });
                        break;
                    case 6200:
//                        Toast.makeText(this,getString(R.string.login_error_timeout),Toast.LENGTH_SHORT).show();
//                        navToLogin();
                        break;
                    default:
//                        Toast.makeText(this,getString(R.string.login_error),Toast.LENGTH_SHORT).show();
//                        navToLogin();
                        break;
                }
            }

            @Override
            public void onSuccess() {
                LogUtil.e("===loginTLS===" + "loginTLS succ");
                ChatActivity.navToChat(MainActivity.this, user, TIMConversationType.C2C);
            }
        });
    }
}
