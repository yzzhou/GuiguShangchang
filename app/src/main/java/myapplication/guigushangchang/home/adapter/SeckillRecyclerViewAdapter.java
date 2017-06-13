package myapplication.guigushangchang.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import myapplication.guigushangchang.R;
import myapplication.guigushangchang.home.bean.HomeBean;
import myapplication.guigushangchang.utils.Constants;

/**
 * Created by zhouzhou on 2017/6/12.
 */

public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<SeckillRecyclerViewAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<HomeBean.ResultBean.SeckillInfoBean.ListBean> datas;


    private LayoutInflater inflater;

    public SeckillRecyclerViewAdapter(Context mContext, HomeBean.ResultBean.SeckillInfoBean seckill_info) {
        this.mContext = mContext;
       this.datas = seckill_info.getList();
        inflater = LayoutInflater.from(mContext);
    }

//    public SeckillRecyclerViewAdapter(Context mContext, HomeBean.ResultBean.SeckillInfoBean data) {
//
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_seckill, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //1.根据位置得到对应数据
        HomeBean.ResultBean.SeckillInfoBean.ListBean listBean = datas.get(position);
        //2.绑定数据
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+listBean.getFigure()).into(holder.ivFigure);
        //设置价格
        holder.tvCoverPrice.setText("￥"+listBean.getCover_price());
        holder.tvOriginPrice.setText("￥"+listBean.getOrigin_price());


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_figure)
        ImageView ivFigure;
        @BindView(R.id.tv_cover_price)
        TextView tvCoverPrice;
        @BindView(R.id.tv_origin_price)
        TextView tvOriginPrice;
        @BindView(R.id.ll_root)
        LinearLayout llRoot;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSeckillRecyclerView != null) {
                        onSeckillRecyclerView.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnSeckillRecyclerView{
        /**
         * 当某条被点击的时候回调
         * @param position
         */
        public void onItemClick(int position);
    }

    private  OnSeckillRecyclerView onSeckillRecyclerView;

    /**
     * 设置item的监听
     * @param onSeckillRecyclerView
     */
    public void setOnSeckillRecyclerView(OnSeckillRecyclerView onSeckillRecyclerView) {
        this.onSeckillRecyclerView = onSeckillRecyclerView;
    }
}
