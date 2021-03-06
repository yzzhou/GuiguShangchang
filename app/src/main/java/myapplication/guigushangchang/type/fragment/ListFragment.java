package myapplication.guigushangchang.type.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import myapplication.guigushangchang.R;
import myapplication.guigushangchang.base.BaseFragment;
import myapplication.guigushangchang.type.adapter.TypeLeftAdapter;
import myapplication.guigushangchang.type.adapter.TypeRightAdapter;
import myapplication.guigushangchang.type.bean.TypeBean;
import myapplication.guigushangchang.utils.Constants;
import okhttp3.Call;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

/**
 * Created by zhouzhou on 2017/6/15.
 */

public class ListFragment extends BaseFragment {
    private String[] urls = new String[]{Constants.SKIRT_URL, Constants.JACKET_URL, Constants.PANTS_URL, Constants.OVERCOAT_URL,
            Constants.ACCESSORY_URL, Constants.BAG_URL, Constants.DRESS_UP_URL, Constants.HOME_PRODUCTS_URL, Constants.STATIONERY_URL,
            Constants.DIGIT_URL, Constants.GAME_URL};
    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品",
            "办公文具", "数码周边", "游戏专区"};
    @BindView(R.id.lv_left)
    ListView lvLeft;
    @BindView(R.id.rv_right)
    RecyclerView rlRight;
    public TypeLeftAdapter leftAdapter;
    public List<TypeBean.ResultBean> result;
    private TypeRightAdapter typeRightAdapter;


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_list, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        leftAdapter = new TypeLeftAdapter(mContext,titles);
        lvLeft.setAdapter(leftAdapter);
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.设置点击的位置
                leftAdapter.changeSelected(position);

                //2.刷新适配器--getView
                leftAdapter.notifyDataSetChanged();

                //根据位置得到不同的url，联网请求
                getDataFromNet(urls[position]);
            }
        });
        getDataFromNet(urls[0]);
    }


    /**
     * 联网请求网络
     * @param url
     */
    private void getDataFromNet(String url) {
        Log.e("TAG", "url======" + url);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Log.e(TAG, "首页请求失败==" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "请求成功==" + response);
                        processData(response);
                    }

                });

    }

    private void processData(String json) {
        TypeBean typeBean = JSON.parseObject(json,TypeBean.class);
        result = typeBean.getResult();
        Log.e("TAG","解析成功=="+typeBean.getResult().get(0).getName());
        if(result !=null &&result.size()>0){
            typeRightAdapter = new TypeRightAdapter(mContext, result);
            rlRight.setAdapter(typeRightAdapter);
            GridLayoutManager manager=new GridLayoutManager(mContext,3);
            manager.setSpanSizeLookup(new  GridLayoutManager.SpanSizeLookup(){

                @Override
                public int getSpanSize(int position) {
                    if(position==0){
                        return  3;
                    }else{
                        return 1;
                    }

                }
            });
            rlRight.setLayoutManager(manager);

        }
    }

//
//    private boolean isFirst  =true;
//    public void initListener() {
//        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //1.设置点击的位置
//
//                leftAdapter.changeSelected(position);
//                //2.在TypeLeftAdapter getView根据位置中高亮显示代码
//
//
//                //3.刷新适配器
//                leftAdapter.notifyDataSetChanged();
//                getDataFromNet(urls[position]);
//                //只要点击的不是0，就不是第一次
//                if(position != 0){
//                    isFirst = false;
//                }
//
//            }
//        });
//    }

}

