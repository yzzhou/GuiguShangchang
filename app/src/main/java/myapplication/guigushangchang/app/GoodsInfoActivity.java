package myapplication.guigushangchang.app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import myapplication.guigushangchang.R;
import myapplication.guigushangchang.home.activity.MainActivity;
import myapplication.guigushangchang.home.adapter.HomeAdapter;
import myapplication.guigushangchang.home.bean.GoodsBean;
import myapplication.guigushangchang.home.bean.HomeBean;
import myapplication.guigushangchang.shoppingcart.utils.CartStorage;
import myapplication.guigushangchang.utils.Constants;
import okhttp3.Call;

public class GoodsInfoActivity extends AppCompatActivity {

    @BindView(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @BindView(R.id.ib_good_info_more)
    ImageButton ibGoodInfoMore;
    @BindView(R.id.iv_good_info_image)
    ImageView ivGoodInfoImage;
    @BindView(R.id.tv_good_info_name)
    TextView tvGoodInfoName;
    @BindView(R.id.tv_good_info_desc)
    TextView tvGoodInfoDesc;
    @BindView(R.id.tv_good_info_price)
    TextView tvGoodInfoPrice;
    @BindView(R.id.tv_good_info_store)
    TextView tvGoodInfoStore;
    @BindView(R.id.tv_good_info_style)
    TextView tvGoodInfoStyle;
    @BindView(R.id.wb_good_info_more)
    WebView wbGoodInfoMore;
    @BindView(R.id.tv_good_info_callcenter)
    TextView tvGoodInfoCallcenter;
    @BindView(R.id.tv_good_info_collection)
    TextView tvGoodInfoCollection;
    @BindView(R.id.tv_good_info_cart)
    TextView tvGoodInfoCart;
    @BindView(R.id.btn_good_info_addcart)
    Button btnGoodInfoAddcart;
    @BindView(R.id.ll_goods_root)
    LinearLayout llGoodsRoot;
    @BindView(R.id.tv_more_share)
    TextView tvMoreShare;
    @BindView(R.id.tv_more_search)
    TextView tvMoreSearch;
    @BindView(R.id.tv_more_home)
    TextView tvMoreHome;
    @BindView(R.id.btn_more)
    Button btnMore;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.activity_goods_info)
    LinearLayout activityGoodsInfo;
    private GoodsBean goodsBean;
    private GoodsBean tmpeGoodsBean;
    private String shareUrl;
    private HomeBean.ResultBean result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.bind(this);
        getData();
        setData();
    }

    private void setData() {
        String coverPrice = goodsBean.getCover_price();
        String figure = goodsBean.getFigure();
        String productId = goodsBean.getProduct_id();
        String name = goodsBean.getName();

        //设置图片
        if (figure.contains("http")) {
            Glide.with(GoodsInfoActivity.this).load(figure).into(ivGoodInfoImage);
        } else {
            //设置图片
            Glide.with(GoodsInfoActivity.this).load(Constants.BASE_URL_IMAGE + figure).into(ivGoodInfoImage);
        }

        //设置名称
        tvGoodInfoName.setText(name);
        //设置价格
        //tvGoodInfoPrice.setText("￥" + cover_price);
        tvGoodInfoPrice.setText("￥" + coverPrice);


        //设置webView的数据
        setWebViewData(productId);
    }

    private void setWebViewData(String productId) {
        WebSettings webSettings = wbGoodInfoMore.getSettings();
        //设置支持js
        webSettings.setJavaScriptEnabled(true);
        //设置支持双击变大变小
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        //设置检索缓存的
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置不跳转到系统的浏览器
        wbGoodInfoMore.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }
        });
        wbGoodInfoMore.loadUrl("http://mp.weixin.qq.com/s/Cf3DrW2lnlb-w4wYaxOEZg");
    }


    private void getData() {
        goodsBean = (GoodsBean) getIntent().getSerializableExtra(HomeAdapter.GOODS_BEAN);
        shareUrl = getIntent().getStringExtra(HomeAdapter.FIGURE);

        if (!TextUtils.isEmpty(shareUrl)) {
            //是扫描进来的
            goodsBean = new GoodsBean();
            goodsBean.setFigure(shareUrl);//图片地址
            setShareData();

        }else{
            //默认正常点击
            setData();
        }
    }

    private void setShareData() {
        /**
         * 重新联网请求数据
         */
        OkHttpUtils
                .get()
                .url(Constants.HOME_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "服务器异常,请重试" + e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功");
                        processData(response);
                        findGoodsBean();
                        setData();
                    }


                });
    }

    private void findGoodsBean() {
        List<HomeBean.ResultBean.BannerInfoBean> act_info = result.getBanner_info();
        for (int i = 0; i < act_info.size(); i++) {
            if (act_info.get(i).getImage().equals(shareUrl)) {
                if (i == 0) {
                    goodsBean.setName("尚硅谷在线课堂");
                    goodsBean.setCover_price("320.00");
                    goodsBean.setProduct_id("627");
                } else if (i == 1) {
                    goodsBean.setName("尚硅谷抢座");
                    goodsBean.setCover_price("800.00");
                    goodsBean.setProduct_id("21");
                } else if (i == 2) {
                    goodsBean.setName("尚硅谷讲座");
                    goodsBean.setCover_price("150.00");
                    goodsBean.setProduct_id("1341");
                }

            }
        }
        List<HomeBean.ResultBean.HotInfoBean> hot_info = result.getHot_info();
        for (int i = 0; i < hot_info.size(); i++) {
            if (hot_info.get(i).getFigure().equals(shareUrl)) {
                goodsBean.setName(hot_info.get(i).getName());
                goodsBean.setCover_price(hot_info.get(i).getCover_price());
                goodsBean.setProduct_id(hot_info.get(i).getProduct_id());
            }
        }
        List<HomeBean.ResultBean.RecommendInfoBean> recommend_info = result.getRecommend_info();
        for (int i = 0; i < recommend_info.size(); i++) {
            if (recommend_info.get(i).getFigure().equals(shareUrl)) {
                goodsBean.setName(recommend_info.get(i).getName());
                goodsBean.setCover_price(recommend_info.get(i).getCover_price());
                goodsBean.setProduct_id(recommend_info.get(i).getProduct_id());
            }
        }
        HomeBean.ResultBean.SeckillInfoBean seckill_info = result.getSeckill_info();
        List<HomeBean.ResultBean.SeckillInfoBean.ListBean> seckill_infoList = seckill_info.getList();
        for (int i = 0; i < seckill_infoList.size(); i++) {
            if (seckill_infoList.get(i).getFigure().equals(shareUrl)) {
                goodsBean.setName(seckill_infoList.get(i).getName());
                goodsBean.setCover_price(seckill_infoList.get(i).getCover_price());
                goodsBean.setProduct_id(seckill_infoList.get(i).getProduct_id());
            }
        }
    }

    private void processData(String response) {
        HomeBean homeBean = JSON.parseObject(response, HomeBean.class);
        //得到resultBean的数据
        result = homeBean.getResult();
    }

    @OnClick({R.id.ib_good_info_back, R.id.ib_good_info_more, R.id.tv_good_info_callcenter, R.id.tv_good_info_collection, R.id.tv_good_info_cart, R.id.btn_good_info_addcart, R.id.tv_more_share, R.id.tv_more_search, R.id.tv_more_home,R.id.btn_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;
            case R.id.ib_good_info_more:
                //Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
                if (llRoot.getVisibility() == View.VISIBLE) {
                    llRoot.setVisibility(View.GONE);
                } else {
                    llRoot.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_more:
                //隐藏
                llRoot.setVisibility(View.GONE);
                break;
            case R.id.tv_good_info_callcenter:
                Toast.makeText(this, "呼叫中心", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_collection:
                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_cart:
               // Toast.makeText(this, "进入购物车", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, ShoppingCartFragment.class);
//                startActivity(intent);
                Intent intent = new Intent(GoodsInfoActivity.this,MainActivity.class);
                intent.putExtra("checkId",R.id. rb_cart);
                startActivity(intent);
                break;
            case R.id.btn_good_info_addcart:
                Toast.makeText(this, "已添加到购物车", Toast.LENGTH_SHORT).show();
                CartStorage.getInstance(GoodsInfoActivity.this).addData(goodsBean);
                break;
            case R.id.tv_more_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_home:
                Toast.makeText(this, "主页", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
