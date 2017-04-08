package pigeon.fast.overseas.com.fastpigeon.activity;


import android.view.View;
import android.widget.ImageView;

import pigeon.fast.overseas.com.fastpigeon.R;


public class MainActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ImageView startVpnIv = (ImageView) findViewById(R.id.start_vpn_iv);
        startVpnIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transfer(VpnActivity.class);
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void loadData() {

    }


}
