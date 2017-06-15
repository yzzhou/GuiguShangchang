package myapplication.guigushangchang.type.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import myapplication.guigushangchang.R;
import myapplication.guigushangchang.base.BaseFragment;
import myapplication.guigushangchang.type.adapter.TagGridViewAdapter;
import myapplication.guigushangchang.type.bean.TagBean;
import myapplication.guigushangchang.utils.Constants;
import okhttp3.Call;

/**
 * Created by zhouzhou on 2017/6/15.
 */

public class TagFragment extends BaseFragment {
    @BindView(R.id.gv_tag)
    GridView gvTag;
    Unbinder unbinder;
    private TextView textView;
    private TagGridViewAdapter adapter;
    private List<TagBean.ResultBean> result;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_tag, null);
        ButterKnife.bind(this,view);
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
                .url(Constants.TAG_URL)
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
//                    Toast.makeText(mContext, "http", Toast.LENGTH_SHORT).show();
                    if (response != null) {
                        processData(response);
                        adapter = new TagGridViewAdapter(mContext, result);
                        gvTag.setAdapter(adapter);
                    }
                    break;
                case 101:
                    break;
            }
        }

    }

    /**
     * 解析json数据
     * @param json
     */
    private void processData(String json) {
        TagBean tagBean = JSON.parseObject(json,TagBean.class);
        result = tagBean.getResult();
        if(result != null && result.size() >0){
            //设置适配器
            adapter = new TagGridViewAdapter(mContext,result);
            gvTag.setAdapter(adapter);

            gvTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Toast.makeText(mContext, ""+result.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }



//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
}

