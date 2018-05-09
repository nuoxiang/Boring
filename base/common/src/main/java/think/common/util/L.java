package think.common.util;

import android.util.Log;

import think.common.constant.AppInit;

/**
 * @author think
 * @date 2018/1/15 下午4:57
 */

public class L {
    private static final String TAG = "========";

    public static void e(String tag, String message) {
        if (AppInit.DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void e(String message) {
        if (AppInit.DEBUG) {
            e(TAG, message);
        }
    }

    public static void e(Exception e) {
        if (AppInit.DEBUG) {
            e.printStackTrace();
        }
    }
}
