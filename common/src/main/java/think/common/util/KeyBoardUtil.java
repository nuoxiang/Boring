package think.common.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author think
 * @date 2018/1/15 下午11:39
 */

public class KeyBoardUtil {

    /**
     * 打开软键盘
     *
     * @param mEditText
     * @param mContext
     */
    public static void openKeyboard(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     * @param mContext
     */
    public static void closeKeyboard(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        }
    }

    /**
     * 关闭软键盘
     *
     * @param mContext
     */
    public static void closeKeyboard(Activity mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive() && mContext.getCurrentFocus() != null) {
            if (mContext.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    /**
     * fragment中关闭键盘
     *
     * @param fragment
     */
    public static void closeKeyboard(Fragment fragment) {
        if (fragment.getActivity() != null && fragment.getView() != null) {
            final InputMethodManager imm = (InputMethodManager) fragment.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(fragment.getView().getWindowToken(), 0);
            }
        }
    }

}
