package pigeon.fast.overseas.com.fastpigeon.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import pigeon.fast.overseas.com.fastpigeon.global.App;

/**
 * 创建者     wh(ggsc)
 * 版权
 * 描述	      封装和ui相关的操作
 */
public class UIUtils {
    /**
     * 得到上下文
     */
    public static Context getContext() {
        return App.getContext();
    }
    private static Context context = App.getInstance();

    /**
     * 得到Resource对象(获取资源)
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中的字符串信息
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }


    /**
     * 得到String.xml中的字符串数组信息
     */
    public static String[] getStrings(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到Color.xml中的颜色信息
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }
    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    /**
     * 得到应用程序包名
     *
     * @return
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * dip-->px
     *
     * @param dip
     * @return
     */
    public static int dip2Px(int dip) {
        /*
        1.  px/(ppi/160) = dp
        2.  px/dp = density
         */

        //取得当前手机px和dp的倍数关系
        float density = getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + .5f);
        return px;
    }

    public static int px2Dip(int px) {
        // 2.  px/dp = density

        //取得当前手机px和dp的倍数关系
        float density = getResources().getDisplayMetrics().density;

        int dip = (int) (px / density + .5f);
        return dip;
    }

    /**
     * 弹出键盘
     *
     * @param editText
     */
    public static void showPan(EditText editText) {
        InputMethodManager manager = (InputMethodManager) editText.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏键盘
     */
    public static void hidePan(Context mcontext, View v) {
        InputMethodManager imm = (InputMethodManager) mcontext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    /**
     * 获取手机屏幕宽度像素
     *
     * @return
     */
    public static int getWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取手机屏幕高度像素
     *
     * @return
     */
    public static int getHeight() {
        return context.getResources().getDisplayMetrics().heightPixels;
    }


    /**
     * 动态设置listView的高度
     *
     * @param listView
     * @param offsetHeight 高度偏移量：用于修正最后一个item高度测量不准确造成数据悬殊不全的BUG
     * @param offsetCount  显示item个数的偏移量：当需要增加footView或者headerView的时候这设置该参数
     */
    public static void setListViewHeight(ListView listView, int offsetHeight,
                                         int offsetCount) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int size = listAdapter.getCount() + offsetCount;

        for (int i = 0; i < size; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        totalHeight += offsetHeight;

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
