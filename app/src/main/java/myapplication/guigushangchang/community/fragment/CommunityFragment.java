package myapplication.guigushangchang.community.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import myapplication.guigushangchang.base.BaseFragment;

/**
 * Created by zhouzhou on 2017/6/12.
 */

public class CommunityFragment extends BaseFragment {
    private static final String TAG = CommunityFragment.class.getSimpleName();
    private TextView textView;

    @Override
    public View initView() {
        Log.e(TAG,"主页视图被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        return textView;
    }
    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"主页数据被初始化了");
        textView.setText("发现");
    }
}
