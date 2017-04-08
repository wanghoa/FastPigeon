package pigeon.fast.overseas.com.fastpigeon.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import pigeon.fast.overseas.com.fastpigeon.R;
import pigeon.fast.overseas.com.fastpigeon.fragment.FlowLayoutFragment;
import pigeon.fast.overseas.com.fastpigeon.utils.SelectPhotoUtils;
import pigeon.fast.overseas.com.fastpigeon.utils.ToastUtil;

import static pigeon.fast.overseas.com.fastpigeon.R.id.enjoy_teleplay_tv;

/**
 * Created by wanghao on 17-4-7.
 */

public class VpnActivity extends BaseActivity implements View.OnClickListener {


    private Dialog dialog;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private CircleImageView mHeadImg;
    private TextView mEnjoyTeleplay, mMy;
    //.......

    private String key;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_vpn;
    }

    @Override
    protected void initView() {

        mEnjoyTeleplay = (TextView) findViewById(enjoy_teleplay_tv);
        mMy = (TextView) findViewById(R.id.me_tv);
        mHeadImg = (CircleImageView) findViewById(R.id.head_img);
        mHeadImg.setOnClickListener(this);
        mEnjoyTeleplay.setOnClickListener(this);
        mMy.setOnClickListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FlowLayoutFragment flowLayoutFragment = new FlowLayoutFragment();
        fragmentTransaction.add(R.id.ll,flowLayoutFragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void loadData() {
//        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("index", 0);
//
//        key = Constants.Http.HOT + ".0";
//        String json = CommonCacheProcess.getLocalJson(key);
//        if (json != null) {
//            parserJson(json);
//            pager.runOnUiThread();
//        } else {
//            loadNetData(params);
//        }
    }
    private void loadNetData(HashMap<String, Object> params) {

//        Request request = HttpUtils.getRequest(Constants.Http.HOT, params);
//
//        Call call = HttpUtils.getClient().newCall(request);
//        // call.download():同步
//        // 异步
//        call.enqueue(new BaseCallBack(pager) {
//            @Override
//            protected void onSuccess(String json) {
//                // 需要在内存存一份文件
//                MyApplication.getProtocolCache().put(key, json);
//                CommonCacheProcess.cacheToFile(key, json);
//                parserJson(json);
//            }
//        });
    }
    private void parserJson(String json) {
//        pager.isReadData = true;
//
//        hots = MyApplication.getGson().fromJson(json, new TypeToken<List<String>>() {
//        }.getType());
//
//        if (hots != null && hots.size() > 0) {
//            pager.isNullData = false;
//        } else {
//            pager.isNullData = true;
//        }
    }


    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        dialog = new Dialog(this, R.style.Theme_dialog);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_bottom, null);
        Button takePhoto = (Button) inflate.findViewById(R.id.takePhoto);
        Button choosePhoto = (Button) inflate.findViewById(R.id.choosePhoto);
        Button btn_cancel = (Button) inflate.findViewById(R.id.btn_cancel);
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAlbumIntent = new Intent(
                        Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);

            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                tempUri = Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "image.jpg"));
                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        //就能够水平占满了
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                        dialog.dismiss();
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = SelectPhotoUtils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            mHeadImg.setImageBitmap(photo);
            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = SelectPhotoUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath + "");
        if (imagePath != null) {
            // 拿着imagePath上传了
            // ...
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_img:
                showChoosePicDialog();
                break;
            case R.id.me_tv:
              //  App.getContext()使用此上下文就崩溃
                ToastUtil.getInstance().showToast(this, "wode");
                break;
            case enjoy_teleplay_tv:

                break;
            default:
                break;
        }
    }

}
