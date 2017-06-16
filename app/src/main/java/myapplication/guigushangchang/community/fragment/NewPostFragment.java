package myapplication.guigushangchang.community.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import myapplication.guigushangchang.R;
import myapplication.guigushangchang.base.BaseFragment;
import myapplication.guigushangchang.community.adapter.NewPostListViewAdapter;
import myapplication.guigushangchang.community.bean.NewPostBean;
import myapplication.guigushangchang.utils.Constants;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by zhouzhou on 2017/6/15.
 */

public class NewPostFragment extends BaseFragment {
    @BindView(R.id.lv_new_post)
    ListView lvNewPost;
    public List<NewPostBean.ResultBean> result;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_new_post, null);
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
                .url(Constants.NEW_POST_URL)
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
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
                    if (response != null) {
                        processData(response);
                        NewPostListViewAdapter adapter = new NewPostListViewAdapter(mContext, result);
                        lvNewPost.setAdapter(adapter);
                        processData(response);
                    }
                    break;
                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    private void processData(String json) {
        NewPostBean newPostBean = JSON.parseObject(json, NewPostBean.class);
        result = newPostBean.getResult();
    }

}

