package myapplication.guigushangchang.home.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import myapplication.guigushangchang.R;
import myapplication.guigushangchang.bean.HomeBean;
import myapplication.guigushangchang.uitls.Constants;
import myapplication.guigushangchang.uitls.GlideImageLoader;

/**
 * Created by zhouzhou on 2017/6/12.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    /**
     * 六种类型
     */
    /**
     * 横幅广告
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;
    private final HomeBean.ResultBean resultBean;

    /**
     * 当前类型
     */
    public int currentType = BANNER;

    private Context mContext;
    private LayoutInflater inflater;


    public HomeAdapter(Context mContext, HomeBean.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getItemViewType(int position) {
        if (position == BANNER) {
            currentType = BANNER;
        } else if (position == CHANNEL) {
            currentType = CHANNEL;
        } else if (position == ACT) {
            currentType = ACT;
        } else if (position == SECKILL) {
            currentType = SECKILL;
        } else if (position == RECOMMEND) {
            currentType = RECOMMEND;
        } else if (position == HOT) {
            currentType = HOT;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        //以后做完后改成6，现在只实现横幅广告，暂时写1
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mContext, inflater.inflate(R.layout.banner_viewpager, null));
        }
// else if (viewType == CHANNEL) {
//            return new ChannelViewHolder(mContext, inflater.inflate(R.layout.channel_item, null));
//        } else if (viewType == ACT) {
//            return new ActViewHolder(mContext, inflater.inflate(R.layout.act_item, null));
//        } else if (viewType == SECKILL) {
//            return new SeckillViewHolder(mContext, inflater.inflate(R.layout.seckill_item, null));
//        } else if (viewType == RECOMMEND) {
//            return new RecommendViewHolder(mContext, inflater.inflate(R.layout.recommend_item, null));
//        } else if (viewType == HOT) {
//            return new HotViewHolder(mContext, inflater.inflate(R.layout.hot_item, null));
        // }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            //设置数据Banner的数据
            bannerViewHolder.setData(resultBean.getBanner_info());
        }
       /* else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }*/
    }


    class BannerViewHolder extends RecyclerView.ViewHolder {
        public Banner banner;
        public Context mContext;
       // public HomeBean.ResultBean resultBean;

//        public BannerViewHolder(View itemView, Context mContext, HomeBean.ResultBean resultBean) {
//            super(itemView);
//            banner = (Banner) itemView.findViewById(R.id.banner);
//            this.mContext = mContext;
//            //this.resultBean = resultBean;
//        }

        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext =mContext;
            banner = (Banner) itemView.findViewById(R.id.banner);
        }


        public void setData(final List<HomeBean.ResultBean.BannerInfoBean> banner_info) {
            List<String> images = new ArrayList<>();
            for(int i = 0; i < banner_info.size(); i++) {
                images.add(Constants.BASE_URL_IMAGE+banner_info.get(i).getImage());
            }
            banner.setImages(images)
                    .setImageLoader(new GlideImageLoader())
                    .setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {

                            Toast.makeText(mContext, "position=="+position, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .start();
        }


    }


}