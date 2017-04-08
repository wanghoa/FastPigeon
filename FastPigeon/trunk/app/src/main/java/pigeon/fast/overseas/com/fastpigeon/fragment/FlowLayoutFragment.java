package pigeon.fast.overseas.com.fastpigeon.fragment;

import android.support.v4.widget.NestedScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pigeon.fast.overseas.com.fastpigeon.utils.UIUtils;
import pigeon.fast.overseas.com.fastpigeon.widget.FlowLayout;

/**
 * Created by Administrator on 2017/4/8.
 */

public class FlowLayoutFragment  extends BaseFragment {

    private String[] desc = {"世上只有妈妈好","壕鑫互联","简单点说话的方式简单点","王诗琪","爱情36计","NBA全明星赛事"};
    private List<String> hots  = new ArrayList<>();


    @Override
    protected void showSuccess() {
        for (String s : desc) {
            hots.add(s);
        }
        //TODO
        NestedScrollView scrollView = new NestedScrollView(UIUtils.getContext());

        FlowLayout flowLayout = new FlowLayout(getContext());
        for(String item:hots){
            TextView tv = new TextView(getContext());
            tv.setText(item);

            int padding = UIUtils.dip2Px(5);
            tv.setPadding(padding,padding,padding,padding);

            flowLayout.addView(tv);
        }
        scrollView.addView(flowLayout);
//        pager.changeViewTo(scrollView);
    }

    @Override
    protected void loadData() {

    }


}
