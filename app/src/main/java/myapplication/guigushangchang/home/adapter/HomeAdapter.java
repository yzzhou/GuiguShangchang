package myapplication.guigushangchang.home.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.magicviewpager.transformer.RotateYTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import myapplication.guigushangchang.R;
import myapplication.guigushangchang.home.bean.HomeBean;
import myapplication.guigushangchang.home.utils.GlideImageLoader;
import myapplication.guigushangchang.utils.Constants;

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
    private HomeBean.ResultBean.SeckillInfoBean seckill_info;


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
        return 6;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mContext, inflater.inflate(R.layout.banner_viewpager, null));
        }
      else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mContext, inflater.inflate(R.layout.channel_item, null));
        } else if (viewType == ACT) {
            return new ActViewHolder(mContext, inflater.inflate(R.layout.act_item, null));
        }
         else if (viewType == SECKILL) {
            return new SeckillViewHolder(mContext, inflater.inflate(R.layout.seckill_item, null));
        }
          else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mContext, inflater.inflate(R.layout.recommend_item, null));
        }
         else if (viewType == HOT) {
            return new HotViewHolder(mContext, inflater.inflate(R.layout.hot_item, null));
         }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            //设置数据Banner的数据
            bannerViewHolder.setData(resultBean.getBanner_info());
        }
        else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        }
        else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        }
        else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        }
          else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());
        }
          else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }
    }


    class BannerViewHolder extends RecyclerView.ViewHolder {
        public Banner banner;
        public Context mContext;

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


    class ChannelViewHolder extends RecyclerView.ViewHolder {
        private final Context mContent;
        private GridView gv;

        public ChannelViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContent = mContext;
            gv = (GridView) itemView.findViewById(R.id.gv);
        }

        public void setData(final List<HomeBean.ResultBean.ChannelInfoBean> channel_info) {
           ChannelAdapter adapter =new ChannelAdapter(mContent,channel_info) ;
            gv.setAdapter(adapter);
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HomeBean.ResultBean.ChannelInfoBean channelInfoBean = channel_info.get(position);
                    Toast.makeText(mContext, ""+channelInfoBean.getChannel_name(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder{
        private final Context mContext;
        private ViewPager act_viewpager;

        public ActViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            act_viewpager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(final List<HomeBean.ResultBean.ActInfoBean> act_info) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(mContext,act_info);
            act_viewpager.setAdapter(adapter);
            //设置间距
            act_viewpager.setPageMargin(20);
            act_viewpager.setPageTransformer(true, new
                    RotateYTransformer());
            adapter.setOnItemClickListener(new ViewPagerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    HomeBean.ResultBean.ActInfoBean actInfoBean = act_info.get(position);
                    Toast.makeText(mContext, ""+actInfoBean.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private boolean isFrist = false;
    class SeckillViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMore;
        private RecyclerView recyclerView;
        public Context mContext;
        private CountdownView countdownView;
        @BindView(R.id.countdownview)
        CountdownView countdownview;
        @BindView(R.id.tv_more_seckill)
        TextView tvMoreSeckill;
        @BindView(R.id.rv_seckill)
        RecyclerView rvSeckill;

        SeckillRecyclerViewAdapter adapter;

        Handler mHandler = new Handler();
        HomeBean.ResultBean.SeckillInfoBean seckillInfo;

        /**
         * 开始刷新
         */


        public SeckillViewHolder(Context mContext,View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            tvMore = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
            countdownView = (CountdownView) itemView.findViewById(R.id.countdownview);
            this.mContext = mContext;
        }
        private long dt;
        public void setData(HomeBean.ResultBean.SeckillInfoBean seckill_info) {
            //设置RecyclerView

            dt =   Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());
            countdownView.start(dt);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
             adapter = new SeckillRecyclerViewAdapter(mContext,seckill_info);
            recyclerView.setAdapter(adapter);
            adapter.setOnSeckillRecyclerView(new SeckillRecyclerViewAdapter.OnSeckillRecyclerView() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(mContext, "position"+position, Toast.LENGTH_SHORT).show();
                }
            });
            this.seckillInfo = seckill_info;
            if (!isFrist) {
                isFrist = true;
                //计算倒计时持续的时间
                long totalTime = Long.parseLong(seckillInfo.getEnd_time()) - Long.parseLong(seckillInfo.getStart_time());

                // 校对倒计时
                long curTime = System.currentTimeMillis();
                //重新设置结束数据时间
                seckillInfo.setEnd_time((curTime + totalTime + ""));
                //开始刷新
                startRefreshTime();
            }
        }
        void startRefreshTime() {
            mHandler.postDelayed(mRefreshTimeRunnable, 10);
        }

        Runnable mRefreshTimeRunnable = new Runnable() {
            @Override
            public void run() {
                //得到当前时间
                long currentTime = System.currentTimeMillis();

                if (currentTime >= Long.parseLong(seckillInfo.getEnd_time())) {
                    // 倒计时结束
                    mHandler.removeCallbacksAndMessages(null);
                } else {
                    //更新时间
                    countdownview.updateShow(Long.parseLong(seckillInfo.getEnd_time()) - currentTime);
                    //每隔1000毫秒更新一次
                    mHandler.postDelayed(mRefreshTimeRunnable, 1000);
                }

            }
        };

    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        @BindView(R.id.tv_more_recommend)
        TextView tvMoreRecommend;
        @BindView(R.id.gv_recommend)
        GridView gvRecommend;
        RecommendGridViewAdapter adapter;

        public RecommendViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.bind(this, itemView);
        }

        public void setData(List<HomeBean.ResultBean.RecommendInfoBean> recommend_info) {

            adapter = new RecommendGridViewAdapter(mContext, recommend_info);
            gvRecommend.setAdapter(adapter);
        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        @BindView(R.id.tv_more_hot)
        TextView tvMoreHot;
        @BindView(R.id.gv_hot)
        GridView gvHot;
        HotGridViewAdapter adapter;
        public HotViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.bind(this,itemView);
        }

        public void setData(List<HomeBean.ResultBean.HotInfoBean> hot_info) {
            adapter = new HotGridViewAdapter(mContext,hot_info);
            gvHot.setAdapter(adapter);

            //设置item的点击事件
            gvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position=="+position, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}