package myapplication.guigushangchang.community.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import myapplication.guigushangchang.R;
import myapplication.guigushangchang.base.BaseFragment;
import myapplication.guigushangchang.community.adapter.HotPostListViewAdapter;
import myapplication.guigushangchang.community.bean.HotPostBean;
import myapplication.guigushangchang.utils.Constants;
import myapplication.guigushangchang.utils.DensityUtil;
import okhttp3.Call;

/**
 * Created by zhouzhou on 2017/6/15.
 */

/**
 * 作者：尚硅谷-杨光福 on 2016/12/16 21:51
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：热帖Fragment
 */
public class HotPostFragment extends BaseFragment {
    @BindView(R.id.lv_hot_post)
    ListView lvHotPost;
    private List<HotPostBean.ResultBean> result;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_hot_post, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }
    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.HOT_POST_URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {


        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
                    if (response != null) {
                        processData(response);
                        HotPostListViewAdapter adapter = new HotPostListViewAdapter(mContext, result);
                        lvHotPost.setAdapter(adapter);
                    }
                    break;
                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    private void processData(String json) {
        HotPostBean hotPostBean = JSONObject.parseObject(json, HotPostBean.class);
        result = hotPostBean.getResult();
    }

}


