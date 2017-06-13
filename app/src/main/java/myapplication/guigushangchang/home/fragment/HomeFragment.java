package myapplication.guigushangchang.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import myapplication.guigushangchang.R;
import myapplication.guigushangchang.base.BaseFragment;
import myapplication.guigushangchang.home.adapter.HomeAdapter;
import myapplication.guigushangchang.home.bean.HomeBean;
import myapplication.guigushangchang.utils.Constants;
import okhttp3.Request;

import static myapplication.guigushangchang.R.id.ib_top;

/**
 * Created by zhouzhou on 2017/6/12.
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.rv_home)
    RecyclerView rvHome;

    @BindView(R.id.tv_search_home)
    TextView tvSearchHome;

    @BindView(R.id.tv_message_home)
    TextView tvMessageHome;

    @BindView(ib_top)
    ImageButton ibTop;
    private HomeBean.ResultBean resultBean;
    private HomeAdapter adapter;


    @Override
    public View initView() {
        Log.e(TAG, "主页视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        //设置点击事件
        return view;

    }

    @OnClick({R.id.tv_search_home, R.id.tv_message_home, ib_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_home:
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_message_home:
                Toast.makeText(mContext, "进入消息中心", Toast.LENGTH_SHORT).show();
                break;
            case ib_top:
                //回到顶部
                ibTop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rvHome.scrollToPosition(0);
                    }
                });
//                ib_top.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        rvHome.scrollToPosition(0);
//                    }
//                });
                break;
        }
    }




    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "主页数据被初始化了");
        //联网请求主页的数据
        getDataFromNet();
    }
    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.HOME_URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {



        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {

        }

        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            //当联网成功后会回调这里
            switch (id) {
                case 100:
                    if (response != null) {
                        processData(response);

                        adapter = new HomeAdapter(mContext, resultBean);
                        rvHome.setAdapter(adapter);

                        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
                        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                if (position <= 3) {
                                    ibTop.setVisibility(View.GONE);

                                } else {
                                    ibTop.setVisibility(View.VISIBLE);
                                }
                                return 1;
                            }
                        });
                        rvHome.setLayoutManager(manager);

                    }
                    break;
                case 101:
//                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    private void processData(String json) {
        if (!TextUtils.isEmpty(json)) {

            HomeBean
                    resultBeanData = JSON.parseObject(json, HomeBean.class);

            resultBean = resultBeanData.getResult();

            Log.e(TAG,"resultBean=="+resultBean.getBanner_info());

        }
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbind
//    }
}

