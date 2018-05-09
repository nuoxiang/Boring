package think.common.util;

import android.content.Context;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * @author think
 * @date 2018/1/15 下午10:29
 */

public class DialogUtil {

    /**
     * loading框
     *
     * @param context
     * @return
     */
    public static QMUITipDialog newLoadingDialog(Context context) {
        return new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
    }

    /**
     * 成功框
     *
     * @param context
     * @param txt     null 可以仅显示图片
     * @return
     */
    public static QMUITipDialog newSuccessDialog(Context context, String txt) {
        return new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(txt)
                .create();
    }

    /**
     * 失败框
     *
     * @param context
     * @param txt     null 可以仅显示图片
     * @return
     */
    public static QMUITipDialog newFailDialog(Context context, String txt) {
        return new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(txt)
                .create();
    }

    /**
     * 提示框
     *
     * @param context
     * @param txt     null 可以仅显示图片
     * @return
     */
    public static QMUITipDialog newInfoDialog(Context context, String txt) {
        return new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord(txt)
                .create();
    }

    /**
     * 自定义提示框
     *
     * @param context
     * @param resId
     * @return
     */
    public static QMUITipDialog newCustomDialog(Context context, int resId) {
        return new QMUITipDialog.CustomBuilder(context)
                .setContent(resId)
                .create();
    }

    private static QMUITipDialog qmuiTipDialog;

    /**
     * 自动消失的提示框
     *
     * @param msg
     */
    public static void showText(Context context, @QMUITipDialog.Builder.IconType int iconType, String msg) {
        if (qmuiTipDialog != null && qmuiTipDialog.isShowing()) {
            qmuiTipDialog.dismiss();
        }
        qmuiTipDialog = new QMUITipDialog.Builder(context)
                .setIconType(iconType)
                .setTipWord(msg)
                .create();
        qmuiTipDialog.setCancelable(false);
        qmuiTipDialog.show();
        RxUtil.timer(1500, i -> {
            if (qmuiTipDialog != null && qmuiTipDialog.isShowing()) {
                qmuiTipDialog.dismiss();
                qmuiTipDialog = null;
            }
        });
    }

    public static void showInfoText(Context context, String msg) {
        showText(context, QMUITipDialog.Builder.ICON_TYPE_INFO, msg);
    }

    public static void showFailText(Context context, String msg) {
        showText(context, QMUITipDialog.Builder.ICON_TYPE_FAIL, msg);
    }
}
