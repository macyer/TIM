package com.example.administrator.android_im_library;

import android.text.TextUtils;
import android.util.Log;


public class LogUtil {
    private static final String TAG = "LV";
    public static final boolean LOG_DEBUG = true;

    public LogUtil() {
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (LOG_DEBUG && !TextUtils.isEmpty(msg))
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (LOG_DEBUG && !TextUtils.isEmpty(msg))
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (LOG_DEBUG && !TextUtils.isEmpty(msg))
            Log.e(TAG, "===IMSDK==="+ msg);
    }

    public static void e(Throwable throwable) {
        if (throwable != null) {
            Log.e(TAG, "===========getLocalizedMessage=="
                    + throwable.getLocalizedMessage()
                    + "\n===========getMessage=="
                    + throwable.getMessage());
            getStacks(throwable);
        }

    }

    private static void getStacks(Throwable throwable) {
        StackTraceElement[] elements = throwable.getStackTrace();
        for (StackTraceElement element : elements) {
            Log.e(TAG, "\n===========getStackTrace==" + element.getFileName() + "==" + element.getClassName() + "==" + element.getMethodName() + "==" + element.getLineNumber());
        }
    }

    public static void dClass(String msg) {
        if (LOG_DEBUG && !TextUtils.isEmpty(msg)) {
            String clazzName = new Throwable().getStackTrace()[1].getClassName();
            String[] clazzNames = clazzName.split("\\.");
            try {
                Log.d(clazzNames[clazzNames.length - 1], msg);
            } catch (RuntimeException e) {
                System.out.println(msg);
            }
        }
    }


    public static void v(String msg) {
        if (LOG_DEBUG && !TextUtils.isEmpty(msg))
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (LOG_DEBUG &&!TextUtils.isEmpty(msg))
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (LOG_DEBUG && !TextUtils.isEmpty(msg))
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (LOG_DEBUG && !TextUtils.isEmpty(msg))
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (LOG_DEBUG && !TextUtils.isEmpty(msg))
            Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (LOG_DEBUG && !TextUtils.isEmpty(msg))
            Log.w(tag, msg);
    }

    public static void wtf(String tag, String msg) {
        if (LOG_DEBUG && !TextUtils.isEmpty(msg))
            Log.wtf(tag, msg);
    }

}
