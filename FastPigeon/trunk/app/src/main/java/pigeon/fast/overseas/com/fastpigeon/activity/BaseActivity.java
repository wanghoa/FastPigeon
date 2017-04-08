package pigeon.fast.overseas.com.fastpigeon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

import pigeon.fast.overseas.com.fastpigeon.utils.ToastUtil;

/**
 * Created by wanghao on 2017/4/4.
 */

public abstract class BaseActivity extends FragmentActivity {

    protected boolean isShouldHideInput = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Activity管理和应用程序退出
        AppManager.getAppManager().addActivity(this);
        //布局
        setContentView(getLayoutId());
        // 这个必须写在setContentView之后才管用
//        ButterKnife.bind(this);

       // intentFactory = new ActivityIntentFactory(this);


        initView();
        initData();
        loadData();
    }
    /**
     * 得到所要填充到activity的布局文件的id
     *
     * @return 所要填充到activity的布局文件的id
     */
    protected abstract int getLayoutId();
    /**
     * 初始化控件
     */
    protected abstract void initView();
    /**
     * 初始化数据
     */
    protected abstract void initData();
    /**
     * 网络请求
     */
    protected abstract void loadData();

    public void doToast(String string) {
        ToastUtil.getInstance().showToast(this,string);
    }

    public void doToast(int resId) {
        ToastUtil.getInstance().showToast(this,resId);
    }

    public void transfer(Class<?> clazz) {
        try {
            Intent intent = new Intent(this, clazz);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Log.e("transfer", e.toString());
        }
    }

    public void transfer(Class<?> clazz, int requestCode) {
        try {
            Intent intent = new Intent(this, clazz);
            startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            Log.e("transfer", e.toString());
        }
    }

    public void transfer(Class<?> clazz, Serializable obj, String params) {
        try {
            Intent intent = new Intent(this, clazz);
            intent.putExtra(params, obj);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Log.e("transfer", e.toString());
        }
    }

    public void transfer(Class<?> clazz, String obj, String params) {
        try {
            Intent intent = new Intent(this, clazz);
            intent.putExtra(params, obj);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Log.e("transfer", e.toString());
        }
    }

    public void transfer(Class<?> clazz, String obj, String params, String obj1, String params1) {
        try {
            Intent intent = new Intent(this, clazz);
            intent.putExtra(params, obj);
            intent.putExtra(params1, obj1);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Log.e("transfer", e.toString());
        }
    }

    public void transfer(Class<?> clazz, String str, String params, int requestCode) {
        try {
            Intent intent = new Intent(this, clazz);
            intent.putExtra(params, str);
            startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            Log.e("transfer", e.toString());
        }
    }

    public void transfer(Class<?> clazz, int requestCode, Serializable obj, String params) {
        try {
            Intent intent = new Intent(this, clazz);
            intent.putExtra(params, obj);
            startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            Log.e("transfer", e.toString());
        }
    }

    public String getStringByUI(View view) {

        if (view instanceof EditText) {
            return ((EditText) view).getText().toString().trim();
        } else if (view instanceof TextView) {
            return ((TextView) view).getText().toString().trim();
        }
        return "";
    }

    public static String getUnNullString(String s, String defalt) {
        return (s == null || TextUtils.isEmpty(s) || "null".equals(s)) ? defalt : s;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev) && isShouldHideInput()) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 点击屏幕关闭键盘是否可用
     *
     * @return
     */
    protected boolean isShouldHideInput() {
        return isShouldHideInput;
    }


    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 隐藏软件盘
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtil.getInstance().cancleToast();
    }

    /*public final User getLoginUser() {
        return App.getInstance().getLoginUser();
    }

    public final String getLoginUserId() {
        User user = getLoginUser();
        if (user == null)
            return "";
        return user.getMemberId();
    }
*/
}
