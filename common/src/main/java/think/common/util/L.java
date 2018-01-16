package think.common.util;

import android.util.Log;

import think.common.engine.EngineManger;

/**
 * @author think
 * @date 2018/1/15 下午4:57
 */

public class L {
    private static final String TAG = "========";

    public static void E(String tag, String message) {
        if (EngineManger.DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void E(String message) {
        if (EngineManger.DEBUG) {
            E(TAG, message);
        }
    }

    public static void E(Exception e) {
        if (EngineManger.DEBUG) {
            e.printStackTrace();
        }
    }
}
