package myapplication.guigushangchang.type.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import myapplication.guigushangchang.R;
import myapplication.guigushangchang.app.GoodsInfoActivity;
import myapplication.guigushangchang.app.GoodsListActivity;
import myapplication.guigushangchang.home.adapter.HomeAdapter;
import myapplication.guigushangchang.home.bean.GoodsBean;
import myapplication.guigushangchang.type.bean.TypeBean;
import myapplication.guigushangchang.utils.Constants;
import myapplication.guigushangchang.utils.DensityUtil;

/**
 * Created by zhouzhou on 2017/6/15.
 */

public class TypeRightAdapter extends RecyclerView.Adapter {
    private final Context mContext;



    private LayoutInflater inflater;
    private List<TypeBean.ResultBean.HotProductListBean> hot_product_list;
    private List<TypeBean.ResultBean.ChildBean> child;

    /**
     * 热卖推荐
     */
    private static final int HOT = 0;
    /**
     * 常用分类
     */
    private static final int COMMON = 1;

    /**
     * 当前类型
     */
    private int currentType = HOT;

    @Override
    public int getItemCount() {
        return 1+child.size() ;
    }

    /**
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == HOT) {
            currentType = HOT;
        } else {
            currentType = COMMON;
        }
        return currentType;
    }


    public TypeRightAdapter(Context mContext, List<TypeBean.ResultBean> result) {
        this.mContext = mContext;

        inflater = LayoutInflater.from(mContext);
        this.hot_product_list = result.get(0).getHot_product_list();
        child = result.get(0).getChild();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("Test","viewType=="+viewType);
        if (viewType == HOT) {
            return new HotViewHolder(mContext, inflater.inflate(R.layout.item_hot_right, null));
        } else if (viewType == COMMON) {
            return new CommonViewHolder(mContext, inflater.inflate(R.layout.item_common_right, null));
        }
        return null;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HOT) {
            HotViewHolder viewHolder = (HotViewHolder) holder;
            //设置数据
            viewHolder.setData(hot_product_list);
        } else if (getItemViewType(position) == COMMON) {
            CommonViewHolder viewHolder = (CommonViewHolder) holder;
            int realPosition = position - 1;
            viewHolder.setData(child.get(realPosition),realPosition);

        }
    }

    class CommonViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        @BindView(R.id.iv_ordinary_right)
        ImageView ivOrdinaryRight;

        @BindView(R.id.tv_ordinary_right)
        TextView tvOrdinaryRight;

        @BindView(R.id.ll_root)
        LinearLayout llRoot;

        public CommonViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.bind(this,itemView);
        }

        public void setData(TypeBean.ResultBean.ChildBean childBean, final int realPosition) {

            //设置图片
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE+childBean.getPic()).into(ivOrdinaryRight);
            //设置文本
            tvOrdinaryRight.setText(childBean.getName());
//
            llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext, "realPostion=="+realPosition, Toast.LENGTH_SHORT).show();

                    if(realPosition <=8){
                        Intent intent  = new Intent(mContext, GoodsListActivity.class);
                        intent.putExtra("position",realPosition);
                        mContext.startActivity(intent);
                    }

//                    Toast.makeText(mContext, "得到列表的数据=="+child.get(realPosition).getName(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        @BindView(R.id.ll_hot_right)
        LinearLayout llHotRight;
        @BindView(R.id.hsl_hot_right)
        HorizontalScrollView hslHotRight;

        public HotViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.bind(this, itemView);
        }

        public void setData(final List<TypeBean.ResultBean.HotProductListBean> hot_product_list) {

            for (int i = 0; i < hot_product_list.size(); i++) {
                TypeBean.ResultBean.HotProductListBean listBean = hot_product_list.get(i);
                //线性布局

                LinearLayout myLinear = new LinearLayout(mContext);
                //参数
                LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, -2);
                llParams.setMargins(DensityUtil.dip2px(mContext, 5), 0, DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 20));
                myLinear.setGravity(Gravity.CENTER);
                myLinear.setOrientation(LinearLayout.VERTICAL);

                //设置参数


                //图片

                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 80), DensityUtil.dip2px(mContext, 80));
                ivParams.setMargins(0, 0, 0, DensityUtil.dip2px(mContext, 10));

                //联网请求图片
                Glide.with(mContext).load(Constants.BASE_URL_IMAGE + listBean.getFigure()).into(imageView);
                //添加到线性布局
                myLinear.addView(imageView, ivParams);

                // 文本

                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(mContext);
//                textView.setTextColor(Color.RED);
                textView.setTextColor(Color.parseColor("#ed3f3f"));

                textView.setText("￥" + listBean.getCover_price());
                //添加到线性布局
                myLinear.addView(textView, tvParams);


                //整条的点击事件
                myLinear.setTag(i);
                myLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) v.getTag();
//                        Toast.makeText(mContext,  hot_product_list.get(position).getCover_price()+"", Toast.LENGTH_SHORT).show();

                        String cover_price = hot_product_list.get(position).getCover_price();
                        String name = hot_product_list.get(position).getName();
                        String figure = hot_product_list.get(position).getFigure();
                        String product_id = hot_product_list.get(position).getProduct_id();


                        GoodsBean goodsBean = new GoodsBean();
                        goodsBean.setProduct_id(product_id);
                        goodsBean.setFigure(figure);
                        goodsBean.setCover_price(cover_price);
                        goodsBean.setName(name);

                        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                        intent.putExtra(HomeAdapter.GOODS_BEAN, goodsBean);
                        mContext.startActivity(intent);


                    }
                });


                //添加到
                llHotRight.addView(myLinear, llParams);
            }

        }
    }


}

