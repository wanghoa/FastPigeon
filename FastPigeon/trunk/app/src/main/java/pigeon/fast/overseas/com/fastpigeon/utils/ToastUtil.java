package pigeon.fast.overseas.com.fastpigeon.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wang on 16/3/28.
 */
public class ToastUtil {

    private static ToastUtil ourInstance;
    private Toast toast;
    public static ToastUtil getInstance() {

        if(ourInstance==null){
            synchronized (ToastUtil.class){
                if(ourInstance==null){
                    ourInstance = new ToastUtil();
                }
            }
        }

        return ourInstance;
    }

    private ToastUtil() {
    }

    public void showToast(Context context, String msg){
        if(toast==null){
            toast = Toast.makeText(context,msg, Toast.LENGTH_SHORT);
        }else{
            toast.cancel();
            toast = Toast.makeText(context,msg, Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    /**
     * 自定义Toast
     *
     * @param resId
     */
    public void showToast(Context context, int resId) {
        toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        if (toast==null) {
            toast= Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        }else{
            toast.cancel();
            toast= Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public void cancleToast(){
        if(toast!=null){
            toast.cancel();
        }
    }
}
